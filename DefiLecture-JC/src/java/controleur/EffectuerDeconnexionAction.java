/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joel
 */
public class EffectuerDeconnexionAction implements Action, RequestAware, SessionAware{
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        session = request.getSession(true);
        
        if(session.getAttribute("connecte") != null){
            session.removeAttribute("connecte");
            if(session.getAttribute("role") != null)
                session.removeAttribute("role");
        }
        request.setAttribute("vue","accueil.jsp");
        return "/index.jsp";
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }


    
    
    
}
