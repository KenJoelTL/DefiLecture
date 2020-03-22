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
package com.defilecture.modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigSiteDAO extends DAO<ConfigSite> {

  public ConfigSiteDAO() {}

  public ConfigSiteDAO(Connection c) {
    super(c);
  }

  @Override
  public boolean create(ConfigSite x) {
    return update(x);
  }

  @Override
  public ConfigSite read(String id) {
    String req = "SELECT * FROM config_site WHERE `ID_CONFIG` = ?";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setString(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        ConfigSite config = new ConfigSite();

        config.getConfig().put(id, resultat.getString("VALUE_CONFIG"));

        resultat.close();
        paramStm.close();
        return config;
      }
      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) {
          paramStm.close();
        }
      } catch (SQLException exp) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return null;
  }

  @Override
  public ConfigSite read(int id) {
    return null;
  }

  @Override
  public boolean update(ConfigSite x) {
    String req = "REPLACE INTO config_site(ID_Config, VALUE_CONFIG) VALUES (?, ?)";

    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);

      for (String cle : x.getConfig().keySet()) {
        paramStm.setString(1, cle);
        paramStm.setString(2, x.getConfig().get(cle));
        paramStm.executeUpdate();
      }
      int n = paramStm.executeUpdate();
      if (n > 0) {
        paramStm.close();
        // stm.close();
        return true;
      }
      return false;
    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) {
          paramStm.close();
        }
      } catch (SQLException ex) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM config_site";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) {
          paramStm.close();
        }
      } catch (SQLException ex) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public boolean delete(ConfigSite x) {
    String req = "DELETE FROM config_site WHERE `ID_Config` = ?";

    PreparedStatement paramStm = null;

    try {
      for (String cle : x.getConfig().keySet()) {
        paramStm.setString(1, cle);
        paramStm = cnx.prepareStatement(req);

        int nbLignesAffectees = paramStm.executeUpdate();

        if (nbLignesAffectees > 0) {
          paramStm.close();
          return true;
        }
      }
      return false;
    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) {
          paramStm.close();
        }
      } catch (SQLException ex) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public List<ConfigSite> findAll() {

    return null;
  }

  public Map<String, String> findAllByMap() {

    String req = "SELECT * FROM config_site";
    ConfigSite config = new ConfigSite();

    Statement stm = null;
    try {

      stm = cnx.createStatement();

      ResultSet resultat = stm.executeQuery(req);
      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        config.getConfig().put(resultat.getString("ID_CONFIG"), resultat.getString("VALUE_CONFIG"));
      }
      resultat.close();
      stm.close();
      return config.getConfig();

    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (stm != null) {
          stm.close();
        }
      } catch (SQLException exp) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return config.getConfig();
  }

  public String getString(String id) {

    String req = "SELECT VALUE_CONFIG FROM config_site WHERE `ID_Config` = ?";
    String valeur = "";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setString(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        valeur = resultat.getString("VALUE_CONFIG");

        resultat.close();
        paramStm.close();
        return valeur;
      }
      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
      Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) {
          paramStm.close();
        }
      } catch (SQLException exp) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(ConfigSiteDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return null;
  }
}
