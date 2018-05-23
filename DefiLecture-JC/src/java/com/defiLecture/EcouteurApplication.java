/**
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
*/
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
        
        Config.DB_USER = application.getInitParameter("userDB");
        Config.DB_PWD = application.getInitParameter("passwordDB");
        Config.DRIVER = application.getInitParameter("piloteJDBC");
        Config.URL = application.getInitParameter("urlDb");
        
        Equipe.NB_MAX_MEMBRES =              
                Integer.parseInt(application.getInitParameter("nbParticipantMax"));        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application terminée");
    }
    
}
