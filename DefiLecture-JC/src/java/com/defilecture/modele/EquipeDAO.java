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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EquipeDAO extends DAO<Equipe> {

  public EquipeDAO() {}

  public EquipeDAO(Connection cnx) {
    super(cnx);
  }

  @Override
  public boolean create(Equipe x) {
    String req = "INSERT INTO equipe (`NOM`) VALUES (?)";
    boolean isCreer = false;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, x.getNom());

      if (paramStm.executeUpdate() > 0) {
        isCreer = true;
      }

      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return isCreer;
  }

  @Override
  public Equipe read(int id) {
    String req = "SELECT * FROM equipe WHERE `ID_EQUIPE` = ?";
    Equipe equipe = null;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, id);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) {
        equipe = new Equipe();
        equipe.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        equipe.setNom(resultat.getString("NOM"));
        equipe.setPoint((new DemandeEquipeDAO(cnx).sumPointByidEquipe(id)));
        equipe.setNbMembres((new CompteDAO(cnx)).countCompteByIdEquipe(id));
      }

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return equipe;
  }

  @Override
  public boolean update(Equipe x) {
    String req = "UPDATE equipe SET `NOM` = ? WHERE `ID_EQUIPE` = ?";
    boolean isUpdated = false;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, x.getNom() == null || "".equals(x.getNom().trim()) ? null : x.getNom());
      paramStm.setInt(2, x.getIdEquipe());

      if (paramStm.executeUpdate() > 0) {
        isUpdated = true;
      }
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  @Override
  public boolean delete(Equipe x) {
    String req = "DELETE FROM equipe WHERE `ID_EQUIPE` = ?";
    boolean isDeleted = false;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, x.getIdEquipe());

      if (paramStm.executeUpdate() > 0) {
        isDeleted = true;
      }
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return isDeleted;
  }

  @Override
  public List<Equipe> findAll() {
    String req = "SELECT * FROM equipe";
    List<Equipe> liste = new ArrayList<>();

    try {
      Statement stm = cnx.createStatement();
      ResultSet r = stm.executeQuery(req);

      while (r.next()) {
        Equipe equipe = new Equipe();
        equipe.setIdEquipe(r.getInt("ID_EQUIPE"));
        equipe.setNom(r.getString("NOM"));

        equipe.setPoint(new DemandeEquipeDAO(cnx).sumPointByidEquipe(r.getInt("ID_EQUIPE")));
        equipe.setNbMembres(new CompteDAO(cnx).countCompteByIdEquipe(r.getInt("ID_EQUIPE")));

        liste.add(equipe);
      }

      Collections.sort(liste);
      Collections.reverse(liste);
      r.close();
      stm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return liste;
  }

  public List<Equipe> findAllByNom(String nom) throws SQLException {
    String req = "SELECT * FROM equipe WHERE `NOM` LIKE ?";
    List<Equipe> liste = new ArrayList<>();

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, "%" + nom + "%");

      ResultSet r = paramStm.executeQuery();

      while (r.next()) {
        Equipe e = new Equipe();
        e.setIdEquipe(r.getInt("ID_EQUIPE"));
        e.setNom(r.getString("NOM"));

        e.setPoint(new DemandeEquipeDAO(cnx).sumPointByidEquipe(r.getInt("ID_EQUIPE")));
        e.setNbMembres(new CompteDAO(cnx).countCompteByIdEquipe(r.getInt("ID_EQUIPE")));

        liste.add(e);
      }

      Collections.sort(liste);
      Collections.reverse(liste);
      r.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      throw ex;
    }

    return liste;
  }

  public Equipe findByNom(String nom) {
    String req = "SELECT * FROM equipe WHERE `NOM` = ?";
    Equipe equipe = null;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, nom);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) {
        equipe = new Equipe();
        equipe.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        equipe.setNom(resultat.getString("NOM"));
        equipe.setPoint(
            (new DemandeEquipeDAO(cnx).sumPointByidEquipe(resultat.getInt("ID_EQUIPE"))));
        equipe.setNbMembres(
            (new CompteDAO(cnx)).countCompteByIdEquipe(resultat.getInt("ID_EQUIPE")));
      }

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return equipe;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM equipe";
    boolean isDeleted = false;

    try {
      PreparedStatement paramStm = cnx.prepareStatement(req);

      if (paramStm.executeUpdate() > 0) {
        isDeleted = true;
      }

      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(EquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return isDeleted;
  }
}
