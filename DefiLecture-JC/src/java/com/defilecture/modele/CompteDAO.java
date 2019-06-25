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

import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joel
 * @author Alexandre Dupré
 */
public class CompteDAO extends DAO<Compte> {

  public CompteDAO() {}

  public CompteDAO(Connection cnx) {
    super(cnx);
  }

  @Override
  public boolean create(Compte compte) {
    String req =
        "INSERT INTO compte (`COURRIEL` , `MOT_PASSE` , `NOM`, "
            + "`PRENOM`, `PSEUDONYME`, `AVATAR`, `PROGRAMME_ETUDE`,`DEVENIR_CAPITAINE`, `SEL`) VALUES "
            + "(?,?,?,?,?,?,?,?)";
    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);

      if (compte != null) {
        // Un sel aléatoire est généré pour ajouter au calcul du hash
        String sel = Util.genererSel();  
        String mdpHashe = Util.hasherAvecSel(compte.getMotPasse(), sel);
          
        paramStm.setString(1, compte.getCourriel());
        paramStm.setString(2, mdpHashe);
        paramStm.setString(3, compte.getNom());
        paramStm.setString(4, compte.getPrenom());
        paramStm.setString(5, compte.getPseudonyme());
        paramStm.setString(6, compte.getAvatar());
        paramStm.setString(7, compte.getProgrammeEtude());
        paramStm.setInt(8, compte.getDevenirCapitaine());
        paramStm.setString(9, sel);
      }
      int nbLignesAffectees = paramStm.executeUpdate();

      paramStm.close();
      return nbLignesAffectees > 0 ? true : false;
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  @Override
  public Compte read(int id) {
    String req = "SELECT * FROM compte WHERE `ID_COMPTE` = ?";
    PreparedStatement paramStm = null;
    Compte compte = null;
    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, id);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) compte = getCompteFromResultSet(resultat);

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return compte;
  }

  @Override
  public Compte read(String id) {
    try {
      return this.read(Integer.parseInt(id));
    } catch (NumberFormatException e) {
      return null;
    }
  }

  @Override
  public boolean update(Compte compte) {
    String reqSel = "SELECT SEL FROM compte WHERE ID_COMPTE = ?";
    
    String req =
        "UPDATE compte SET COURRIEL = ?, MOT_PASSE = ?, "
            + "NOM = ?, PRENOM = ?, PSEUDONYME = ?, AVATAR = ?, "
            + "PROGRAMME_ETUDE = ?, ID_EQUIPE = ?, MINUTES_RESTANTES = ?, "
            + "POINT = ?, ROLE = ? WHERE ID_COMPTE = ?";

    PreparedStatement paramStm = null;
    
    try {
      // Requête pour le sel du compte à modifier
      paramStm = cnx.prepareStatement(reqSel);
      paramStm.setInt(1, compte.getIdCompte());
      ResultSet resultat = paramStm.executeQuery();
      
      if (resultat.next()) {
        String sel = resultat.getString("SEL");
        String mdp = Util.hasherAvecSel(compte.getMotPasse(), sel);
    
        paramStm = cnx.prepareStatement(req);
        paramStm.setString(1, compte.getCourriel());
        paramStm.setString(2, mdp);
        paramStm.setString(3, compte.getNom());
        paramStm.setString(4, compte.getPrenom());
        paramStm.setString(5, compte.getPseudonyme());
        paramStm.setString(6, compte.getAvatar());
        paramStm.setString(7, compte.getProgrammeEtude());

        if (compte.getIdEquipe() == -1) {
          paramStm.setNull(8, java.sql.Types.INTEGER);
        } else {
          paramStm.setInt(8, compte.getIdEquipe());
        }

        paramStm.setInt(9, compte.getMinutesRestantes());
        paramStm.setInt(10, compte.getPoint());
        paramStm.setInt(11, compte.getRole());
        paramStm.setInt(12, compte.getIdCompte());      
        
        int nbLignesAffectees = paramStm.executeUpdate();
        
        resultat.close();
        paramStm.close();
        
        return nbLignesAffectees > 0 ? true : false;
      } 
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  @Override
  public boolean delete(Compte compte) {
    String req = "DELETE FROM compte WHERE `ID_COMPTE` = ?";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, compte.getIdCompte());

      int nbLignesAffectees = paramStm.executeUpdate();

      paramStm.close();
      return nbLignesAffectees > 0 ? true : false;
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  @Override
  public List<Compte> findAll() {

    List<Compte> liste = new ArrayList<Compte>();
    try {
      Statement stm = cnx.createStatement();
      ResultSet resultat = stm.executeQuery("SELECT * FROM compte ORDER BY NOM");

      while (resultat.next()) liste.add(getCompteFromResultSet(resultat));

      resultat.close();
      stm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return liste;
  }

  public List<Compte> findAll(int startingRow, int nbOfResult) {

    List<Compte> liste = new ArrayList<>();
    try {
      Statement stm = cnx.createStatement();
      ResultSet resultat = stm.executeQuery("SELECT * FROM compte LIMIT ?, ?");

      while (resultat.next()) liste.add(getCompteFromResultSet(resultat));

      resultat.close();
      stm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return liste;
  }

  public List<Compte> findAllByNom(String nom) throws SQLException {
    List<Compte> liste = new ArrayList<>();
    String req =
        "SELECT * FROM compte WHERE `NOM` LIKE ? OR `PRENOM` LIKE ? OR `PSEUDONYME` LIKE ?";

    PreparedStatement paramStm = null;
    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, "%" + nom + "%");
      paramStm.setString(2, "%" + nom + "%");
      paramStm.setString(3, "%" + nom + "%");

      ResultSet resultat = paramStm.executeQuery();
      while (resultat.next()) liste.add(getCompteFromResultSet(resultat));

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return liste;
  }

  public ArrayList<Compte> findByIdEquipe(int idEquipe) {
    ArrayList<Compte> liste = new ArrayList<>();
    String req = "SELECT * FROM compte WHERE `ID_EQUIPE` = ?";
    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, idEquipe);

      ResultSet resultat = paramStm.executeQuery();

      while (resultat.next()) liste.add(getCompteFromResultSet(resultat));

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return liste;
  }

  public Compte findByIdentifiantMotPasse(String identifiant, String motPasse) {
    String sel = "";

    // Requête pour trouver le sel de l'utilisateur correspondant
    String reqSel = "SELECT SEL FROM compte WHERE `COURRIEL` = ? or `PSEUDONYME` = ?";
    // Requête pour trouver le compte (avec mot de passe valide)
    String req =
        "SELECT * FROM compte WHERE (`COURRIEL` = ? or " + "`PSEUDONYME` = ?) and `MOT_PASSE` = ?";

    ResultSet resultat;
    Compte compte = null;
    PreparedStatement paramStm = null;

    try {
      // Définition des paramètres de la requête pour le sel
      paramStm = cnx.prepareStatement(reqSel);
      paramStm.setString(1, identifiant);
      paramStm.setString(2, identifiant);
      resultat = paramStm.executeQuery();
      if (resultat.next()) {
        sel = resultat.getString("SEL");
        Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, sel);

        // Définition des paramètres de la requête pour l'accès au compte
        paramStm = cnx.prepareStatement(req);
        paramStm.setString(1, identifiant);
        paramStm.setString(2, identifiant);
        String mdp = Util.hasherAvecSel(motPasse, sel);
        Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, mdp);
        // Le mot de passe est hashé avec le sel du compte (si trouvé)
        paramStm.setString(3, mdp);
        resultat = paramStm.executeQuery();

        if (resultat.next()) compte = getCompteFromResultSet(resultat);

        resultat.close();
        paramStm.close();
      }
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return compte;
  }

  public Compte findByPseudonyme(String pseudo) {
    String req = "SELECT * FROM compte WHERE `PSEUDONYME` = ?";
    PreparedStatement paramStm = null;
    Compte compte = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, pseudo);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) compte = getCompteFromResultSet(resultat);

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return compte;
  }

  public int countCompteByIdEquipe(int idEquipe) {
    String req =
        "SELECT COUNT(ID_DEMANDE_EQUIPE) FROM `demande_equipe` WHERE ID_EQUIPE = ? and STATUT_DEMANDE = 1";
    int nbMembre = 0;
    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setInt(1, idEquipe);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) nbMembre = resultat.getInt("COUNT(ID_DEMANDE_EQUIPE)");

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return nbMembre;
  }

  public Compte findByCourriel(String courriel) {
    String req = "SELECT * FROM compte WHERE `COURRIEL` = ?";
    PreparedStatement paramStm = null;
    Compte compte = null;

    try {
      paramStm = cnx.prepareStatement(req);
      paramStm.setString(1, courriel);
      ResultSet resultat = paramStm.executeQuery();

      if (resultat.next()) compte = getCompteFromResultSet(resultat);

      resultat.close();
      paramStm.close();
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return compte;
  }

  public boolean deleteTable() {
    String req = "DELETE FROM compte";

    PreparedStatement paramStm = null;

    try {
      paramStm = cnx.prepareStatement(req);
      int nbLignesAffectees = paramStm.executeUpdate();

      paramStm.close();
      return nbLignesAffectees > 0 ? true : false;
    } catch (SQLException ex) {
      Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return false;
  }

  protected Compte getCompteFromResultSet(ResultSet resultat) throws SQLException {
    return new Compte(
        resultat.getInt("ID_COMPTE"),
        resultat.getInt("ID_EQUIPE") == 0 ? -1 : resultat.getInt("ID_EQUIPE"),
        resultat.getString("PSEUDONYME"),
        resultat.getString("MOT_PASSE"),
        resultat.getString("NOM"),
        resultat.getString("PRENOM"),
        resultat.getString("COURRIEL"),
        resultat.getString("PROGRAMME_ETUDE"),
        resultat.getString("AVATAR"),
        resultat.getInt("ROLE"),
        resultat.getInt("POINT"),
        resultat.getInt("MINUTES_RESTANTES"),
        resultat.getInt("DEVENIR_CAPITAINE"));
  }
}
