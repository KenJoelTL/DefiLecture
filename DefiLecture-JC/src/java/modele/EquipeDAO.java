/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import com.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class EquipeDAO extends DAO<Equipe>{

    public EquipeDAO(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Equipe x) {
        String req = "INSERT INTO equipe (`NOM`) VALUES (?)";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);
/*
                    byte[] ptext = x.getNom().getBytes(ISO_8859_1); 
String value = new String(ptext, UTF_8);*/ 
                paramStm.setString(1, Util.toUTF8(x.getNom()));
//                paramStm.setInt(2, x.getIdCapitaine());
                
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
                    Logger.getLogger(EquipeDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
        }
        return false;
    }

    @Override
    public Equipe read(int id) {
        String req = "SELECT * FROM equipe WHERE `ID_EQUIPE` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, id);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Equipe e = new Equipe();
                e.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                e.setNom(resultat.getString("NOM"));

                resultat.close();
                paramStm.close();
                    return e;
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
    public Equipe read(String id) {
        
        try{
            return this.read(Integer.parseInt(id));
        }
        catch(NumberFormatException e){
            return null;
        }

    }

    @Override
    public boolean update(Equipe x) {
        String req = "UPDATE equipe SET `NOM` = ? WHERE `ID_EQUIPE = ?`";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                if(x.getNom() == null || "".equals(x.getNom().trim()))
                    paramStm.setString(1, null);
                else
                    paramStm.setString(1, x.getNom());
                paramStm.setInt(2, x.getIdEquipe());
                
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
                    Logger.getLogger(EquipeDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
        }
        return false;
    }

    @Override
    public boolean delete(Equipe x) {
        String req = "DELETE FROM equipe WHERE `ID_EQUIPE = ?`";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);
                paramStm.setInt(1, x.getIdEquipe());
                
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
                    Logger.getLogger(EquipeDAO.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                
        }
        return false;
    }

    @Override
    public List<Equipe> findAll() {
        List<Equipe> liste = new LinkedList<>();
        try {
            Statement stm = cnx.createStatement(); 
            ResultSet r = stm.executeQuery("SELECT * FROM equipe ORDER BY POINT_EQUIPE DESC");
            while (r.next()) {
                Equipe e = new Equipe();
                e.setIdEquipe(r.getInt("ID_EQUIPE"));
                e.setNom(r.getString("NOM"));
                
                //appeler les DAO DEMANDE
                e.setPoint(r.getInt("POINT_EQUIPE"));

                liste.add(e);
            }
            r.close();
            stm.close();
        }
        catch (SQLException exp) {
        }
        return liste;
    }

    public Equipe findByNom(String nom) {
        String req = "SELECT * FROM equipe WHERE `NOM` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);/*
            byte[] ptext = nom.getBytes(ISO_8859_1); 
            String value = new String(ptext, UTF_8); */      
            paramStm.setString(1, Util.toUTF8(nom));

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Equipe e = new Equipe();
                e.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                e.setNom(resultat.getString("NOM"));

                resultat.close();
                paramStm.close();
                    return e;
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
    
    public Equipe findByIdCapitaine(int idCapitaine) {
        String req = "SELECT * FROM equipe WHERE `ID_CAPITAINE` = ?";
        
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idCapitaine);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){

                Equipe e = new Equipe();
                e.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                e.setNom(resultat.getString("NOM"));

                resultat.close();
                paramStm.close();
                    return e;
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
    
    public int countNbMembre(int idEquipe){
    
//        String req = "SELECT COUNT(ID_COMPTE) FROM `compte` WHERE ID_EQUIPE = ?";
        String req = "SELECT COUNT(ID_DEMANDE_EQUIPE) FROM `demande_equipe` WHERE ID_EQUIPE = ? and STATUT_DEMANDE > 0";
       int nbMembre = 0;
        PreparedStatement paramStm = null;
        try {

            paramStm = cnx.prepareStatement(req);

            paramStm.setInt(1, idEquipe);

            ResultSet resultat = paramStm.executeQuery();

            // On vérifie s'il y a un résultat    
            if(resultat.next()){
                nbMembre = resultat.getInt("COUNT(ID_COMPTE)");
            }
            
            resultat.close();
            paramStm.close();
            return nbMembre;
                
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
        
        return nbMembre;
    }

    
}
