package com.defiLecture.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joel
 */
public class AfficherPageListeEquipesAction implements Action, RequestAware, SessionAware, DataReceiver {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    @Override
    public String execute() {
        if( session.getAttribute("connecte") != null && session.getAttribute("role") != null )
            request.setAttribute("vue", "pageListeEquipes.jsp");
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
