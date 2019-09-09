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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InscriptionDefiDAO extends DAO<InscriptionDefi> {

  public InscriptionDefiDAO() {}

  public InscriptionDefiDAO(Connection c) {
    super(c);
  }

  @Override
  public boolean create(InscriptionDefi x) {

    String req =
        "INSERT INTO inscription_defi (`ID_COMPTE` ,`ID_DEFI` ,`VALEUR_MINUTE` , `EST_REUSSI`) VALUES "
            + "(?,?,?,?)";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, x.getIdCompte());
      paramStm.setInt(2, x.getIdDefi());
      paramStm.setInt(3, x.getValeurMinute());
      paramStm.setInt(4, x.getEstReussi());

      int n = paramStm.executeUpdate();

      if (n > 0) {
        paramStm.close();
        return true;
      }
    } catch (SQLException exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public InscriptionDefi read(int id) {
    String req = "SELECT * FROM inscription_defi WHERE `ID_INSCRIPTION_DEFI` = ?";

    PreparedStatement paramStm = null;

    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        InscriptionDefi i = new InscriptionDefi();

        i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
        i.setIdCompte(resultat.getInt("ID_COMPTE"));
        i.setIdDefi(resultat.getInt("ID_DEFI"));
        i.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        i.setEstReussi(resultat.getInt("EST_REUSSI"));
        i.setDateInscription(resultat.getString("DATE_INSCRIPTION"));

        resultat.close();
        paramStm.close();
        return i;
      }
      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return null;
  }

  @Override
  public boolean update(InscriptionDefi x) {
    throw new UnsupportedOperationException(
        "Not supported yet."); // To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean delete(InscriptionDefi x) {
    String req = "DELETE FROM inscription_defi WHERE `ID_INSCRIPTION_DEFI` = ?";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, x.getIdInscriptionDefi());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public List<InscriptionDefi> findAll() {

    String req = "SELECT * FROM inscription_defi";
    List<InscriptionDefi> listeInscriptionDefi = new ArrayList<InscriptionDefi>();

    Statement stm = null;
    try {

      stm = cnx.createStatement();

      ResultSet resultat = stm.executeQuery(req);

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        InscriptionDefi i = new InscriptionDefi();

        i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
        i.setIdCompte(resultat.getInt("ID_COMPTE"));
        i.setIdDefi(resultat.getInt("ID_DEFI"));
        i.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        i.setEstReussi(resultat.getInt("EST_REUSSI"));
        i.setDateInscription(resultat.getString("DATE_INSCRIPTION"));

        listeInscriptionDefi.add(i);
      }
      resultat.close();
      stm.close();
      return listeInscriptionDefi;

    } catch (SQLException exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (stm != null) stm.close();
      } catch (SQLException exp) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return listeInscriptionDefi;
  }

  public List<InscriptionDefi> findAllByIdCompte(int idCompte) {

    String req = "SELECT * FROM inscription_defi WHERE `ID_COMPTE` = ?";
    List<InscriptionDefi> listeInscriptionDefi = new ArrayList<InscriptionDefi>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        InscriptionDefi i = new InscriptionDefi();

        i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
        i.setIdCompte(resultat.getInt("ID_COMPTE"));
        i.setIdDefi(resultat.getInt("ID_DEFI"));
        i.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        i.setEstReussi(resultat.getInt("EST_REUSSI"));
        i.setDateInscription(resultat.getString("DATE_INSCRIPTION"));

        listeInscriptionDefi.add(i);
      }
      resultat.close();
      paramStm.close();
      return listeInscriptionDefi;

    } catch (SQLException ex) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return listeInscriptionDefi;
  }

  public List<InscriptionDefi> findByDefiReussi() {

    String req = "SELECT * FROM inscription_defi WHERE `EST_REUSSI` = 1";
    List<InscriptionDefi> listeInscriptionDefi = new ArrayList<InscriptionDefi>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        InscriptionDefi i = new InscriptionDefi();

        i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
        i.setIdCompte(resultat.getInt("ID_COMPTE"));
        i.setIdDefi(resultat.getInt("ID_DEFI"));
        i.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        i.setEstReussi(resultat.getInt("EST_REUSSI"));
        i.setDateInscription(resultat.getString("DATE_INSCRIPTION"));

        listeInscriptionDefi.add(i);
      }
      resultat.close();
      paramStm.close();
      return listeInscriptionDefi;

    } catch (SQLException exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
      } catch (Exception e) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }

    return listeInscriptionDefi;
  }

  // Trouver les incription par un id d'un défi
  public List<InscriptionDefi> findByIdDefi(int id) {

    String req = "SELECT * FROM inscription_defi WHERE `ID_DEFI` = ?";
    List<InscriptionDefi> listeInscriptionDefi = new ArrayList<InscriptionDefi>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        InscriptionDefi i = new InscriptionDefi();

        i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
        i.setIdCompte(resultat.getInt("ID_COMPTE"));
        i.setIdDefi(resultat.getInt("ID_DEFI"));
        i.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        i.setEstReussi(resultat.getInt("EST_REUSSI"));
        i.setDateInscription(resultat.getString("DATE_INSCRIPTION"));

        listeInscriptionDefi.add(i);
      }
      resultat.close();
      paramStm.close();
      return listeInscriptionDefi;

    } catch (SQLException ex) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception e) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      } catch (Exception e) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, e);
      }
    }
    return listeInscriptionDefi;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM inscription_defi";

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
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception exp) {
      Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(InscriptionDefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }
}
