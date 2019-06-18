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
import com.util.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/** @author Charles */
public class EffectuerModificationDefiAction
    implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {

  private HttpSession session;
  private HttpServletResponse response;
  private HttpServletRequest request;
  private HashMap data;

  @Override
  public String execute() {

    if (session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && (((int) session.getAttribute("role") == Compte.MODERATEUR)
            || ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR))
        && request.getParameter("modifie") != null) {

      String nom = request.getParameter("nom"),
          description = request.getParameter("description"),
          heureDebut = request.getParameter("heureDebut"),
          dateDebut = request.getParameter("dateDebut") + " " + heureDebut,
          heureFin = request.getParameter("heureFin"),
          dateFin = request.getParameter("dateFin") + " " + heureFin,
          question = request.getParameter("question"),
          reponse = request.getParameter("reponse"),
          choixReponse = request.getParameter("choixReponseJSON"),
          idDefi = request.getParameter("idDefi");

      int valeurMinute;

      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        Defi defi = new DefiDAO(cnx).read(idDefi);
        if (defi == null) {

          return "*.do?tache=afficherPageParticipationDefi";
        } else {

          if (nom != null && !"".equals(nom.trim()) && !nom.equals(defi.getNom())) {
            defi.setNom(Util.toUTF8(nom));
          }

          if (request.getParameter("valeurMinute") != null) {
            try {
              valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));
              if (valeurMinute != defi.getValeurMinute()) {
                defi.setValeurMinute(valeurMinute);
              }
            } catch (NumberFormatException e) {
            }
          }

          if (dateDebut != defi.getDateDebut()) {
            defi.setDateDebut(dateDebut);
          }
          if (dateFin != defi.getDateFin()) {
            defi.setDateFin(dateFin);
          }

          if (description != null
              && !"".equals(description.trim())
              && !description.equals(defi.getDescription())) {
            defi.setDescription(Util.toUTF8(description));
          }

          if (question != null
              && !"".equals(question.trim())
              && !question.equals(defi.getQuestion())) {
            defi.setQuestion(Util.toUTF8(question));
          }

          if (choixReponse != defi.getChoixReponse()) {
            defi.setChoixReponse(Util.toUTF8(choixReponse));
          }
          if (reponse != defi.getReponse()) {
            defi.setReponse(Util.toUTF8(reponse));
          }

          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

          DefiDAO dao = new DefiDAO(cnx);
          if (!dao.update(defi)) {
            return "*.do?tache=afficherPageParticipationDefi";
          } else {
            return "*.do?tache=afficherPageParticipationDefi";
          }
        }
      } catch (ClassNotFoundException ex) {
        Logger.getLogger(EffectuerModificationLectureAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageParticipationDefi";
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationDefiAction.class.getName())
            .log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageParticipationDefi";
      }
    } else {
      return "*.do?tache=afficherPageParticipationDefi";
    }
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

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
