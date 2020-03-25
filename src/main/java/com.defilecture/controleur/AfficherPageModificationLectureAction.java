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

import com.defilecture.modele.Lecture;
import com.defilecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class AfficherPageModificationLectureAction extends Action {

  @Override
  public String execute() {
    try {
      // Seuls les Capitaines et les Participants peuvent ajouter et modifier leurs lectures.
      if (userIsConnected() && request.getParameter("id") != null) {
        if (userIsCapitaine() || userIsParticipant()) {
          Connection cnx =
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          Lecture lecture = new LectureDAO(cnx).read(request.getParameter("id"));

          if (lecture != null
              && lecture.getIdCompte()
                  == ((Integer) session.getAttribute("currentId")).intValue()) {
            request.setAttribute("vue", "pageModificationLecture.jsp");
          }
        }
      }

    } catch (SQLException ex) {
      Logger.getLogger(AfficherPageModificationLectureAction.class.getName())
          .log(Level.SEVERE, null, ex);
    } finally {
      Connexion.close();
    }

    return "/index.jsp";
  }
}
