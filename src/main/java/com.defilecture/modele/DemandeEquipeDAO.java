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

public class DemandeEquipeDAO extends DAO<DemandeEquipe> {

  public DemandeEquipeDAO() {}

  public DemandeEquipeDAO(Connection cnx) {
    super(cnx);
  }

  @Override
  public boolean create(DemandeEquipe x) {
    String req =
        "INSERT INTO demande_equipe (`ID_COMPTE`, `ID_EQUIPE`,"
            + " `STATUT_DEMANDE`, `POINT`) VALUES (?,?,?,?)";

    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, x.getIdCompte());
      paramStm.setInt(2, x.getIdEquipe());
      paramStm.setInt(3, x.getStatutDemande());
      paramStm.setInt(4, x.getPoint());

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
        Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public DemandeEquipe read(int id) {
    String req = "SELECT * FROM demande_equipe WHERE `ID_DEMANDE_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, id);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));

        resultat.close();
        paramStm.close();
        return de;
      }

      resultat.close();
      paramStm.close();
      return null;

    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      } catch (Exception de) {
      }
    }

    return null;
  }

  @Override
  public boolean update(DemandeEquipe x) {

    String req =
        "UPDATE demande_equipe SET `ID_EQUIPE` = ? , `ID_COMPTE` = ? , `POINT` = ? , `STATUT_DEMANDE`= ? WHERE `ID_DEMANDE_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, x.getIdEquipe());

      paramStm.setInt(2, x.getIdCompte());

      paramStm.setInt(3, x.getPoint());

      paramStm.setInt(4, x.getStatutDemande());

      paramStm.setInt(5, x.getIdDemandeEquipe());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public boolean delete(DemandeEquipe x) {
    String req = "DELETE FROM demande_equipe WHERE `ID_DEMANDE_EQUIPE` = ?";

    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, x.getIdDemandeEquipe());

      int nbLignesAffectees = paramStm.executeUpdate();

      if (nbLignesAffectees > 0) {
        paramStm.close();
        return true;
      }
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }

  @Override
  public List<DemandeEquipe> findAll() {
    List<DemandeEquipe> liste = new ArrayList<>();

    try {
      String req = "SELECT * FROM demande_equipe";
      Statement stm = cnx.createStatement();

      ResultSet resultat = stm.executeQuery(req);

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));
        liste.add(de);
      }

      resultat.close();
      stm.close();

    } catch (SQLException exp) {
    }
    return liste;
  }

  public List<DemandeEquipe> findByIdCompte(int idCompte) {
    List<DemandeEquipe> liste = new ArrayList<>();

    try {
      String req = "SELECT * FROM demande_equipe WHERE `ID_COMPTE` = ?";

      PreparedStatement paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));
        liste.add(de);
      }

      resultat.close();
      paramStm.close();

    } catch (SQLException exp) {
    }
    return liste;
  }

  public List<DemandeEquipe> findByIdEquipe(int idEquipe) {
    List<DemandeEquipe> liste = new ArrayList<>();

    try {
      String req = "SELECT * FROM demande_equipe WHERE `ID_EQUIPE` = ?";

      PreparedStatement paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idEquipe);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));
        liste.add(de);
      }

      resultat.close();
      paramStm.close();

    } catch (SQLException exp) {
    }
    return liste;
  }

  public DemandeEquipe findByIdCompteEquipe(int idCompte, int idEquipe) {

    try {
      String req = "SELECT * FROM demande_equipe WHERE `ID_COMPTE` = ? and `ID_EQUIPE` = ?";

      PreparedStatement paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idCompte);
      paramStm.setInt(2, idEquipe);

      ResultSet resultat = paramStm.executeQuery();
      // On vérifie s'il y a un résultat
      if (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));

        return de;
      }

      resultat.close();
      paramStm.close();

    } catch (SQLException exp) {
    }
    return null;
  }

  public int sumPointByidEquipe(int idEquipe) {
    int somme = 0;
    String req = "SELECT SUM(POINT) FROM demande_equipe WHERE `ID_EQUIPE` = ?";
    PreparedStatement paramStm = null;
    try {

      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, idEquipe);
      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      if (resultat.next()) somme = resultat.getInt("SUM(POINT)");

      resultat.close();
      paramStm.close();
    } catch (SQLException exp) {
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException exp) {
      }
    }

    return somme;
  }

  public List<DemandeEquipe> findByIdEquipeStatutDemande(int idEquipe, int statutDemande) {
    List<DemandeEquipe> liste = new ArrayList<>();

    try {
      String req = "SELECT * FROM demande_equipe WHERE `ID_EQUIPE` = ? and STATUT_DEMANDE = ?";

      PreparedStatement paramStm = cnx.prepareStatement(req);

      paramStm.setInt(1, idEquipe);
      paramStm.setInt(2, statutDemande);

      ResultSet resultat = paramStm.executeQuery();

      // On vérifie s'il y a un résultat
      while (resultat.next()) {

        DemandeEquipe de = new DemandeEquipe();
        de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
        de.setIdCompte(resultat.getInt("ID_COMPTE"));
        de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
        de.setStatutDemande(resultat.getInt("STATUT_DEMANDE"));
        de.setPoint(resultat.getInt("POINT"));
        liste.add(de);
      }

      resultat.close();
      paramStm.close();

    } catch (SQLException exp) {
    }
    return liste;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM demande_equipe";

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
      Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, exp);
    } catch (Exception e) {
      Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, e);
    } finally {
      try {
        if (paramStm != null) paramStm.close();
      } catch (SQLException ex) {
        Logger.getLogger(DemandeEquipeDAO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return false;
  }
}
