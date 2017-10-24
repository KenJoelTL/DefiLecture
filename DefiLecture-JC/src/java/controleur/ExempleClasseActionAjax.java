/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joel
 */
public class ExempleClasseActionAjax implements Action, RequestAware, SendAjaxResponse{
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    @Override
    public String execute() {
        try {
            //Logique ici
            
            
            //contruire une chaine json ou biee faire comme on veux
            String json = "[]";
            String commeOnVeux = "['"+request.getParameter("prenom")+"']";
            
            
            
            //finalement envoyer un reponse en spéficiant le format
            response.setContentType("application/json;UTF-8"); //ou n'importe quoi comme string text/plain ou  bien html/text
            //response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().write(commeOnVeux);
        } catch (IOException ex) {
            //Logger.getLogger(ExempleClasseActionAjax.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\n"+ex.getMessage());
        }
        
        return "N'importe quoi, car la chaine ne sera pas prise en compte par le contrôleur frontal"; //return null
        
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
