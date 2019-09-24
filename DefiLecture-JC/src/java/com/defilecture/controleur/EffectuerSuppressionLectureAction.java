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
import com.defilecture.modele.Lecture;
import com.defilecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerSuppressionLectureAction extends Action implements RequirePRGAction {

  @Override
  public String execute() {
    if (userIsConnected() && (userIsCapitaine() || userIsParticipant())) {

	if (LocalDateTime.now().isBefore(getDébutLectures())
	    || LocalDateTime.now().isAfter(getFinLectures())) {
        return "*.do?tache=afficherPageGestionLecture";
      }

      String idLecture = request.getParameter("idLecture");
      int idCompte = ((Integer) session.getAttribute("currentId")).intValue();

      try {

        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        LectureDAO daoLecture = new LectureDAO(cnx);
        Lecture lecture = daoLecture.read(idLecture);
        if (lecture != null) {
          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

          CompteDAO daoCompte = new CompteDAO(cnx);
          Compte compte = new Compte();
          compte = daoCompte.read(idCompte);

          if (lecture.getEstObligatoire() == Lecture.NON_OBLIGATOIRE) {
            lecture.setDureeMinutes(lecture.getDureeMinutes() * 2);
          }

          int pointLecture = (lecture.getDureeMinutes()) / 15;
          compte.setPoint(compte.getPoint() - pointLecture);

          daoCompte.update(compte);

          if (compte.getIdEquipe() > 0) {
            DemandeEquipeDAO demandeDAO = new DemandeEquipeDAO(cnx);
            DemandeEquipe demande = new DemandeEquipe();
            demande = demandeDAO.findByIdCompteEquipe(idCompte, compte.getIdEquipe());
            int pointDemandeEquipe = demande.getPoint() - pointLecture;
            demande.setPoint(pointDemandeEquipe);
            demandeDAO.update(demande);
          }

          daoLecture.setCnx(cnx);

          daoLecture.delete(lecture);
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
      }
    }
    return "*.do?tache=afficherPageGestionLecture";
  }
}
