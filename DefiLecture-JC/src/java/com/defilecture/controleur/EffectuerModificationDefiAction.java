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

import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import com.defilecture.Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerModificationDefiAction extends Action
  implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {

    if (userIsConnected()
	&& (userIsAdmin() || userIsModerateur())
	&& request.getParameter("modifie") != null) {

      String nom = request.getParameter("nom");
      String description = request.getParameter("description");
      String heureDebut = request.getParameter("heureDebut");
      String dateDebut = request.getParameter("dateDebut") + " " + heureDebut;
      String heureFin = request.getParameter("heureFin");
      String dateFin = request.getParameter("dateFin") + " " + heureFin;
      String question = request.getParameter("question");
      String reponse = request.getParameter("reponse");
      String choixReponse = request.getParameter("choixReponseJSON");
      String idDefi = request.getParameter("idDefi");

      int valeurMinute;

      try {
	Connection cnx =
	  Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

	Defi defi = new DefiDAO(cnx).read(idDefi);
	if (defi != null) {

	  if (nom != null && !"".equals(nom.trim()) && !nom.equals(defi.getNom())) {
	    defi.setNom(Util.toUTF8(nom));
	  }

	  if (request.getParameter("valeurMinute") != null) {
	    valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));
	    if (valeurMinute != defi.getValeurMinute()) {
	      defi.setValeurMinute(valeurMinute);
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
	    defi.setReponse(reponse);
	  }

	  cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

	  DefiDAO dao = new DefiDAO(cnx);
	  if (dao.update(defi)) {
	    return "*.do?tache=afficherPageParticipationDefi";
	  } else {
	    Logger.getLogger(EffectuerModificationDefiAction.class.getName())
	      .log(Level.WARNING, "Mise à jour défi échouée.");
	    return "*.do?tache=afficherPageParticipationDefi";
	  }
	}
      } catch (SQLException ex) {
	Logger.getLogger(EffectuerModificationDefiAction.class.getName())
	  .log(Level.SEVERE, null, ex);
	return "*.do?tache=afficherPageParticipationDefi";
      } catch (NumberFormatException ex) {
	Logger.getLogger(EffectuerModificationDefiAction.class.getName())
	  .log(Level.SEVERE, null, ex);
	return "*.do?tache=afficherPageParticipationDefi";
      }
    }
    return "*.do?tache=afficherPageParticipationDefi";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
