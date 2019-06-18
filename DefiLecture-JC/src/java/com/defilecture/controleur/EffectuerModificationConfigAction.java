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

import com.defilecture.modele.ConfigSite;
import com.defilecture.modele.ConfigSiteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/** @author usager */
public class EffectuerModificationConfigAction
    implements Action, RequestAware, SessionAware, RequirePRGAction {
  private HttpServletResponse response;
  private HttpServletRequest request;
  private HttpSession session;
  private ConfigSite configUpdate, configCreate, verification;
  private ConfigSiteDAO configDAO;

  @Override
  public String execute() {
    if ((session.getAttribute("connecte") != null && session.getAttribute("role") != null)
        && (int) session.getAttribute("role") >= 3) {
      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        List<String> parameterNames = new ArrayList<>(request.getParameterMap().keySet());
        configDAO = new ConfigSiteDAO(cnx);
        configUpdate = new ConfigSite();
        configCreate = new ConfigSite();
        verification = new ConfigSite();
        // Modification et création des paramètres dans la même action
        for (String id : parameterNames) {
          verification = configDAO.read(id);
          // Vérification que l'input n'est pas vide
          String verif = request.getParameter(id).trim();
          if (verification != null && !"tache".equals(id) && !verif.isEmpty()) {
            configUpdate.getConfig().put(id, request.getParameter(id));
          }
          if (verification == null && !"tache".equals(id) && !verif.isEmpty()) {
            configCreate.getConfig().put(id, request.getParameter(id));
          }
        }
        configDAO.update(configUpdate);
        configDAO.create(configCreate);
      } catch (ClassNotFoundException ex) {
        Logger.getLogger(EffectuerModificationConfigAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerModificationConfigAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return "*.do?tache=afficherPageConfiguration";
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
