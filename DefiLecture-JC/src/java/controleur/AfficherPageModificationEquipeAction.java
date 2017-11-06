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
public class AfficherPageModificationEquipeAction implements Action, RequestAware, SessionAware{
    private HttpSession session;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() {
        if(session.getAttribute("connecte") !=null && session.getAttribute("role") != null)
            request.setAttribute("vue", "pageModificationEquipe.jsp");
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
