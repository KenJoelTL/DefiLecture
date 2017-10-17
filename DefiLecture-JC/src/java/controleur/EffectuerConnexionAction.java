/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class EffectuerConnexionAction implements Action, RequestAware, SessionAware {
    
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public String execute() {
        
        System.out.println("Entrer dans l'action de connexion");
        
        String  identifiant = request.getParameter("identifiant"),
                motPasse = request.getParameter("motPasse"); 
        
        String pilote = "com.mysql.jdbc.Driver";
        
        Connection cnx =null;
        ResultSet rs = null;
        PreparedStatement requetePreparee = null; 
        String requete = "SELECT 'COURRIEL' FROM participant WHERE ('COURRIEL' = ? or 'PSEUDONYME' = ? ) and 'MOT_PASSE' = ?";

        try{
            //Étape 1 : chargement du pilote JDBC
            Class.forName(pilote);
            //Étape 2 : ouverture de la connection vers la base de données
            Connexion.setUrl(Config.URL);
            Connexion.setUser(Config.DB_USER);
            Connexion.setPassword(Config.DB_PWD);
            cnx = Connexion.getInstance();
            //Étape 3 : execution de la requete
            requetePreparee = cnx.prepareStatement(requete);
            //Étape 3.1 : liaison des paramètres avec les valeurs
            requetePreparee.setString(1, identifiant);   //met la 1er ? à la valeur du courriel dans la requete
            requetePreparee.setString(2, identifiant);   //met la 2e  ? à la valeur du nomUtilisateur dans la requete
            requetePreparee.setString(3, motPasse);      //met la 1er ? à la valeur du motPasse dans la requete

            ResultSet resultat = requetePreparee.executeQuery();
            // On vérifie s'il y a un résultat    
            if(resultat.next()){
                HttpSession session = request.getSession(true);
                session.setAttribute("connecte", resultat.getString(1));
                request.setAttribute("vue", "pageEquipe.jsp");
                return "/index.jsp";
            }
            else{/*
                HttpSession session = request.getSession(true);
                session.setAttribute("connecte", "0");*/
                request.setAttribute("vue", "connexion.jsp");
                return "/index.jsp";
            }
        }
        catch(SQLException e){
            System.out.println("problème dans la connexion vers la base de données : " + e);
            request.setAttribute("vue", "connexion.jsp");
            return "/index.jsp";
        }
        catch(ClassNotFoundException e){
            System.out.println("Erreur dans le chargement du pilote :"+ e);
            request.setAttribute("vue", "connexion.jsp");
            return "/index.jsp";
        }
        finally{
            try {
                if(rs != null)
                    rs.close();
                if(requetePreparee !=null)
                    requetePreparee.close();
                if(cnx != null)
                    cnx.close();
            } catch (SQLException ex) {
                System.out.println("problème dans la fermeture de la connexion");
            } 
        }
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
}
