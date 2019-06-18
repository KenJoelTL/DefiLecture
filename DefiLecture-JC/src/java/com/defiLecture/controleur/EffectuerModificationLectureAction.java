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
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.Lecture;
import com.defiLecture.modele.LectureDAO;
import com.util.Util;
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
public class EffectuerModificationLectureAction
    implements Action, RequestAware, RequirePRGAction, SessionAware {
  private HttpSession session;
  private HttpServletResponse response;
  private HttpServletRequest request;

  @Override
  public String execute() {

    if (session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && (((int) session.getAttribute("role") == Compte.PARTICIPANT)
            || ((int) session.getAttribute("role") == Compte.CAPITAINE))
        && request.getParameter("modifie") != null) {
      String idLecture = request.getParameter("idLecture"), titre = request.getParameter("titre");

      int dureeMinutes, estObligatoire = Integer.parseInt(request.getParameter("obligatoire"));

      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        LectureDAO dao = new LectureDAO(cnx);
        Lecture lecture = dao.read(idLecture);
        if (lecture == null)
          // request.setAttribute("vue", "pageProfil.jsp");
          return "*.do?tache=afficherPageGestionLecture";
        else {
          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          dao.setCnx(cnx);

          if (request.getParameter("dureeMinutes") != null) {
            try {
              dureeMinutes = Integer.parseInt(request.getParameter("dureeMinutes"));
              if (dureeMinutes != lecture.getDureeMinutes()) lecture.setDureeMinutes(dureeMinutes);
            } catch (NumberFormatException e) {
            }
          }

          if (estObligatoire != lecture.getEstObligatoire())
            lecture.setEstObligatoire(estObligatoire);

          if (titre != null && !"".equals(titre.trim()) && !titre.equals(lecture.getTitre()))
            lecture.setTitre(Util.toUTF8(titre));

          if (!dao.update(lecture)) {

            // request.setAttribute("vue", "accueil.jsp");
            return "*.do?tache=afficherPageAccueil";
          } else {
            // il faut avertir que les changements ont étés faits
            //    request.setAttribute("vue", "pageProfil.jsp"); //faire PRG
            return "*.do?tache=afficherPageGestionLecture";
          }
        }
      } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageGestionLecture";
      }
    } else return "*.do?tache=afficherPageGestionLecture";
  }

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }

  @Override
  public void setSession(HttpSession session) {
    this.session = session;
  }
}
