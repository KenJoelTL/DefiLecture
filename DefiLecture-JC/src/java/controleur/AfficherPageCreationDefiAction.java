/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modele.Compte;

/**
 *
 * @author Charles
 */
public class AfficherPageCreationDefiAction implements Action, RequestAware, SessionAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    @Override
    public String execute() {
        if(session.getAttribute("connecte") != null
        && (((int)session.getAttribute("role") == Compte.MODERATEUR)
        ||  ((int)session.getAttribute("role") == Compte.ADMINISTRATEUR)))
            request.setAttribute("vue", "pageCreationDefi.jsp");

        
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
