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
import java.util.List;
import jdbc.Connexion;

/**
 *
 * @author Joel
 */
public class ParticipantDAO extends DAO<Participant>{

    public ParticipantDAO(Connection cnx) {
        super(cnx);
    }

    @Override
    public boolean create(Participant x) {
        String req = "INSERT INTO participant (`ID_EQUIPE` , `COURRIEL` , `MOT_PASSE` , `NOM`, `PRENOM`, `PSEUDONYME`, `AVATAR`, `PROGRAMME_ETUDE`) VALUES "+
			     "(?,?,?,?,?,?,?,?)";

        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                
                paramStm.setInt(1, x.getIdEquipe());
                paramStm.setString(2, x.getCourriel());
                paramStm.setString(3, x.getMotPasse());
                paramStm.setString(4, x.getNom());
                paramStm.setString(5, x.getPrenom());
                paramStm.setString(6, x.getPseudonyme());
                paramStm.setString(7, x.getAvatar());
                paramStm.setString(8, x.getProgrammeEtude());

                int nbLignesAffectees= paramStm.executeUpdate();
                
                if (nbLignesAffectees>0) {
                        paramStm.close();
                        return true;
                }
        }
        catch (SQLException exp) {
        }
        finally {
                if (paramStm!=null)
                    Connexion.close();
        }
        return false;
    }

    @Override
    public Participant read(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Participant read(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean update(Participant x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Participant x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Participant findByIdentifiantMotPasse(String identifiant, String motPasse){
        
        String req = "SELECT * FROM participant WHERE (`COURRIEL` = ? or `PSEUDONYME` = ?) and `MOT_PASSE` = ?";
        
        PreparedStatement paramStm = null;
        try {

                paramStm = cnx.prepareStatement(req);

                paramStm.setString(1, identifiant);
                paramStm.setString(2, identifiant);
                paramStm.setString(3, motPasse);

                ResultSet resultat = paramStm.executeQuery();
                
                // On vérifie s'il y a un résultat    
                if(resultat.next()){
                    
                    Participant p = new Participant();
                    p.setIdParticipant(resultat.getInt("ID_PARTICIPANT"));
                    p.setIdEquipe(resultat.getInt("ID_EQUIPE"));
                    p.setCourriel(resultat.getString("COURRIEL"));
                    p.setPrenom(resultat.getString("PRENOM"));             
                    p.setNom(resultat.getString("NOM"));
                    p.setPseudonyme(resultat.getString("PSEUDONYME"));             
                    p.setAvatar(resultat.getString("AVATAR"));             
                    p.setProgrammeEtude(resultat.getString("PROGRAMME_ETUDE"));
                    p.setMinutesRestantes(resultat.getInt("MINUTES_RESTANTES"));
                    p.setPointage(resultat.getInt("POINTAGE"));
                    p.setRole(resultat.getInt("ROLE"));
                    
                    resultat.close();
                    paramStm.close();
                        return p;
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
