/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import modele.Compte;
import modele.CompteDAO;

/**
 *
 * @author Joel
 */
public class EffectuerConnexionAction implements Action, RequestAware, SessionAware {
    
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CompteDAO dao;

    @Override
    public String execute() {
        
        System.out.println("Entrer dans l'action de connexion");
        
        String identifiant = request.getParameter("identifiant"),
               motPasse    = request.getParameter("motPasse"); 
        
        System.out.println(identifiant);
        System.out.println(motPasse);

        Compte compte;

        try{
            Class.forName(Config.DRIVER);
            Connexion.setUrl(Config.URL);
            Connexion.setUser(Config.DB_USER);
            Connexion.setPassword(Config.DB_PWD);
            Connection cnx = Connexion.getInstance();
            
            dao = new CompteDAO(cnx);
            compte = dao.findByIdentifiantMotPasse(identifiant, motPasse);
            
            // On vérifie s'il y a un résultat    
            if(compte!=null){
                System.out.println("Trouver résultat dans base de donnée");
                session = request.getSession(true);
                session.setAttribute("connecte", compte.getIdCompte());
                session.setAttribute("role", compte.getRole());
                request.setAttribute("vue", "pageProfil.jsp");
            }
            else{
                System.out.println("Pas trouvé de résultat dans base de donnée");
                request.setAttribute("vue", "connexion.jsp");
            }
            return "/index.jsp";           
        }
        catch(ClassNotFoundException e){ 
            System.out.println("Erreur dans le chargement du pilote :"+ e);
            request.setAttribute("vue", "connexion.jsp");
            return "/index.jsp";
        }
        finally{
            Connexion.close();
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
