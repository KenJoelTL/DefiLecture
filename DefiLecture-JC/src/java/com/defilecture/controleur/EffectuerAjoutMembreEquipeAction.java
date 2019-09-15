
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
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerAjoutMembreEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected()
        && request.getParameter("idEquipe") != null
        && request.getParameter("idCompte") != null
        && userIsAdmin()) {

      int idEquipe = ((Integer) request.getParameter("idEquipe")).intValue(),
            idCompte = ((Integer) request.getParameter("idCompte")).intValue();
      boolean promouvoirCapitaine = request.getParameter("promouvoirCapitaine") != null;

      try {
        Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        EquipeDAO daoEquipe = new EquipeDAO(cnx);
        CompteDAO daoCompte = new CompteDAO(cnx);

        Compte compte = daoCompte.read(idCompte);
        Equipe equipe = daoEquipe.read(idEquipe);

        if(promouvoirCapitaine) {
          List<Compte> membres = daoCompte.findByIdEquipe(idEquipe);
          if(!list.stream().filter(m -> m.getRole() == Compte.CAPITAINE).findFirst().isPresent()) {
            compte.setRole(Compte.CAPITAINE);
          } else {
            data.put(
                "warningPromouvoirCapitaine",
                "L'équipe a déjà un capitaine.");
          }
        }

        if(compte.getIdEquipe() == -1) {
          compte.setIdEquipe(idEquipe);
        } else {
          data.put(
              "erreurMembreDejaDansEquipe",
              "Le participant est déjà membre d'une équipe");
          return "erreur.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
        }

        if(equipe.getNbMembres() < Equipe.NB_MAX_MEMBRES) {
          daoCompte.update(compte);
          Logger.getLogger(this.getClass().getName())
            .log(Level.INFO, 
                "Compte " + Integer.toString(compte.getIdCompte())
                + " ajouté à l'équipe " + Integer.toString(compte.getIdEquipe())
                + (promouvoirCapitaine ? "et a été promu capitaine !" : ""));
          data.put(
              "successAjoutMembre",
              "Le membre a été ajouté.");
          return "succes.do?tache=afficherPageGestionEquipes?idEquipe="+idEquipe;
        } else {
          data.put(
              "erreurEquipePleine",
              "L'équipe est complète.");
          return "erreur.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
        }
      } catch (SQLException ex){
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        data.put(
          "erreurServeur",
          "Erreur du serveur. Veuillez contacter l'administrateur.");
        return "erreur.do?tache=afficherPageModificationEquipe&idEquipe=" + idEquipe;
      } finally {
        Connexion.close();
      }
    }
    return "/";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
