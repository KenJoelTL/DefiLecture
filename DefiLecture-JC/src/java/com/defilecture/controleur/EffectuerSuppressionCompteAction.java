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

/** @author Joel */
public class EffectuerSuppressionCompteAction
    implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {

  private HttpServletResponse reponse;
  private HttpServletRequest request;
  private HttpSession session;
  private HashMap data;

  @Override
  public String execute() {
    String action = "Acceuil.do?tache=afficherPageAccueil";
    if (session.getAttribute("connecte") == null
        || session.getAttribute("role") == null
        || request.getParameter("idCompte") == null) {
    } else if (!request.getParameter("idCompte").equals(session.getAttribute("connecte") + "")
        && ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR)) {
      try {
        String idCompte = request.getParameter("idCompte");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        CompteDAO compteDao = new CompteDAO(cnx);
        Compte compte = compteDao.read(idCompte);

        if (compte != null) {
          if (compteDao.delete(compte)) {
            action = "succes.do?tache=afficherPageGestionListeCompte";
            data.put(
                "suppressionSucces", "Le compte " + compte.getCourriel() + " a bien été supprimé");
          } else {
            action = "echec.do?tache=afficherPageGestionListCompte";
            data.put("suppressionEchec", "Une erreur est survenue lors de la suppression");
          }

        } else {
          action = "echec.do?tache=afficherPageGestionListCompte";
          data.put("suppressionEchec", "Le compte que vous tentez de supprimer n'existe pas");
        }

      } catch (ClassNotFoundException ex) {
        Logger.getLogger(EffectuerDepartEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerSuppressionCompteAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }

    return action;
  }

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.reponse = response;
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
