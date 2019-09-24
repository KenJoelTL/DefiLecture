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

public class EffectuerDemandeAdhesionEquipeAction extends Action
    implements RequestAware, RequirePRGAction, DataSender, SendAjaxResponse {
  HashMap data;

  @Override
  public String execute() {
    // action envoyée au controleur frontal
    String action = "connexion.do?tache=afficherPageConnexion";
    if (userIsConnected()
        && request.getParameter("idEquipe") != null
        && request.getParameter("idCompte") != null) {
      try {
        String idEquipe = request.getParameter("idEquipe");
        String idCompte = request.getParameter("idCompte");

        // Ouverture de la connexion
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        Equipe equipe = new EquipeDAO(cnx).read(idEquipe);
        Compte compte = new CompteDAO(cnx).read(idCompte);

        // Cherche si le compte existe
        if (compte != null && equipe != null) {
          DemandeEquipe demandeEq;
          DemandeEquipeDAO demandeDao = new DemandeEquipeDAO(cnx);
          demandeEq = demandeDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          // Vérifie si la demande existe déjà comme dans le cas que l'utilisateur
          // a quitté l'équipe avec plus de 0 points en contribution
          if (demandeEq == null) { // si la demande n'existe pas
            demandeEq = new DemandeEquipe();
            demandeEq.setIdCompte(compte.getIdCompte());
            demandeEq.setIdEquipe(equipe.getIdEquipe());

            // Insertion dans la base de données
            if (demandeDao.create(demandeEq)) {
              response.setContentType("text/plain");
              try {
                String msg = "Une demande d'adhésion a été envoyée à l'équipage " + equipe.getNom();
                demandeEq =
                    demandeDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                int idDemandeEquipe = demandeEq.getIdDemandeEquipe();
                String json =
                    "{\"msg\":\"" + msg + "\",\"idDemandeEquipe\" :\"" + idDemandeEquipe + "\"}";
                response.getWriter().write(json);
              } catch (IOException ex) {
                Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                    .log(Level.SEVERE, null, ex);
              }
            }

            action = "demandeEchouee.do?tache=afficherPageListeEquipes";
          } else {
            // si la demande existe déjà alors on la rend visible
            demandeEq.setStatutDemande(-1);
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
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
