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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/** @author Charles */
public class EffectuerSuppressionLectureAction
    implements Action, SessionAware, RequestAware, RequirePRGAction {
  HttpServletRequest request;
  HttpServletResponse response;
  HttpSession session;

  @Override
  public String execute() {
    if (session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && (((int) session.getAttribute("role") == Compte.PARTICIPANT)
            || ((int) session.getAttribute("role") == Compte.CAPITAINE))) {

      String idLecture = request.getParameter("idLecture");
      int idCompte = (int) session.getAttribute("connecte");

      try {

        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        LectureDAO daoLecture = new LectureDAO(cnx);
        Lecture lecture = daoLecture.read(idLecture);
        if (lecture == null) {

          return "*.do?tache=afficherPageGestionLecture";
        } else {

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

          if (!daoLecture.delete(lecture)) {

            return "*.do?tache=afficherPageGestionLecture";
          } else {

            return "*.do?tache=afficherPageGestionLecture";
          }
        }
      } catch (ClassNotFoundException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageGestionLecture";
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
      }
    } else return "*.do?tache=afficherPageGestionLecture";
    return null;
  }

  @Override
  public void setSession(HttpSession session) {
    this.session = session;
  }

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }
}
