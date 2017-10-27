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
public class AfficherPageGestionConfigurationCompteAction implements Action, RequestAware,SessionAware {
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        if(request.getParameter("id")==null)
            request.setAttribute("vue", "gestionListeComptes.jsp");
        else{
            String idCompte = request.getParameter("id");
            try {
                Class.forName(Config.DRIVER);
                Connexion.setUrl(Config.URL);
                Connexion.setUser(Config.DB_USER);
                Connexion.setPassword(Config.DB_PWD);
                Connection cnx = Connexion.getInstance();

                CompteDAO dao = new CompteDAO(cnx);

                if(dao.read(idCompte)==null)
                    request.setAttribute("vue", "pageGestionListeCompte.jsp");
                else{    
                    request.setAttribute("vue", "pageGestionConfigurationCompte.jsp");
                }
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(AfficherPageGestionConfigurationCompteAction.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("vue", "pageGestionListeCompte.jsp");
                return "/index.jsp";
            }
            
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
