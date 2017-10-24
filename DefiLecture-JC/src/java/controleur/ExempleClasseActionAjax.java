/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            
            
            //contruire une chaine json
            String json = "[]";
            
            
            
            //finalement envoyer un reponse en spéficiant le format
            response.setContentType("application/json"); //ou n'importe quoi comme string
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException ex) {
            //Logger.getLogger(ExempleClasseActionAjax.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("\n"+ex.getMessage());
        }
        
        return "N'importe quoi, car la chaine ne sera pas prise en compte par le contrôleur frontal";
        
    }

    @Override
    public void setRequest(HttpServletRequest resquest) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    
}
