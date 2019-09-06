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

public class EffectuerSuspensionMembreEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected()
        && request.getParameter("idEquipe") != null
        && request.getParameter("idCompte") != null
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
        // même équipe pour suspendre un membre.
        // si le compte connecté est un Administrateur alors il peut suspendre un membre de
        // n'importe quelle équipe
        if (compte != null
            && equipe != null
            && compteSup != null
            && equipe.getIdEquipe() == compte.getIdEquipe()
            && ((compte.getIdEquipe() == compteSup.getIdEquipe()
                    && compteSup.getRole() == Compte.CAPITAINE)
                || compteSup.getRole() == Compte.ADMINISTRATEUR)) {

          DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
          DemandeEquipe demandeEquipe =
              demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          if (demandeEquipe != null && demandeEquipe.getStatutDemande() == DemandeEquipe.ACCEPTEE) {
            demandeEquipe.setStatutDemande(DemandeEquipe.SUSPENDUE);
            // si l'un des enregistrements échouent alors on revient à l'état initial
            if (demandeEqpDao.update(demandeEquipe)) {
              data.put(
                  "succesSuspension",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " est maintenant à la cale");
              return "suspension.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
            } else {
              data.put(
                  "erreurSuspension",
                  "Un erreur est arrivé lors de la tentative d'envoie matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " à la cale");
              return "tuRestes.do?tache=afficherPageEquipe&idEquipe=" + idEquipe;
            }
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerSuspensionMembreEquipeAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }

    return "Accueil.do?tache=afficherPageAccueil";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
