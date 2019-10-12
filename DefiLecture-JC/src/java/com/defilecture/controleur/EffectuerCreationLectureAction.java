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
import com.defilecture.modele.Lecture;
import com.defilecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerCreationLectureAction extends Action implements RequirePRGAction {

  @Override
  public String execute() {

    if (userIsConnected()
        && (userIsParticipant() || userIsCapitaine())
        && request.getParameter("titre") != null
        && request.getParameter("dureeMinutes") != null
        && request.getParameter("obligatoire") != null) {

      if (LocalDateTime.now().isBefore(getDébutLectures())
          || LocalDateTime.now().isAfter(getFinLectures())) {
        return "*.do?tache=afficherPageGestionLectures";
      }

      String titre = Util.toUTF8(request.getParameter("titre"));
      int dureeMinutes = Integer.parseInt(request.getParameter("dureeMinutes"));
      int obligatoire = Integer.parseInt(request.getParameter("obligatoire"));
      int idCompte = ((Integer) session.getAttribute("currentId")).intValue();

      // Vérifie la limite de lectures
      if (dureeMinutes <= 0) {
        return "*.do?tache=afficherPageGestionLectures";
      }

      Lecture lecture;

      try {

        Connexion.reinit();
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        LectureDAO dao;
        dao = new LectureDAO(cnx);

        // Vérifie la limite absolue de temps de lecture quotidien
        if (dao.getMinutesAjd(idCompte) + dureeMinutes > getLimiteLectureHard()) {
          return "*.do?tache=afficherPageGestionLectures";
        }

        lecture = new Lecture();
        lecture.setIdCompte(idCompte);
        lecture.setDureeMinutes(dureeMinutes);
        lecture.setTitre(titre);
        lecture.setEstObligatoire(obligatoire);
        if (dao.create(lecture)) {

          // Mise à jour des points du participant
          // Conversion du nombre de minutes de la lecture en points pour le Participant :
          // 15mins =
          // 1 point
          CompteDAO daoCompte = new CompteDAO(cnx);
          Compte compte = new Compte();
          compte = daoCompte.read(idCompte);
          if (lecture.getEstObligatoire() == Lecture.NON_OBLIGATOIRE) {
            dureeMinutes *= 2;
          }
          int pointLecture = (dureeMinutes + compte.getMinutesRestantes()) / 15;
          int pointCompte = compte.getPoint() + pointLecture;
          // Les minutes restantes sont gardées en mémoire ici
          int minutesRestantes = (dureeMinutes + compte.getMinutesRestantes()) % 15;
          compte.setPoint(pointCompte);
          compte.setMinutesRestantes(minutesRestantes);
          daoCompte.update(compte);
          // Mise à jour des points dans demande_equipe (pour calculer le total des points
          // de
          // l'équipe)
          if (compte.getIdEquipe() > 0) {
            DemandeEquipeDAO demandeDAO = new DemandeEquipeDAO(cnx);
            DemandeEquipe demande = new DemandeEquipe();
            demande = demandeDAO.findByIdCompteEquipe(idCompte, compte.getIdEquipe());
            int pointDemandeEquipe = demande.getPoint() + pointLecture;
            demande.setPoint(pointDemandeEquipe);
            demandeDAO.update(demande);

            Logger.getLogger(this.getClass().getName())
                .log(Level.INFO, ("Une lecture a été créée avec succès"));

          } else {
            Logger.getLogger(this.getClass().getName())
                .log(Level.WARNING, ("Problème de création de la lecture"));
          }
        }

      } catch (SQLException ex) {
        Logger.getLogger(EffectuerCreationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
      }
    }
    return "*.do?tache=afficherPageGestionLectures";
  }
}
