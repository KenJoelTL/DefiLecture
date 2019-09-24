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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerConnexionAction extends Action implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageConnexion";
    boolean erreur = false;

    if (request.getParameter("identifiant") != null && request.getParameter("motPasse") != null) {
      String identifiant = Util.toUTF8(request.getParameter("identifiant")),
	motPasse = Util.toUTF8(request.getParameter("motPasse"));
    
      try {
	CompteDAO dao =
	  new CompteDAO(
			Connexion.startConnection(
						  Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
	Compte compte = dao.findByPseudonyme(identifiant);

	if (compte == null) {
	  compte = dao.findByCourriel(identifiant);
	}

	if (compte != null &&
	    compte.verifierMotPasse(motPasse) &&
	    ((((Integer)compte.getRole()).intValue()==Compte.ADMINISTRATEUR ||
	      (LocalDateTime.now().isAfter(getDébutInscriptions())
	       && LocalDateTime.now().isBefore(getFinLectures()))))){
	  session = request.getSession(true);
	  session.setAttribute("connecte", compte.getIdCompte());
	  session.setAttribute("role", compte.getRole());
	  session.setAttribute("currentId", compte.getIdCompte());
	  action = "*.do?tache=afficherTableauScores";
	} else {
	  erreur = true;
	}

      if (erreur) {
	data.put("echecConnexion", "L'identifiant ou le mot de passe entrés sont invalides");
	data.put("identifiant", identifiant);
	action = "echec.do?tache=afficherPageConnexion";
      }
    } catch (SQLException ex) {
      Logger.getLogger(EffectuerConnexionAction.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      Connexion.close();
    }
  }
  return action;
}

@Override
public void setData(Map<String, Object> data) {
  this.data = (HashMap) data;
}
}
