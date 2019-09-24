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

public class EffectuerModificationEquipeAction extends Action
    implements RequirePRGAction, DataSender {
  HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageAccueil";
    if (request.getParameter("idEquipe") != null) {
      action = "*.do?tache=afficherPageEquipe&idEquipe=" + request.getParameter("idEquipe");
	
      String nomEquipe = Util.toUTF8(request.getParameter("nom"));
      if (request.getParameter("modifier") != null) {
        if (userIsConnected()
            && (userIsCapitaine() || userIsAdmin())
            && nomEquipe != null) {
          try {

            int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));

            Connection cnx =
                Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
            Compte compte =
                new CompteDAO(cnx).read(((Integer) session.getAttribute("currentId")).intValue());
            EquipeDAO equipeDao = new EquipeDAO(cnx);
            Equipe equipe = equipeDao.findByNom(nomEquipe);

            if ((userIsAdmin() || compte != null) && equipe == null) {
              equipe = equipeDao.read(idEquipe);
              equipe.setNom(nomEquipe);

              if (equipeDao.update(equipe)) {
                data.put("succesNom", "L'enregistrement du nouveau nom s'est fait avec succès");
              } else {
                data.put(
                    "erreurModification",
                    "Un problème est survenu lors de la modification des informations");
              }
            } else {
              data.put(
                  "erreurNom",
                  "Le nom "
                      + nomEquipe
                      + " est déjà utilisé par un autre équipage");
            }
          } catch (NumberFormatException ex) {
            data.put("erreurNom", "Équipe inexistante.");
            Logger.getLogger(EffectuerModificationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
          } catch (SQLException ex) {
            Logger.getLogger(EffectuerModificationEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
          }
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
