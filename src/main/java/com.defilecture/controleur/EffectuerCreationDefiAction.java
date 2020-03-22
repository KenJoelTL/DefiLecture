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
import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerCreationDefiAction extends Action implements RequirePRGAction {

  private DefiDAO dao;

  @Override
  public String execute() {
    if (userIsConnected()
        && (userIsAdmin() || userIsModerateur())
        && request.getParameter("nom") != null
        && request.getParameter("description") != null
        && request.getParameter("dateFin") != null
        && request.getParameter("heureFin") != null
        && request.getParameter("heureDebut") != null
        && request.getParameter("question") != null
        && request.getParameter("choixReponseJSON") != null
        && request.getParameter("reponse") != null
        && request.getParameter("valeurMinute") != null
        && request.getParameter("dateDebut") != null) {
      String nom = request.getParameter("nom"),
          description = request.getParameter("description"),
          dateDebut = request.getParameter("dateDebut"),
          heureDebut = request.getParameter("heureDebut"),
          dateFin = request.getParameter("dateFin"),
          heureFin = request.getParameter("heureFin"),
          question = request.getParameter("question"),
          choixReponse = request.getParameter("choixReponseJSON"),
          reponse = request.getParameter("reponse");

      int idCompte = ((Integer) session.getAttribute("currentId")).intValue(),
          valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));

      try {

        Connexion.reinit();
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        dao = new DefiDAO(cnx);
        Defi defi = new Defi();
        defi.setIdCompte(idCompte);
        defi.setNom(Util.toUTF8(nom));
        defi.setDescription(Util.toUTF8(description));
        defi.setDateDebut(dateDebut + " " + heureDebut);
        defi.setDateFin(dateFin + " " + heureFin);
        defi.setQuestion(Util.toUTF8(question));
        defi.setChoixReponse(Util.toUTF8(choixReponse));
        defi.setReponse(reponse);
        defi.setValeurMinute(valeurMinute);

        if (dao.create(defi)) {
          Logger.getLogger(this.getClass().getName())
              .log(Level.INFO, ("Un defi a été créé avec succès"));
        } else {
          Logger.getLogger(this.getClass().getName())
              .log(Level.WARNING, ("Problème de création du défi"));
        }

        return "*.do?tache=afficherPageParticipationDefi";
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerCreationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageCreationDefi";
      } finally {
        Connexion.close();
      }
    } else return "*.do?tache=afficherPageCreationDefi";
  }
}
