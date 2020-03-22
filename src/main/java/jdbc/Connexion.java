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
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** @author Charles */
public class Connexion {
  private static Connection connection;
  private static String url = Config.URL;
  private static String user = Config.DB_USER;
  private static String password = Config.DB_PWD;

  public Connexion() {}

  public static Connection getInstance() throws SQLException {
    if (connection == null) {
      if (user.equals("")) connection = DriverManager.getConnection(url);
      else connection = DriverManager.getConnection(url, user, password);
    }
    return connection;
  }

  public Connection getConnection() throws SQLException, ClassNotFoundException {
    return Connexion.startConnection(user, password, url, Config.DRIVER);
  }

  public static void reinit() {
    close();
    connection = null;
  }

  public static void close() {
    try {
      if (connection != null) {
        connection.close();
        connection = null;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static String getUrl() {
    return url;
  }

  public static void setUrl(String url) {
    Connexion.url = url;
  }

  public static String getUser() {
    return user;
  }

  public static void setUser(String user) {
    Connexion.user = user;
  }

  public static void setPassword(String password) {
    Connexion.password = password;
  }

  public static void setConfig(String user, String password, String url) {
    Connexion.user = user;
    Connexion.password = password;
    Connexion.url = url;
  }

  /**
   * @param user
   * @param password
   * @param url
   * @param driver
   * @return Connection
   * @throws java.lang.SQLException
   */
  public static Connection startConnection(String user, String password, String url, String driver)
      throws SQLException {
    Connexion.user = user;
    Connexion.password = password;
    Connexion.url = url;
    return getInstance();
  }
}
