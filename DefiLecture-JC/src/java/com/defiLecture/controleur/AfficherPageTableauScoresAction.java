/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joel
 */
public class AfficherPageTableauScoresAction implements Action, RequestAware {
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() {
        request.setAttribute("vue", "pageTableauScores.jsp");
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
    
}