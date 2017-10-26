/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import modele.CompteDAO;

/**
 *
 * @author Joel
 */
public class AfficherPageGestionListeComptesAction implements Action, RequestAware , SessionAware {
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        try {
            //Exclusivement pour l'Admin. on vérifie si le role a été changé durant la session.
            Class.forName(Config.DRIVER);
            Connexion.setUrl(Config.URL);
            Connexion.setUser(Config.DB_USER);
            Connexion.setPassword(Config.DB_PWD);
            Connection cnx = Connexion.getInstance();
            CompteDAO dao = new CompteDAO(cnx);
            
            if(session.getAttribute("connecte")!=null &&
                    dao.read((int)session.getAttribute("connecte")).getRole()==4)   //si le compte connecté est Admin
                request.setAttribute("vue", "pageGestionListeCompte.jsp");
            else
                request.setAttribute("vue", "accueil.jsp");
            
            return "/index.jsp";

        } catch (ClassNotFoundException ex) {
            System.out.println("Erreur dans le chargement du pilote");
            Logger.getLogger(AfficherPageGestionListeComptesAction.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("vue", "accueil.jsp");
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
