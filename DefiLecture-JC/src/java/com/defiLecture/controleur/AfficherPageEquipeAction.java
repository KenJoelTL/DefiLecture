/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.EquipeDAO;

/**
 *
 * @author Charles
 */
public class AfficherPageEquipeAction implements Action, RequestAware {
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        
        String idEquipe = request.getParameter("idEquipe");
        
        if(idEquipe!=null){
            try {
                Connection cnx = Connexion.startConnection
                        (Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                EquipeDAO dao = new EquipeDAO(cnx);
                if(dao.read(idEquipe) != null)
                    request.setAttribute("vue", "pageEquipe.jsp");
                    
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AfficherPageEquipeAction
                                   .class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("vue", "accueil.jsp");
            }
            finally{
                Connexion.close();
            }

        }        
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
