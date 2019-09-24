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

public class LectureDAO extends DAO<Lecture> {

  public LectureDAO() {}

  public LectureDAO(Connection c) {
    super(c);
  }

  @Override
  public boolean create(Lecture x) {

    String req =
        "INSERT INTO lecture (`ID_COMPTE` , `TITRE` , `DUREE_MINUTES`, `EST_OBLIGATOIRE`) VALUES "
            + "(?,?,?,?)";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, x.getIdCompte());
      paramStm.setString(2, x.getTitre());
      paramStm.setInt(3, x.getDureeMinutes());
      paramStm.setInt(4, x.getEstObligatoire());
      int n = paramStm.executeUpdate();

      if (n > 0) {
        paramStm.close();
        // stm.close();
        return true;
      }
    } catch (SQLException exp) {
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
  public Lecture read(int id) {
    String req = "SELECT * FROM lecture WHERE `ID_LECTURE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        Lecture l = new Lecture();
        l.setIdLecture(resultat.getInt("ID_LECTURE"));
        l.setIdCompte(resultat.getInt("ID_COMPTE"));
        l.setDateInscription(resultat.getDate("DATE_INSCRIPTION"));
        l.setTitre(resultat.getString("TITRE"));
        l.setDureeMinutes(resultat.getInt("DUREE_MINUTES"));
        l.setEstObligatoire(resultat.getInt("EST_OBLIGATOIRE"));

        resultat.close();
        paramStm.close();
        return l;
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
  public boolean update(Lecture x) {
    String req =
        "UPDATE lecture SET TITRE = ?, DUREE_MINUTES = ?,"
            + "EST_OBLIGATOIRE = ? WHERE ID_LECTURE = ?";

    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setString(1, x.getTitre());
      paramStm.setInt(2, x.getDureeMinutes());
      paramStm.setInt(3, x.getEstObligatoire());
      paramStm.setInt(4, x.getIdLecture());

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
  public boolean delete(Lecture x) {
    Logger.getLogger(this.getClass().getName()).log(Level.INFO, ("entrer dans DAOLecture"));
    String req = "DELETE FROM lecture WHERE `ID_LECTURE` = ?";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, x.getIdLecture());

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
  public List<Lecture> findAll() {
    List<Lecture> liste = new ArrayList<>();
    try {
      Statement stm = cnx.createStatement();
      ResultSet r = stm.executeQuery("SELECT * FROM lecture ORDER BY TITRE ASC");
      while (r.next()) {
        Lecture l = new Lecture();
        l.setIdLecture(r.getInt("ID_LECTURE"));
        l.setIdCompte(r.getInt("ID_COMPTE"));
        l.setTitre(r.getString("TITRE"));
        l.setDateInscription(r.getDate("DATE_INSCRIPTION"));
        l.setDureeMinutes(r.getInt("DUREE_MINUTES"));
        l.setEstObligatoire(r.getInt("EST_OBLIGATOIRE"));
        liste.add(l);
      }
      r.close();
      stm.close();
    } catch (SQLException exp) {
    }
    return liste;
  }

  public List<Lecture> findByIdCompte(int idCompte) {

    String req = "SELECT * FROM lecture WHERE `ID_COMPTE` = ?";
    List<Lecture> listeLecture = new ArrayList<Lecture>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Lecture l = new Lecture();
        l.setIdLecture(resultat.getInt("ID_LECTURE"));
        l.setIdCompte(resultat.getInt("ID_COMPTE"));
        l.setDateInscription(resultat.getDate("DATE_INSCRIPTION"));
        l.setTitre(resultat.getString("TITRE"));
        l.setDureeMinutes(resultat.getInt("DUREE_MINUTES"));
        l.setEstObligatoire(resultat.getInt("EST_OBLIGATOIRE"));
        listeLecture.add(l);
      }
      resultat.close();
      paramStm.close();
      return listeLecture;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeLecture;
  }

  public int getMinutesAjd(int idCompte) {

    String req = "SELECT SUM(DUREE_MINUTES) AS MINAJD FROM lecture WHERE `ID_COMPTE` = ? AND DATE(DATE_INSCRIPTION)=CURDATE()";
    List<Lecture> listeLecture = new ArrayList<Lecture>();

    PreparedStatement paramStm = null;
    int minAjd=0;
    
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {
	  minAjd=resultat.getInt("MINAJD");
      }
      resultat.close();
      paramStm.close();

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return minAjd;
  }
    
    
  public List<Lecture> findByIdCompteOrderByDate(int idCompte) {

    String req =
        "SELECT * FROM lecture WHERE `ID_COMPTE` = ? ORDER BY lecture.DATE_INSCRIPTION desc";
    List<Lecture> listeLecture = new ArrayList<Lecture>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Lecture l = new Lecture();
        l.setIdLecture(resultat.getInt("ID_LECTURE"));
        l.setIdCompte(resultat.getInt("ID_COMPTE"));
        l.setDateInscription(resultat.getDate("DATE_INSCRIPTION"));
        l.setTitre(resultat.getString("TITRE"));
        l.setDureeMinutes(resultat.getInt("DUREE_MINUTES"));
        l.setEstObligatoire(resultat.getInt("EST_OBLIGATOIRE"));
        listeLecture.add(l);
      }
      resultat.close();
      paramStm.close();
      return listeLecture;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeLecture;
  }

  public List<Lecture> findByIdCompteOrderByTitre(int idCompte) {

    String req = "SELECT * FROM lecture WHERE `ID_COMPTE` = ? ORDER BY lecture.TITRE asc";
    List<Lecture> listeLecture = new ArrayList<Lecture>();

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        Lecture l = new Lecture();
        l.setIdLecture(resultat.getInt("ID_LECTURE"));
        l.setIdCompte(resultat.getInt("ID_COMPTE"));
        l.setDateInscription(resultat.getDate("DATE_INSCRIPTION"));
        l.setTitre(resultat.getString("TITRE"));
        l.setDureeMinutes(resultat.getInt("DUREE_MINUTES"));
        l.setEstObligatoire(resultat.getInt("EST_OBLIGATOIRE"));
        int taille = listeLecture.size();
        boolean existe = false;
        for (int i = 0; i < taille; i++) {
          if (listeLecture.get(i).getTitre().toUpperCase().equals(l.getTitre().toUpperCase())) {
            existe = true;
          }
        }
        if (existe == false) {
          listeLecture.add(l);
        }
      }
      resultat.close();
      paramStm.close();
      return listeLecture;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception e) {
      }
    }

    return listeLecture;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM lecture";

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
      Logger.getLogger(LectureDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception e) {
      Logger.getLogger(LectureDAO.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(LectureDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }
}
