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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerAjoutMembreEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageConnexion";
    if (userIsConnected()
        && request.getParameter("idEquipe") != null
        && request.getParameter("idCompte") != null
        && userIsAdmin()) {
      try {
        int idEquipe = Integer.parseInt(request.getParameter("idEquipe")),
            idCompte = Integer.parseInt(request.getParameter("idCompte"));
        boolean promouvoirCapitaine = request.getParameter("promouvoirCapitaine") != null;

        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        EquipeDAO daoEquipe = new EquipeDAO(cnx);
        CompteDAO daoCompte = new CompteDAO(cnx);
        DemandeEquipeDAO daoDE = new DemandeEquipeDAO(cnx);

        Compte compte = daoCompte.read(idCompte);
        Equipe equipe = daoEquipe.read(idEquipe);

        if (promouvoirCapitaine) {
          List<Compte> membres = daoCompte.findByIdEquipe(idEquipe);
          if (membres.stream()
              .filter(m -> m.getRole() == Compte.CAPITAINE)
              .findFirst()
              .isPresent()) {
            data.put("attentionPromouvoirCapitaine", "L'équipe a déjà un capitaine.");
          } else {
            compte.setRole(Compte.CAPITAINE);
          }
        }

        if (compte.getIdEquipe() == -1) {
          compte.setIdEquipe(idEquipe);
        } else {
          data.put("erreurMembreDejaDansEquipe", "Le participant est déjà membre d'une équipe");
          return "echec.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
        }

        if (equipe.getNbMembres() - 1 < getNbMatelotsMax()) {

          DemandeEquipe demandeEq =
              daoDE.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          if (demandeEq == null) {
            demandeEq = new DemandeEquipe();
            demandeEq.setIdCompte(compte.getIdCompte());
            demandeEq.setIdEquipe(equipe.getIdEquipe());

            if (daoDE.create(demandeEq)) {
              demandeEq = daoDE.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
            }
          }

          if (demandeEq.getStatutDemande() == DemandeEquipe.EN_ATTENTE) {
            demandeEq.setStatutDemande(DemandeEquipe.ACCEPTEE);
            if (daoDE.update(demandeEq)) {
              daoCompte.update(compte);
            } else {
              data.put("erreurDemandeNonAcceptee", "La demande n'a pas pu être acceptée.");
              return "echec.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
            }
          } else {
            data.put(
                "erreurParticipantDejaAccepter",
                "Le participant a déjà été accepté ou a été suspendu.");
            return "echec.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
          }

          Logger.getLogger(this.getClass().getName())
              .log(
                  Level.INFO,
                  "Compte "
                      + Integer.toString(compte.getIdCompte())
                      + " ajouté à l'équipe "
                      + Integer.toString(compte.getIdEquipe())
                      + (promouvoirCapitaine ? "et a été promu capitaine !" : ""));
          data.put("succesAjoutMembre", "Le membre a été ajouté.");
          return "succes.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
        } else {
          data.put("erreurEquipePleine", "L'équipe est complète.");
          return "echec.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
        }
      } catch (NumberFormatException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        data.put("erreurServeur", "Equipe ou compte introuvable.");
        return "erreur.do?tache=afficherPageModificationEquipe&idEquipe="
            + request.getParameter("idEquipe");
      } catch (SQLException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        data.put("erreurServeur", "Erreur du serveur. Veuillez contacter l'administrateur.");
        return "erreur.do?tache=afficherPageModificationEquipe&idEquipe="
            + request.getParameter("idEquipe");
      } finally {
        Connexion.close();
      }
    }
    return "*.do?tache=afficherPageConnexion";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
