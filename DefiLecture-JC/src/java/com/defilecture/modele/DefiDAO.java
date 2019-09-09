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

public class DefiDAO extends DAO<Defi> {

  public DefiDAO() {}

  public DefiDAO(Connection cnx) {
    super(cnx);
  }

  @Override
  public boolean create(Defi defi) {

    Logger.getLogger(this.getClass().getName()).log(Level.INFO, ("entrer dans le DAO"));
    String req =
        "INSERT INTO defi (`ID_COMPTE` , `NOM` , `DESCRIPTION`, `DATE_DEBUT` , `DATE_FIN`, `QUESTION`, `CHOIX_REPONSE`, `REPONSE`, `VALEUR_MINUTE`) VALUES "
            + "(?,?,?,?,?,?,?,?,?)";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, defi.getIdCompte());
      paramStm.setString(2, defi.getNom());
      paramStm.setString(3, defi.getDescription());
      paramStm.setString(4, defi.getDateDebut());
      paramStm.setString(5, defi.getDateFin());
      paramStm.setString(6, defi.getQuestion());
      paramStm.setString(7, defi.getChoixReponse());
      paramStm.setString(8, defi.getReponse());
      paramStm.setInt(9, defi.getValeurMinute());

      int n = paramStm.executeUpdate();

      if (n > 0) {
        paramStm.close();
        // stm.close();
        return true;
      }
    } catch (SQLException exp) {
      Logger.getLogger(DefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(DefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public Defi read(int id) {
    String req = "SELECT * FROM defi WHERE `ID_DEFI` = ?";

    PreparedStatement paramStm = null;

    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        Defi d = new Defi();

        d.setIdDefi(resultat.getInt("ID_DEFI"));
        d.setIdCompte(resultat.getInt("ID_COMPTE"));
        d.setNom(resultat.getString("NOM"));
        d.setDescription(resultat.getString("DESCRIPTION"));
        d.setDateDebut(resultat.getString("DATE_DEBUT"));
        d.setDateFin(resultat.getString("DATE_FIN"));
        d.setQuestion(resultat.getString("QUESTION"));
        d.setChoixReponse(resultat.getString("CHOIX_REPONSE"));
        d.setReponse(resultat.getString("REPONSE"));
        d.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));

        resultat.close();
        paramStm.close();
        return d;
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
  public boolean update(Defi defi) {
    String req =
        "UPDATE defi SET NOM = ? , DESCRIPTION = ?,"
            + "DATE_DEBUT = ?, DATE_FIN = ?, CHOIX_REPONSE = ?,"
            + "REPONSE = ?, VALEUR_MINUTE = ?, QUESTION = ? WHERE ID_DEFI = ?";

    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setString(1, defi.getNom());
      paramStm.setString(2, defi.getDescription());
      paramStm.setString(3, defi.getDateDebut());
      paramStm.setString(4, defi.getDateFin());
      paramStm.setString(5, defi.getChoixReponse());
      paramStm.setString(6, defi.getReponse());
      paramStm.setInt(7, defi.getValeurMinute());
      paramStm.setString(8, defi.getQuestion());
      paramStm.setInt(9, defi.getIdDefi());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
      Logger.getLogger(this.getClass().getName()).log(Level.INFO, (exp.getMessage()));
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public boolean delete(Defi defi) {
    String req = "DELETE FROM defi WHERE `ID_DEFI` = ?";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, defi.getIdDefi());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }

      return false;
    } catch (SQLException exp) {
    } catch (Exception exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(LectureDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public List<Defi> findAll() {

    String req = "SELECT * FROM defi";
    List<Defi> listeDefi = new ArrayList<Defi>();

    Statement stm = null;
    try {

      stm = cnx.createStatement();

      ResultSet resultat = stm.executeQuery(req);

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Defi d = new Defi();

        d.setIdDefi(resultat.getInt("ID_DEFI"));
        d.setIdCompte(resultat.getInt("ID_COMPTE"));
        d.setNom(resultat.getString("NOM"));
        d.setDescription(resultat.getString("DESCRIPTION"));
        d.setDateDebut(resultat.getString("DATE_DEBUT"));
        d.setDateFin(resultat.getString("DATE_FIN"));
        d.setQuestion(resultat.getString("QUESTION"));
        d.setChoixReponse(resultat.getString("CHOIX_REPONSE"));
        d.setReponse(resultat.getString("REPONSE"));
        d.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));

        listeDefi.add(d);
      }
      resultat.close();
      stm.close();
      return listeDefi;

    } catch (SQLException exp) {
    } finally {
      try {
        if (stm != null) stm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeDefi;
  }

  public List<Defi> findAllByIdCompte(int idCompte) {

    String req = "SELECT * FROM defi WHERE ID_COMPTE = ? ORDER BY defi.DATE_DEBUT desc";
    List<Defi> listeDefi = new ArrayList<Defi>();

    PreparedStatement paramStm = null;

    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Defi d = new Defi();

        d.setIdDefi(resultat.getInt("ID_DEFI"));
        d.setIdCompte(resultat.getInt("ID_COMPTE"));
        d.setNom(resultat.getString("NOM"));
        d.setDescription(resultat.getString("DESCRIPTION"));
        d.setDateDebut(resultat.getString("DATE_DEBUT"));
        d.setDateFin(resultat.getString("DATE_FIN"));
        d.setQuestion(resultat.getString("QUESTION"));
        d.setChoixReponse(resultat.getString("CHOIX_REPONSE"));
        d.setReponse(resultat.getString("REPONSE"));
        d.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));

        listeDefi.add(d);
      }
      resultat.close();
      paramStm.close();
      return listeDefi;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeDefi;
  }

  public List<Defi> findAllDefiEnCours() {

    String req =
        "SELECT * FROM defi WHERE defi.DATE_DEBUT < SYSDATE() AND defi.DATE_FIN > SYSDATE() ORDER BY defi.DATE_DEBUT desc";
    List<Defi> listeDefi = new ArrayList<Defi>();

    Statement stm = null;
    try {

      stm = cnx.createStatement();

      ResultSet resultat = stm.executeQuery(req);

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Defi d = new Defi();

        d.setIdDefi(resultat.getInt("ID_DEFI"));
        d.setIdCompte(resultat.getInt("ID_COMPTE"));
        d.setNom(resultat.getString("NOM"));
        d.setDescription(resultat.getString("DESCRIPTION"));
        d.setDateDebut(resultat.getString("DATE_DEBUT"));
        d.setDateFin(resultat.getString("DATE_FIN"));
        d.setQuestion(resultat.getString("QUESTION"));
        d.setChoixReponse(resultat.getString("CHOIX_REPONSE"));
        d.setReponse(resultat.getString("REPONSE"));
        d.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));

        listeDefi.add(d);
      }
      resultat.close();
      stm.close();
      return listeDefi;

    } catch (SQLException exp) {
    } finally {
      try {
        if (stm != null) stm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeDefi;
  }

  public List<Defi> findHistorique() {

    String req =
        "SELECT * FROM defi WHERE defi.DATE_DEBUT <= SYSDATE() ORDER BY defi.DATE_DEBUT desc";
    List<Defi> listeDefi = new ArrayList<>();
    ResultSet resultat;

    Statement stm = null;
    try {

      stm = cnx.createStatement();

      resultat = stm.executeQuery(req);

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Defi d = new Defi();

        d.setIdDefi(resultat.getInt("ID_DEFI"));
        d.setIdCompte(resultat.getInt("ID_COMPTE"));
        d.setNom(resultat.getString("NOM"));
        d.setDescription(resultat.getString("DESCRIPTION"));
        d.setDateDebut(resultat.getString("DATE_DEBUT"));
        d.setDateFin(resultat.getString("DATE_FIN"));
        d.setQuestion(resultat.getString("QUESTION"));
        d.setChoixReponse(resultat.getString("CHOIX_REPONSE"));
        d.setReponse(resultat.getString("REPONSE"));
        d.setValeurMinute(resultat.getInt("VALEUR_MINUTE"));
        listeDefi.add(d);
      }
      resultat.close();
      stm.close();
      return listeDefi;

    } catch (SQLException exp) {
    } finally {
      try {
        if (stm != null) stm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeDefi;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM defi";

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
      Logger.getLogger(DefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception exp) {
      Logger.getLogger(DefiDAO.class.getName()).log(Level.SEVERE, null, exp);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(DefiDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }
}
