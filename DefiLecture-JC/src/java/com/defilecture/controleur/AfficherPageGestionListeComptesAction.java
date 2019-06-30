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

import com.defilecture.modele.CompteDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class AfficherPageGestionListeComptesAction extends Action {
  @Override
  public String execute() {
    // Exclusivement pour l'Admin et le Modérateur.
    if (userIsConnected()) {
      try {
        if (userIsAdmin() || userIsModerateur()) {
          int idCompte = (int) session.getAttribute("currentId");
          CompteDAO dao =
              new CompteDAO(
                  Connexion.startConnection(
                      Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));

          if (dao.read(idCompte) != null) {
            request.setAttribute("vue", "pageGestionListeCompte.jsp");
          }

          return "/index.jsp";
        }
      } catch (NullPointerException ex) {
        System.out.println("L'utilisateur n'existe pas");
        return "/index.jsp";
      } catch (SQLException ex) {
        Logger.getLogger(AfficherPageGestionListeComptesAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return "/index.jsp";
  }
}
