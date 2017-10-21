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
public class CompteDAO extends DAO<Compte>{

    public CompteDAO(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Compte x) {
        String req = "INSERT INTO compte (`COURRIEL` , `MOT_PASSE` , `NOM`, `PRENOM`, `PSEUDONYME`, `AVATAR`, `PROGRAMME_ETUDE`) VALUES "+
			     "(?,?,?,?,?,?,?)";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                
                paramStm.setString(1, x.getCourriel());
                paramStm.setString(2, x.getMotPasse());
                paramStm.setString(3, x.getNom());
                paramStm.setString(4, x.getPrenom());
                if(!"".equals(x.getPseudonyme()))
                    paramStm.setString(5, x.getPseudonyme());
                else
                    paramStm.setString(5, null);
                if(!"".equals(x.getAvatar()))
                    paramStm.setString(6, x.getAvatar());
                else
                    paramStm.setString(6, null);
                if(!"".equals(x.getProgrammeEtude()))
                    paramStm.setString(7, x.getProgrammeEtude());
                else
                    paramStm.setString(7, null);
                
                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                        paramStm.close();
                        return true;
                }
                
                //paramStm.execute();
                
            return false;
        }
        catch (SQLException exp) {
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompteDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                Connexion.close();
        }
        return false;
    }

    @Override
    public Compte read(int id) {
        return this.read(id+"");
    }

    @Override
    public Compte read(String id) {
                String req = "SELECT * FROM compte WHERE `ID_COMPTE` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setString(1, id);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Compte c = new Compte();
                c.setIdCompte(resultat.getInt("ID_COMPTE"));
                c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                c.setCourriel(resultat.getString("COURRIEL"));
                c.setPrenom(resultat.getString("PRENOM"));             
                c.setNom(resultat.getString("NOM"));
                c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                c.setAvatar(resultat.getString("AVATAR"));             
                c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                c.setPointage(resultat.getInt("POINTAGE"));
                c.setRole(resultat.getInt("ROLE"));

                resultat.close();
                paramStm.close();
                    return c;
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
                if(cnx!=null)
                    Connexion.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return null;
    }
    
    @Override
    public boolean update(Compte x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Compte x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Compte> findAll() {
        List<Compte> liste = new LinkedList<>();
        try {
            Statement stm = cnx.createStatement(); 
            ResultSet r = stm.executeQuery("SELECT * FROM compte");
            while (r.next())
            {
                    Compte c = new Compte();
                    c.setIdCompte(r.getInt("ID_COMPTE"));
                    c.setIdEquipe(r.getInt("ID_EQUIPE"));
                    c.setCourriel(r.getString("COURRIEL"));
                    c.setMotPasse(r.getString("MOT_PASSE"));
                    c.setNom(r.getString("NOM"));
                    c.setPrenom(r.getString("PRENOM"));
                    c.setPointage(r.getInt("DUREE_MINUTES"));
                    c.setMinutesRestantes(r.getInt("MINUTES_RESTANTES"));
                    c.setProgrammeEtude(r.getString("PROGRAMME_ETUDE"));
                    c.setAvatar(r.getString("AVATAR"));
                    c.setPseudonyme(r.getString("PSEUDONYME"));
                    c.setRole(r.getInt("ROLE"));
                    liste.add(c);
            }
            r.close();
            stm.close();
        }
        catch (SQLException exp){
        }
        return liste;

    }

    public Compte findByIdentifiantMotPasse(String identifiant, String motPasse){
        
        String req = "SELECT * FROM compte WHERE (`COURRIEL` = ? or `PSEUDONYME` = ?) and `MOT_PASSE` = ?";
        ResultSet resultat;
        
        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                paramStm.setString(1, identifiant);
                paramStm.setString(2, identifiant);
                paramStm.setString(3, motPasse);

                resultat = paramStm.executeQuery();
                
                // On vérifie s'il y a un résultat    
                if(resultat.next()){
                    
                    Compte c = new Compte();
                    c.setIdCompte(resultat.getInt("ID_COMPTE"));
                    c.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                    c.setCourriel(resultat.getString("COURRIEL"));
                    c.setPrenom(resultat.getString("PRENOM"));             
                    c.setNom(resultat.getString("NOM"));
                    c.setPseudonyme(resultat.getString("PSEUDONYME"));             
                    c.setAvatar(resultat.getString("AVATAR"));             
                    c.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                    c.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                    c.setPointage(resultat.getInt("POINTAGE"));
                    c.setRole(resultat.getInt("ROLE"));
                    
                    resultat.close();
                    paramStm.close();
                        return c;
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
                if(cnx!=null)
                    Connexion.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return null;
    }
    
}
