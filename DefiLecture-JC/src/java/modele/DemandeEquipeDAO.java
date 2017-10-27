/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Connexion;

/**
 *
 * @author Joel
 */
public class DemandeEquipeDAO extends DAO<DemandeEquipe>{

    public DemandeEquipeDAO(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(DemandeEquipe x) {
        String req = "INSERT INTO demande_equipe (`ID_DEMANDE_EQUIPE`, `ID_COMPTE,` "
                   + "`ID_EQUIPE`, `STATUS_DEMANDE`, `POINT`) + VALUES (?,?,?,?,?)";

        PreparedStatement paramStm = null;
        try {
            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, x.getIdDemandeEquipe());
            paramStm.setInt(2, x.getIdCompte());
            paramStm.setInt(3, x.getIdEquipe());
            paramStm.setInt(4, x.getStatutDemande());
            paramStm.setInt(5, x.getPoint());

            int nbLignesAffectees= paramStm.executeUpdate();

            if (nbLignesAffectees>0) {
                paramStm.close();
                return true;
            }
                
            return false;
        }
        catch (SQLException exp) {
        }
        finally {
            try {
                if (paramStm!=null)
                    paramStm.close();
            } catch (SQLException ex) {
                Logger.getLogger(DemandeEquipeDAO.class.getName())
                        .log(Level.SEVERE, null, ex);
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
            if(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatutDemande(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));

                resultat.close();
                paramStm.close();
                    return de;
            }
            
            resultat.close();
            paramStm.close();
            return null;
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (paramStm!=null)
                    paramStm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception de) {
            }
        }        
        
        return null;
    }

    @Override
    public DemandeEquipe read(String id) {
        try{
            return this.read(Integer.parseInt(id));
        }
        catch(NumberFormatException de){
            return null;
        }
    }

    @Override
    public boolean update(DemandeEquipe x) {
        String req = "UPDATE demande_equipe SET `ID_EQUIPE` = ? , `ID_COMPTE` = ? , "
                   + "`POINT` = ? , `STATUS_DEMANDE`= ? WHERE `ID_DEMANDE_EQUIPE = ?`";

        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, x.getIdEquipe());
            paramStm.setInt(2, x.getIdCompte());
            paramStm.setInt(3, x.getPoint());
            paramStm.setInt(4, x.getStatutDemande());
            paramStm.setInt(5, x.getIdDemandeEquipe());

            int nbLignesAffectees= paramStm.executeUpdate();

            if (nbLignesAffectees>0) {
                    paramStm.close();
                    return true;
            }
        }
        catch (SQLException exp) {
        }
        finally {
            try {
                if (paramStm!=null)
                    paramStm.close();
            } catch (SQLException ex) {
                Logger.getLogger(DemandeEquipeDAO.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
                
        }
        return false;
    }

    @Override
    public boolean delete(DemandeEquipe x) {
        String req = "DELETE FROM equipe WHERE `ID_DEMANDE_EQUIPE = ?`";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);
                paramStm.setInt(1, x.getIdDemandeEquipe());
                
                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                        paramStm.close();
                        return true;
                }
        }
        catch (SQLException exp) {
        }
        finally {
            try {
                if (paramStm!=null)
                    paramStm.close();
            } catch (SQLException ex) {
                Logger.getLogger(DemandeEquipeDAO.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
                
        }
        return false;
    }

    @Override
    public List<DemandeEquipe> findAll() {
                List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe";
            Statement stm = cnx.createStatement();

            ResultSet resultat = stm.executeQuery(req);

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatutDemande(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            stm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }
    
    public List<DemandeEquipe> findByIdCompte(int idCompte) {
        List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe WHERE `ID_COMPTE` = ?";

            PreparedStatement paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idCompte);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatutDemande(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            paramStm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }
    
    public List<DemandeEquipe> findByIdEquipe(int idEquipe) {
        List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe WHERE `ID_EQUIPE` = ?";

            PreparedStatement paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idEquipe);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatutDemande(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            paramStm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }

    public List<DemandeEquipe> findByIdCompteEquipe(int idCompte, int idEquipe) {
        List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe WHERE `ID_COMPTE` = ? and `ID_EQUIPE` = ?";

            PreparedStatement paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idCompte);
            paramStm.setInt(2, idEquipe);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatutDemande(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            paramStm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }
    
    /*
    public List<DemandeEquipe> findByIdEquipeStatut(int idEquipe,) {
        List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe WHERE `ID_EQUIPE` = ?";

            PreparedStatement paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idEquipe);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatut(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            paramStm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }*/
    /*
    
    public int findByIdEquipe(int idEquipe) {
        List<DemandeEquipe> liste = new LinkedList<>();
        
        try {
            String req = "SELECT * FROM demande_equipe WHERE `ID_EQUIPE` = ?";

            PreparedStatement paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idEquipe);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            while(resultat.next()){

                DemandeEquipe de = new DemandeEquipe();
                de.setIdDemandeEquipe(resultat.getInt("ID_DEMANDE_EQUIPE"));
                de.setIdCompte(resultat.getInt("ID_COMPTE"));
                de.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                de.setStatut(resultat.getInt("STATUT"));
                de.setPoint(resultat.getInt("POINT"));
                liste.add(de);
            }
            
            resultat.close();
            paramStm.close();
                
        }
        catch (SQLException exp) {
        }
        return liste;
    }
    */
    
    
}
