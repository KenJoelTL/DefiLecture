/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class LectureDAO extends DAO<Lecture> {
    
    public LectureDAO(Connection c)
    {
        super(c);
    }

    @Override
    public boolean create(Lecture x) {
               
        String req = "INSERT INTO lecture (`ID_COMPTE` , `TITRE` , `DATE_INSCRIPTION` , `DUREE_MINUTES`) VALUES "+
			     "(?,?,?,?)";

        PreparedStatement paramStm = null;
        try 
        {

                paramStm = cnx.prepareStatement(req);

                
                paramStm.setInt(1, x.getIdCompte());
                paramStm.setString(2, x.getTitre());
                paramStm.setString(3, x.getDateInscription());
                paramStm.setInt(4, x.getDureeMinutes());

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
                  /*if(stm != null)
                      Connexion.close();*/
                  
        }
        return false;
    }

    @Override
    public Lecture read(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lecture read(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Lecture x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Lecture x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lecture> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
