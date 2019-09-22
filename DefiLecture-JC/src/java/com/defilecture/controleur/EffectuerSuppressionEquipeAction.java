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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;

import jdbc.Config;
import jdbc.Connexion;

public class EffectuerSuppressionEquipeAction extends Action
    implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected()) {
      if (userIsAdmin()) {
        try {
          String idCompte = request.getParameter("idCompte"),
              idEquipe = request.getParameter("idEquipe");

          Connection cnx =
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          CompteDAO compteDao = new CompteDAO(cnx);
          List<Compte> comptes = compteDao.findByIdEquipe(Integer.parseInt(idEquipe));
          
          EquipeDAO equipeDao = new EquipeDAO(cnx);
          Equipe equipe = equipeDao.read(idEquipe);

          DemandeEquipeDAO demandeequipeDao = new DemandeEquipeDAO(cnx);
          
          for(Compte compte : comptes) {
            compte.setIdEquipe(-1);
            DemandeEquipe demande = demandeequipeDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

            if(compteDao.update(compte) && demandeequipeDao.delete(demande)) {
                Logger.getLogger(this.getClass().getName())
                  .log(Level.INFO, "Le compte #" + compte.getIdCompte() + " a été retiré de l'équipe #" + idEquipe);
              data.put("suppressionSucces", "L'équipe a été supprimée avec succès.");
              equipeDao.delete(equipe);
            } else {
              Logger.getLogger(this.getClass().getName())
                .log(Level.INFO, "Le compte #" + compte.getIdCompte() + " n'a pas été retiré de l'équipe #" + idEquipe);
              data.put("suppressionEchec", "Erreur lors de la suppression de l'équipe.");
              return "erreur.do?tache=afficherPageGestionListeEquipes&idEquipe="
                      + request.getParameter("idEquipe");
            }
          }
          return "succes.do?tache=afficherPageGestionListeEquipes";
        } catch (NumberFormatException ex) {
          data.put("suppressionEchec", "Erreur lors de la suppression de l'équipe.");
          return "erreur.do?tache=afficherPageGestionListeEquipes&idEquipe="
                  + request.getParameter("idEquipe");
        } catch (SQLException ex) {
          Logger.getLogger(EffectuerSuppressionCompteAction.class.getName())
              .log(Level.SEVERE, null, ex);
            data.put("suppressionEchec", "Erreur avec le serveur de base de donnée.");
            return "erreur.do?tache=afficherPageGestionListeEquipes&idEquipe="
                    + request.getParameter("idEquipe");
        } finally {
          Connexion.close();
        }
      }
    }

    return "*.do?tache=afficherPageConnexion";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
