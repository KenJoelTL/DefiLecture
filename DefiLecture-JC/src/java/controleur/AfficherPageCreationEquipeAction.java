/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AfficherPageCreationEquipeAction implements Action,RequestAware, SessionAware{
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;        
            
    @Override
    public String execute() {
        if((int)session.getAttribute("role")==2 || (int)session.getAttribute("role")==4){

            int idCompte = (int)session.getAttribute("connecte");
            try {
                CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
                Compte compte = dao.read(idCompte);
                if(compte.getIdEquipe()==-1){
                    request.setAttribute("vue", "pageCreationEquipe.jsp");
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AfficherPageCreationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                 request.setAttribute("vue","accueil.jsp");
            }
        }
        return"/index.jsp";
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
