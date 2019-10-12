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
package com.defilecture;

import com.defilecture.modele.ConfigSiteDAO;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import jdbc.Config;
import jdbc.Connexion;

public class EcouteurApplication implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, ("Application démarrée"));
    ServletContext application = sce.getServletContext();

    Config.DB_USER = application.getInitParameter("userDB");
    Config.DB_PWD = application.getInitParameter("passwordDB");
    Config.DRIVER = application.getInitParameter("piloteJDBC");
    Config.URL = application.getInitParameter("urlDb");

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      Logger.getLogger(EcouteurApplication.class.getName()).log(Level.SEVERE, null, e);
    }

    try {
      ConfigSiteDAO configDao =
          new ConfigSiteDAO(
              Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
      Map<String, String> config = configDao.findAllByMap();

      for (Map.Entry<String, String> entry : config.entrySet()) {
        application.setAttribute("com.defilecture." + entry.getKey(), entry.getValue());
      }

    } catch (SQLException ex) {
      Logger.getLogger(EcouteurApplication.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      Connexion.close();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, ("Application terminée"));
  }
}
