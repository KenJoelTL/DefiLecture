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

import com.defilecture.Util;
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

public class EffectuerCreationEquipeAction extends Action implements RequirePRGAction, DataSender {
  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected()) {
      if (userIsCapitaine()) {
        int idCompte = ((Integer) session.getAttribute("currentId")).intValue();
        String nom = Util.toUTF8(request.getParameter("nom"));
        if (nom != null && !"".equals(nom.trim())) {
          Equipe equipe = new Equipe();
          equipe.setNom(nom);

          try {
            Connection cnx =
                Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
            EquipeDAO daoEquipe = new EquipeDAO(cnx);

            if (daoEquipe.findByNom(equipe.getNom()) == null) {
              if (daoEquipe.create(equipe)) {
                equipe = daoEquipe.findByNom(equipe.getNom());
                Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                    .log(
                        Level.INFO,
                        "Équipe {ID: "
                            + Integer.toString(equipe.getIdEquipe())
                            + ", Nom: "
                            + equipe.getNom()
                            + "} créée.");
              } else {
                data.put("erreurNom", "Équipe non créée.");
                Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                    .log(
                        Level.INFO,
                        "Erreur lors de la création de l'équipe " + equipe.getNom() + ".");
                return "creation.do?tache=afficherPageCreationEquipe";
              }

              CompteDAO daoCompte = new CompteDAO(cnx);
              Compte compte = daoCompte.read(idCompte);
              compte.setIdEquipe(equipe.getIdEquipe());
              Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                  .log(
                      Level.INFO,
                      "Ajout du compte " + idCompte + " à l'équipe " + equipe.getIdEquipe());

              if (daoCompte.update(compte)) {
                DemandeEquipeDAO daoDemandeEquipe = new DemandeEquipeDAO(cnx);
                DemandeEquipe demande = new DemandeEquipe();
                demande.setIdCompte(compte.getIdCompte());
                demande.setIdEquipe(compte.getIdEquipe());

                demande.setStatutDemande(1);
                demande.setPoint(compte.getPoint());

                if (daoDemandeEquipe.create(demande)) {
                  Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                      .log(Level.INFO, "Demande complétée");
                  return "creationEquipeCompletee.do?tache=afficherPageEquipe&idEquipe="
                      + equipe.getIdEquipe();
                }
              }
            } else {
              data.put("erreurNom", "Ce nom est déjà utilisé par un équipage");
              return "creation.do?tache=afficherPageCreationEquipe";
            }
          } catch (SQLException ex) {
            Logger.getLogger(EffectuerCreationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
            return "creation.do?tache=afficherPageCreationEquipe";

          } finally {
            Connexion.close();
          }
        }
        return "creation.do?tache=afficherPageCreationEquipe";
      }
      return "creation.do?tache=afficherPageAccueil";
    }
    return "connexion.do?tache=afficherPageConnexion";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
