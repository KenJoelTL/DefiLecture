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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerSuppressionCompteAction extends Action
    implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected() && userIsAdmin() && request.getParameter("idCompte") != null) {
      if (!request.getParameter("idCompte").equals(session.getAttribute("currentId"))
          && userIsAdmin()) {
        try {
          String idCompte = request.getParameter("idCompte");
          Connection cnx =
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          CompteDAO compteDao = new CompteDAO(cnx);
          Compte compte = compteDao.read(idCompte);

          if (compte != null) {
            if (compteDao.delete(compte)) {
              data.put(
                  "suppressionSucces",
                  "Le compte " + compte.getCourriel() + " a bien été supprimé");
              return "succes.do?tache=afficherPageGestionListeCompte";
            } else {
              data.put("suppressionEchec", "Une erreur est survenue lors de la suppression");
              return "echec.do?tache=afficherPageGestionListCompte";
            }

          } else {
            data.put("suppressionEchec", "Le compte que vous tentez de supprimer n'existe pas");
            return "echec.do?tache=afficherPageGestionListCompte";
          }

        } catch (SQLException ex) {
          Logger.getLogger(EffectuerSuppressionCompteAction.class.getName())
              .log(Level.SEVERE, null, ex);
        } finally {
          Connexion.close();
        }
      }
    }

    return "Accueil.do?tache=afficherPageAccueil";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
