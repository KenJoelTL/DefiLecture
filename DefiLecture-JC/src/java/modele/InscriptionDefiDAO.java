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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class InscriptionDefiDAO extends DAO<InscriptionDefi> {
    
    public InscriptionDefiDAO(Connection c)
    {
        super(c);
    }

    @Override
    public boolean create(InscriptionDefi x) {
               
        String req = "INSERT INTO inscription_defi (`ID_COMPTE` ,`ID_DEFI` , `EST_REUSSI`) VALUES "+
			     "(?,?,?)";
				 		 

        PreparedStatement paramStm = null;
        try 
        {

            paramStm = cnx.prepareStatement(req);

                
                
            paramStm.setInt(1, x.getIdCompte());
            paramStm.setInt(2, x.getIdDefi());
            paramStm.setInt(3, x.getEstReussi());
                

            int n= paramStm.executeUpdate();
                
            if (n>0)
            {
                paramStm.close();
                return true;
            }
        }
        catch (SQLException exp)
        {
        }
        finally
        {
                if (paramStm!=null)
                    Connexion.close();
         
        }
        return false;
    }

    @Override
    public InscriptionDefi read(String id) {
        return(this.read(Integer.parseInt(id)));
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
                if(resultat.next()){
                    
                    InscriptionDefi i = new InscriptionDefi();

                    i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
                    i.setIdCompte(resultat.getInt("ID_COMPTE"));
                    i.setIdDefi(resultat.getInt("ID_DEFI"));
                    i.setEstReussi(resultat.getInt("EST_REUSSI"));
                    i.setDateInscriptionDefi(resultat.getString("DATE_INSCRIPTION_DEFI"));
                    
                    resultat.close();
                    paramStm.close();
                    return i;
                    
                        
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
    public boolean update(InscriptionDefi x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(InscriptionDefi x) {
        String req = "DELETE FROM inscription_defi WHERE `ID_INSCRIPTION_DEFI` = ?";
        
        PreparedStatement paramStm = null;

        try {
                paramStm.setInt(1, x.getIdInscriptionDefi());
                paramStm = cnx.prepareStatement(req);

                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                    paramStm.close();
                    return true;
                }
                
            return false;
        }
        catch (SQLException exp) {
        }
        catch (Exception exp) {
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LectureDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                Connexion.close();
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
                while(resultat.next()){
                    
                    InscriptionDefi i = new InscriptionDefi();

                    i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
                    i.setIdCompte(resultat.getInt("ID_COMPTE"));
                    i.setIdDefi(resultat.getInt("ID_DEFI"));
                    i.setEstReussi(resultat.getInt("EST_REUSSI"));
                    i.setDateInscriptionDefi(resultat.getString("DATE_INSCRIPTION_DEFI"));

                    listeInscriptionDefi.add(i);
                        
                }
                resultat.close();
                stm.close();
                return listeInscriptionDefi;
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (stm!=null)
                    stm.close();
                if(cnx!=null)
                    Connexion.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
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
                while(resultat.next()){

                    InscriptionDefi i = new InscriptionDefi();

                    i.setIdInscriptionDefi(resultat.getInt("ID_INSCRIPTION_DEFI"));
                    i.setIdCompte(resultat.getInt("ID_COMPTE"));
                    i.setIdDefi(resultat.getInt("ID_DEFI"));
                    i.setEstReussi(resultat.getInt("EST_REUSSI"));
                    i.setDateInscriptionDefi(resultat.getString("DATE_INSCRIPTION_DEFI"));

                    listeInscriptionDefi.add(i);

                }
                resultat.close();
                paramStm.close();
                return listeInscriptionDefi;

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

        return listeInscriptionDefi;
        
    }
    
    
}
