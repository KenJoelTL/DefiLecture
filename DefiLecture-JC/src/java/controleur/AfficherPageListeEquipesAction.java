/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joel
 */
public class AfficherPageListeEquipesAction implements Action, RequestAware {
    HttpServletRequest request;
    HttpServletResponse response;
    
    @Override
    public String execute() {
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
    
}
