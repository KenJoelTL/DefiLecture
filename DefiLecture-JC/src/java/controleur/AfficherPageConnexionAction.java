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
 * @author Charles
 */
public class AfficherPageConnexionAction implements Action, RequestAware{
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        
        System.out.println("affichage de la page de connexion");
        
        request.setAttribute("vue", "connexion.jsp");
        
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
