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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Charles
 */
public class DefiDAO extends DAO<Defi> {

    public DefiDAO() {
    }
    
    public DefiDAO(Connection c)
    {
        super(c);
    }

    @Override
    public boolean create(Defi x) {
               
        System.out.println("entrer dans le DAO");
        String req = "INSERT INTO defi (`ID_COMPTE` , `NOM` , `DESCRIPTION`, `DATE_DEBUT` , `DATE_FIN`, `QUESTION`, `CHOIX_REPONSE`, `REPONSE`, `VALEUR_MINUTE`) VALUES "+
			     "(?,?,?,?,?,?,?,?,?)";
				 		 

        PreparedStatement paramStm = null;
        try 
        {

                paramStm = cnx.prepareStatement(req);
                

                paramStm.setInt(1, x.getIdCompte());
                paramStm.setString(2, Util.toUTF8(x.getNom()));
                paramStm.setString(3, Util.toUTF8(x.getDescription()));
                paramStm.setString(4, x.getDateDebut());
		paramStm.setString(5, x.getDateFin());
		paramStm.setString(6, Util.toUTF8(x.getQuestion()));
                paramStm.setString(7, Util.toUTF8(x.getChoixReponse()));
		paramStm.setString(8, Util.toUTF8(x.getReponse()));
                paramStm.setInt(9, x.getValeurMinute());

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
                try {
                if (paramStm!=null)
                        paramStm.close();
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
                if(resultat.next()){
                    
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
             catch (Exception e) {
            }
        }        
        
        return null;
    }

    @Override
    public Defi read(String id) {
        try{
            return this.read(Integer.parseInt(id));
        }
        catch(NumberFormatException e){
            return null;
        }  
    }

    @Override
    public boolean update(Defi x) {
                 String req = "UPDATE defi SET NOM = ? , DESCRIPTION = ?,"
                           + "DATE_DEBUT = ?, DATE_FIN = ?, CHOIX_REPONSE = ?,"
                         + "REPONSE = ?, VALEUR_MINUTE = ? WHERE ID_DEFI = ?";

        PreparedStatement paramStm = null;
        try {
                paramStm = cnx.prepareStatement(req);

                paramStm.setString(1, Util.toUTF8(x.getNom()));
                paramStm.setString(2, Util.toUTF8(x.getDescription()));
                paramStm.setString(3, x.getDateDebut());
                paramStm.setString(4, x.getDateFin());
                paramStm.setString(5, Util.toUTF8(x.getChoixReponse()));
                paramStm.setString(6, Util.toUTF8(x.getReponse()));
                paramStm.setInt(7, x.getValeurMinute());
                paramStm.setInt(8, x.getIdDefi());

                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                        paramStm.close();
                        return true;
                }
                                
            return false;
        }
        catch (SQLException exp) {
            System.out.println(exp.getMessage());
        }
        finally {
                try {
                    if (paramStm!=null)
                        paramStm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(CompteDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
        }
        return false;
    }

    @Override
    public boolean delete(Defi x) {
        String req = "DELETE FROM defi WHERE `ID_DEFI` = ?";
        
        PreparedStatement paramStm = null;

        try {
                paramStm.setInt(1, x.getIdDefi());
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
                while(resultat.next()){
                    
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
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (stm!=null)
                    stm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
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
                while(resultat.next()){
                    
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
             catch (Exception e) {
            }
        }        
        
        return listeDefi;
    }
     public List<Defi> findAllDefiEnCours() {
        
        String req = "SELECT * FROM defi WHERE defi.DATE_DEBUT < SYSDATE() AND defi.DATE_FIN > SYSDATE() ORDER BY defi.DATE_DEBUT desc";
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
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (stm!=null)
                    stm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return listeDefi;
    }
     
     
     public List<Defi> findHistorique() {
        
        String req = "SELECT * FROM defi WHERE defi.DATE_DEBUT <= SYSDATE() ORDER BY defi.DATE_DEBUT desc";
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
                
        }
        catch (SQLException exp) {
        }
        finally {
            try{
                if (stm!=null)
                    stm.close();
            }
            catch (SQLException exp) {
            }
             catch (Exception e) {
            }
        }        
        
        return listeDefi;
    }
    
    
}

