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
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class DefiDAO extends DAO<Defi> {
    
    public DefiDAO(Connection c)
    {
        super(c);
    }

    @Override
    public boolean create(Defi x) {
               
        String req = "INSERT INTO defi (`ID_COMPTE` , `NOM_DEFI` , `DATE_DEBUT` , `DATE_FIN`, `QUESTION`, `REPONSE`, `POINT_DEFI`) VALUES "+
			     "(?,?,?,?,?,?,?)";
				 		 

        PreparedStatement paramStm = null;
        try 
        {

                paramStm = cnx.prepareStatement(req);

                
                paramStm.setInt(1, x.getIdCompte());
                paramStm.setString(2, x.getNom());
                paramStm.setString(3, x.getDateDebut());
		paramStm.setString(4, x.getDateFin());
		paramStm.setString(5, x.getQuestion());
		paramStm.setString(6, x.getReponse());
                paramStm.setInt(7, x.getPoint());

                int n= paramStm.executeUpdate();
                
                if (n>0)
                {
                        paramStm.close();
                        //stm.close();
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
    public Defi read(int id) {
        String req = "SELECT * FROM defi WHERE `ID_DEFI` = ?";
        
        PreparedStatement paramStm = null;
		
        try {
		
                paramStm = cnx.prepareStatement(req);

                paramStm.setInt(1, id);

                ResultSet resultat = paramStm.executeQuery();
                
                // On vérifie s'il y a un résultat    
                if(resultat.next()){
                    
                    Defi d = new Defi();

                    d.setIdDefi(resultat.getInt("ID_DEFI"));
                    d.setIdCompte(resultat.getInt("ID_COMPTE"));
                    d.setNom(resultat.getString("NOM"));
                    d.setDateDebut(resultat.getString("DATE_DEBUT"));
                    d.setDateFin(resultat.getString("DATE_FIN"));
                    d.setQuestion(resultat.getString("QUESTION"));
                    d.setReponse(resultat.getString("REPONSE"));
                    d.setPoint(resultat.getInt("POINT_DEFI"));

                    resultat.close();
                    paramStm.close();
                    return d;

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
    public Defi read(String id) {
        return(this.read(Integer.parseInt(id)));
    }

    @Override
    public boolean update(Defi x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Defi x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                while(resultat.next()){
                    
                    Defi d = new Defi();

                    d.setIdDefi(resultat.getInt("ID_DEFI"));
                    d.setIdCompte(resultat.getInt("ID_COMPTE"));
                    d.setNom(resultat.getString("NOM"));
                    d.setDateDebut(resultat.getString("DATE_DEBUT"));
                    d.setDateFin(resultat.getString("DATE_FIN"));
                    d.setQuestion(resultat.getString("QUESTION"));
                    d.setReponse(resultat.getString("REPONSE"));
                    d.setPoint(resultat.getInt("POINT_DEFI"));
                    
                    listeDefi.add(d);
                        
                }
                resultat.close();       
                stm.close();
                return listeDefi;
                
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
        
        return listeDefi;
    }
    
    
}
