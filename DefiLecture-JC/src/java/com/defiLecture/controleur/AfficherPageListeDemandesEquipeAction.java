/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.defiLecture.modele.Compte;

/**
 *
 * @author Joel
 */
public class AfficherPageListeDemandesEquipeAction implements Action, RequestAware, SessionAware {
    private HttpServletResponse reponse;
    private HttpServletRequest request;
    private HttpSession session;
    
    @Override
    public String execute() { 
        
        if(session.getAttribute("connecte") == null)
           request.setAttribute("vue", "pageConnexion");        
        else
            request.setAttribute("vue", "pageListeDemandesEquipe.jsp");

        return"/index.jsp";
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.reponse = response;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
}
