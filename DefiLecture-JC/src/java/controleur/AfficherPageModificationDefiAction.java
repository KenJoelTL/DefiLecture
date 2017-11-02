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
import modele.Compte;
import modele.Defi;
import modele.DefiDAO;
import modele.Lecture;
import modele.LectureDAO;

/**
 *
 * @author Charles
 */
public class AfficherPageModificationDefiAction implements Action, RequestAware, SessionAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    @Override
    public String execute() {
        try {
            //Seuls les Capitaines et les Participants peuvent ajouter et modifier leurs lectures.
            if( session.getAttribute("connecte") != null && session.getAttribute("role") != null && request.getParameter("id")!=null)
                if( ( (int)session.getAttribute("role") == Compte.MODERATEUR)
                 || ( (int)session.getAttribute("role") == Compte.ADMINISTRATEUR) ){
                   Connection cnx = Connexion.startConnection
                        (Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                   Defi d = new DefiDAO(cnx).read(request.getParameter("id"));
                    
                    
                 //seul celui qui a ajouté la lecture peut la modifier
                if(d != null && d.getIdCompte() == (int)session.getAttribute("connecte"))
                    request.setAttribute("vue", "pageModificationDefi.jsp");
                }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherPageModificationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            Connexion.close();
        }
        
        return "/index.jsp";
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
