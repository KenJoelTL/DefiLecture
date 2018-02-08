/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture;

import com.defiLecture.modele.Equipe;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import jdbc.Config;

/**
 *
 * @author usager
 */
public class EcouteurApplication implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application démarrée");
        ServletContext application = sce.getServletContext();
        
        Config.DB_USER = application.getInitParameter("userBD");
        Config.DB_PWD = application.getInitParameter("passwordBD");
        Config.DRIVER = application.getInitParameter("piloteJDBC");
        Config.URL = application.getInitParameter("urlBd");
        
        Equipe.NB_MAX_MEMBRES =              
                Integer.parseInt(application.getInitParameter("nbParticipantMax"));        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application terminée");
    }
    
}
