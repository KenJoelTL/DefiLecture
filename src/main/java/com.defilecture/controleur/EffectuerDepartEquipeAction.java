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
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerDepartEquipeAction extends Action implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageConnexion";
    if (userIsConnected()
        && session.getAttribute("role") != null
        && request.getParameter("idEquipe") != null
        && request.getParameter("idCompte") != null
        && (userIsAdmin()
            || userIsCapitaine()
            || request.getParameter("idCompte").equals(session.getAttribute("currentId")))) {

      action =
          "echec.do?tache=afficherPageModificationEquipe&idEquipe="
              + request.getParameter("idEquipe");

      try {
        String idCompte = request.getParameter("idCompte");
        String idEquipe = request.getParameter("idEquipe");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        EquipeDAO equipeDao = new EquipeDAO(cnx);
        CompteDAO compteDao = new CompteDAO(cnx);
        Compte compte = compteDao.read(idCompte);
        Equipe equipe = equipeDao.read(idEquipe);
        if (compte != null && equipe != null && equipe.getIdEquipe() == compte.getIdEquipe()) {
          DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
          DemandeEquipe demandeEquipe =
              demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          if (demandeEquipe != null) {
            equipe.ajouterPoint(demandeEquipe.getPoint());
            if (demandeEqpDao.delete(demandeEquipe) && equipeDao.update(equipe)) {
              compte.setPoint(0);
              compte.setIdEquipe(-1);
              compteDao.update(compte);
              action = "auRevoir.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
              data.put(
                  "succesRetrait",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " a été envoyé par-dessus bord");
            } else {
              action = "tuRestes.do?tache=afficherPageEquipe&idEquipe=" + idEquipe;
              data.put(
                  "erreurRetrait",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " n'a pas été envoyé par-dessus bord");
            }
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerDepartEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
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
