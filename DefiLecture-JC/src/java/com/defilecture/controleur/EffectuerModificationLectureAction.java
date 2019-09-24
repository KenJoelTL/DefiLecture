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
import com.defilecture.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerModificationLectureAction extends Action implements RequirePRGAction {

  @Override
  public String execute() {
    if (userIsConnected()
        && (userIsCapitaine() || userIsParticipant())
        && request.getParameter("modifie") != null) {

	if (LocalDateTime.now().isBefore(getDébutLectures())
	    || LocalDateTime.now().isAfter(getFinLectures())) {
        return "*.do?tache=afficherPageGestionLecture";
      }

      String idLecture = request.getParameter("idLecture");
      String titre = Util.toUTF8(request.getParameter("titre"));

      int dureeMinutes;
      int estObligatoire = Integer.parseInt(request.getParameter("obligatoire"));

      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        LectureDAO dao = new LectureDAO(cnx);
        Lecture lecture = dao.read(idLecture);
        if (lecture != null) {
          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          dao.setCnx(cnx);

          if (request.getParameter("dureeMinutes") != null) {
            dureeMinutes = Integer.parseInt(request.getParameter("dureeMinutes"));
            if (dureeMinutes != lecture.getDureeMinutes()) {
              lecture.setDureeMinutes(dureeMinutes);
            }
          }

          if (estObligatoire != lecture.getEstObligatoire()) {
            lecture.setEstObligatoire(estObligatoire);
          }

          if (titre != null && !"".equals(titre.trim()) && !titre.equals(lecture.getTitre())) {
            lecture.setTitre(titre);
          }

          if (!dao.update(lecture)) {
            return "*.do?tache=afficherPageAccueil";
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageGestionLecture";
      } catch (NumberFormatException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageGestionLecture";
      }
    }
    return "*.do?tache=afficherPageGestionLecture";
  }
}
