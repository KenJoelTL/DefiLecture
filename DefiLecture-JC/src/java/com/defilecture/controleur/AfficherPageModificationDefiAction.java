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
import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Charles
 * @author Mikaël Nadeau
 * @author Mikaël
 * @author Mikaël Nadeau
 */
public class AfficherPageModificationDefiAction extends Action {
  @Override
  public String execute() {
    try {
      // Seuls les Capitaines et les Participants peuvent ajouter et modifier leurs lectures.
      if (session.getAttribute("connecte") != null
          && session.getAttribute("role") != null
          && request.getParameter("id") != null)
        if (((int) session.getAttribute("role") == Compte.MODERATEUR)
            || ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR)) {
          Connection cnx =
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          Defi d = new DefiDAO(cnx).read(request.getParameter("id"));

          // seul celui qui a ajouté la lecture peut la modifier
          if (d != null && d.getIdCompte() == (int) session.getAttribute("connecte"))
            request.setAttribute("vue", "pageModificationDefi.jsp");
        }

    } catch (SQLException ex) {
      Logger.getLogger(AfficherPageModificationDefiAction.class.getName())
          .log(Level.SEVERE, null, ex);
    } finally {
      Connexion.close();
    }

    return "/index.jsp";
  }
}
