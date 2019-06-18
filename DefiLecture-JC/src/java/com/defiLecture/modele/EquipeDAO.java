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
package com.defiLecture.modele;

import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Joel */
public class EquipeDAO extends DAO<Equipe> {

  public EquipeDAO() {}

  public EquipeDAO(Connection cnx) {
    super(cnx);
  }

  @Override
  public boolean create(Equipe x) {
    String req = "INSERT INTO equipe (`NOM`) VALUES (?)";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, Util.toUTF8(x.getNom()));

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public Equipe read(int id) {
    String req = "SELECT * FROM equipe WHERE `ID_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        Equipe e = new Equipe();
        e.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        e.setNom(resultat.getString("NOM"));
        e.setPoint((new DemandeEquipeDAO(cnx).sumPointByidEquipe(id)));
        e.setNbMembres((new CompteDAO(cnx)).countCompteByIdEquipe(id));

        resultat.close();
        paramStm.close();
        return e;
      }

      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return null;
  }

  @Override
  public Equipe read(String id) {

    try {
      return this.read(Integer.parseInt(id));
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public boolean update(Equipe x) {
    String req = "UPDATE equipe SET `NOM` = ? WHERE `ID_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      if (x.getNom() == null || "".equals(x.getNom().trim())) paramStm.setString(1, null);
      else paramStm.setString(1, x.getNom());
      paramStm.setInt(2, x.getIdEquipe());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public boolean delete(Equipe x) {
    String req = "DELETE FROM equipe WHERE `ID_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, x.getIdEquipe());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public List<Equipe> findAll() {
    List<Equipe> liste = new ArrayList<>();
    try {
      Statement stm = cnx.createStatement();
      ResultSet r = stm.executeQuery("SELECT * FROM equipe");
      while (r.next()) {
        Equipe e = new Equipe();
        e.setIdEquipe(r.getInt("ID_EQUIPE"));
        e.setNom(r.getString("NOM"));

        // appelle les DAO DEMANDE Compte et DemandeEquipe
        e.setPoint(new DemandeEquipeDAO(cnx).sumPointByidEquipe(r.getInt("ID_EQUIPE")));
        e.setNbMembres(new CompteDAO(cnx).countCompteByIdEquipe(r.getInt("ID_EQUIPE")));

        liste.add(e);
      }
      Collections.sort(liste);
      Collections.reverse(liste);
      r.close();
      stm.close();
    } catch (SQLException exp) {
    }
    return liste;
  }

  public List<Equipe> findAllByNom(String nom) throws SQLException {
    List<Equipe> liste = new ArrayList<>();
    String req = "SELECT * FROM equipe WHERE `NOM` LIKE ?";
    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, "%" + Util.toUTF8(nom) + "%");

      ResultSet r = paramStm.executeQuery();
      while (r.next()) {
        Equipe e = new Equipe();
        e.setIdEquipe(r.getInt("ID_EQUIPE"));
        e.setNom(r.getString("NOM"));

        // appelle les DAO DEMANDE Compte et DemandeEquipe
        e.setPoint(new DemandeEquipeDAO(cnx).sumPointByidEquipe(r.getInt("ID_EQUIPE")));
        e.setNbMembres(new CompteDAO(cnx).countCompteByIdEquipe(r.getInt("ID_EQUIPE")));

        liste.add(e);
      }
      Collections.sort(liste);
      Collections.reverse(liste);
      r.close();
      paramStm.close();
    } catch (SQLException exp) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, exp);
      throw exp;
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return liste;
  }

  public Equipe findByNom(String nom) {
    String req = "SELECT * FROM equipe WHERE `NOM` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, Util.toUTF8(nom));

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        Equipe e = new Equipe();
        e.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        e.setNom(resultat.getString("NOM"));
        e.setPoint((new DemandeEquipeDAO(cnx).sumPointByidEquipe(resultat.getInt("ID_EQUIPE"))));
        e.setNbMembres((new CompteDAO(cnx)).countCompteByIdEquipe(resultat.getInt("ID_EQUIPE")));

        resultat.close();
        paramStm.close();
        return e;
      }

      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return null;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM equipe";

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
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception e) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }
}
