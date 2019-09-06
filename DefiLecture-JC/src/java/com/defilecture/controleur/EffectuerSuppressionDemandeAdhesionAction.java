/**
 * This file is part of DefiLecture.
 *
 * <p>DefiLecture is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>DefiLecture is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with DefiLecture. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerSuppressionDemandeAdhesionAction extends Action
    implements RequirePRGAction, DataSender, SendAjaxResponse {

  HashMap data;

  @Override
  public String execute() {
    String action = "bienvenue.do?tache=afficherPageAccueil";
    if (userIsConnected() && request.getParameter("idDemandeEquipe") != null) {
      try {
        String idDemandeEq = request.getParameter("idDemandeEquipe");
        String msg = "", typeAlerte = "";
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
        DemandeEquipe demandeEq = deDao.read(idDemandeEq);
        response.setContentType("text/plain");

        if (demandeEq != null) {
          EquipeDAO eqDao = new EquipeDAO(cnx);
          Equipe eq = eqDao.read(demandeEq.getIdEquipe());

          CompteDAO compteDao = new CompteDAO(cnx);
          Compte compte = compteDao.read(demandeEq.getIdCompte());

          if ((demandeEq.getIdCompte() == ((Integer) session.getAttribute("currentId")).intValue())
              || userIsCapitaine()
              || userIsAdmin()) {
            if (!deDao.delete(demandeEq)) {
              action = "annulation.do?tache=afficherPageListeEquipes";
              if (userIsCapitaine()) {
                action = "annulation.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                msg = "Impossible de refuser la demande puisqu'elle a été retirée par le matelot";
                typeAlerte = "avertissement";
              }
            } else {
              if (request.getParameter("ordre") != null
                  && "recu".equals(request.getParameter("ordre"))
                  && userIsCapitaine()) {
                msg =
                    "Demande du matelot "
                        + compte.getPrenom()
                        + " «"
                        + compte.getPseudonyme()
                        + "» "
                        + compte.getNom()
                        + " refusée";
                typeAlerte = "succes";
                action = "refus.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
              } else {
                msg = "Votre demande d'adhésion à l'équipage " + eq.getNom() + " a été retirée";
                typeAlerte = "succes";
                action = "annulation.do?tache=afficherPageListeEquipes";
              }
            }
          }
        } else {
          action = "annulation.do?tache=afficherPageListeEquipes";
          if (userIsCapitaine()) {
            action = "annulation.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
            msg = "Impossible de refuser la demande puisqu'elle a été retirée par le matelot";
            typeAlerte = "avertissement";
          }
        }
        response
            .getWriter()
            .write("{\"msg\":\"" + msg + "\",\"typeAlert\" :\"" + typeAlerte + "\"}");

      } catch (IOException ex) {
        Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerSuppressionDemandeAdhesionAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
