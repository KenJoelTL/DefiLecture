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

import com.defilecture.modele.CompteDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class AfficherPageDefisReussisAction extends Action {
  @Override
  public String execute() {
    if (userIsConnected()) {
      try {
        if (userIsAdmin() || userIsModerateur()) {
          int idCompte = ((Integer) session.getAttribute("currentId")).intValue();
          CompteDAO dao =
              new CompteDAO(
                  Connexion.startConnection(
                      Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));

          if (dao.read(idCompte) != null) {
            request.setAttribute("vue", "pageDefisReussis.jsp");
          }

          return "/index.jsp";
        }
      } catch (SQLException ex) {
        Logger.getLogger(AfficherPageGestionComptesAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return "/index.jsp";
  }
}
