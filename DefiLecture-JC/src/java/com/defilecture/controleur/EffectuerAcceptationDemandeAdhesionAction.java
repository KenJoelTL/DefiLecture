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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerAcceptationDemandeAdhesionAction extends Action
    implements RequirePRGAction, DataSender, SendAjaxResponse {
  HashMap data;

  @Override
  public String execute() {
    String action = ".do?tache=afficherPageAccueil";
    if (userIsConnected()) {
      if ((userIsCapitaine() || userIsAdmin()) && request.getParameter("idDemandeEquipe") != null) {
        try {
          String idDemandeEq = request.getParameter("idDemandeEquipe");
          Connection cnx =
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

          DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
          DemandeEquipe demandeEq = deDao.read(idDemandeEq);
          response.setContentType("text/plain");

          String msg = "";
          String typeAlert = "";

          if (demandeEq == null) {
            action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
            msg = "Impossible d'accepter la demande puisqu'elle a été retirée par le matelot";
            typeAlert = "avertissement";
          } else {
            CompteDAO compteDao = new CompteDAO(cnx);
            Compte cpt = compteDao.read(demandeEq.getIdCompte());
            action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";

            if (cpt == null) {
              action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
              msg = "Le matelot auteur de cette demande est à la retraite.";
              typeAlert = "danger";
            } else {
              if (cpt.getIdEquipe() != -1) {
                msg =
                    "Le matelot "
                        + cpt.getPrenom()
                        + "«"
                        + cpt.getPseudonyme()
                        + "»"
                        + cpt.getNom()
                        + " fait déjà partie d'un équipage.";
                typeAlert = "danger";
              } else {
                int idEquipe = demandeEq.getIdEquipe();
                int nbMembre = compteDao.countCompteByIdEquipe(idEquipe);

                if (nbMembre - 1 < getNbMatelotsMax()) {
                  cpt.setIdEquipe(idEquipe);
                  demandeEq.setStatutDemande(DemandeEquipe.ACCEPTEE);
                  demandeEq.setPoint(cpt.getPoint());
                  if (deDao.update(demandeEq) && compteDao.update(cpt)) {
                    action = "ajoutReussi.do?tache=afficherPageListeDemandesEquipe&ordre=recu";

                    msg =
                        "Le matelot "
                            + cpt.getPrenom()
                            + "«"
                            + cpt.getPseudonyme()
                            + "»"
                            + cpt.getNom()
                            + " fait maintenant partie de votre équipage !";
                    typeAlert = "succes";

                    ArrayList<DemandeEquipe> listeDemandes =
                        (ArrayList<DemandeEquipe>) deDao.findByIdCompte(cpt.getIdCompte());
                    listeDemandes.forEach(
                        demande -> {
                          if (demande.getIdDemandeEquipe() != demandeEq.getIdDemandeEquipe()) {
                            deDao.delete(demande);
                          }
                        });
                  }
                } else {
                  msg = "Impossible d'accepter la demande puisque votre équipe est pleine";
                  typeAlert = "avertissement";
                }
              }
            }
          }

          String msgJson = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"" + typeAlert + "\"}";
          response.getWriter().write(msgJson);

        } catch (IOException ex) {
          Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
              .log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
          Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction.class.getName())
              .log(Level.SEVERE, null, ex);
        } finally {
          Connexion.close();
        }
      }
    }

    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
