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

public class EffectuerReaffectationMembreEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  HashMap data;

  @Override
  public String execute() {
    String action = "Accueil.do?tache=afficherPageAccueil";
    if (userIsConnected()
        && !request.getParameter("idCompte").equals(session.getAttribute("currentId"))
        && (userIsCapitaine() || userIsAdmin())) {
      try {
        String idCompte = request.getParameter("idCompte");
        String idEquipe = request.getParameter("idEquipe");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        EquipeDAO equipeDao = new EquipeDAO(cnx);
        CompteDAO compteDao = new CompteDAO(cnx);
        Compte compte = compteDao.read(idCompte);
        Compte compteSup = compteDao.read(((Integer) session.getAttribute("currentId")).intValue());
        Equipe equipe = equipeDao.read(idEquipe);

        // si le compte connecté est au niveau de Capitaine, alors il faut qu'il soit membre
        // de la
        // même équipe pour réaffecter un membre.
        // si le compte connecté est un Administrateur alors il peut réaffecter un membre de
        // n'importe quelle équipe
        if (compte != null
            && equipe != null
            && compteSup != null
            && equipe.getIdEquipe() == compte.getIdEquipe()
            && ((compte.getIdEquipe() == compteSup.getIdEquipe() && userIsCapitaine())
                || userIsAdmin())) {

          DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
          DemandeEquipe demandeEquipe =
              demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          if (demandeEquipe != null
              && demandeEquipe.getStatutDemande() == DemandeEquipe.SUSPENDUE) {
            demandeEquipe.setStatutDemande(DemandeEquipe.ACCEPTEE);
            if (demandeEqpDao.update(demandeEquipe)) {
              action = "suspension.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
              data.put(
                  "succesReafectation",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " n'est plus à la cale");
            } else {
              data.put(
                  "echecReafectation",
                  "Un erreur est survenue lors de la réafectation du matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " à l'équipage");
              action = "tuRestes.do?tache=afficherPageEquipe&idEquipe=" + idEquipe;
            }
          }
        }

      } catch (SQLException ex) {
        Logger.getLogger(EffectuerReaffectationMembreEquipeAction.class.getName())
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
