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
package com.defiLecture.controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.Defi;
import com.defiLecture.modele.DefiDAO;
import com.defiLecture.modele.Lecture;
import com.defiLecture.modele.LectureDAO;
import java.sql.SQLException;


/**
 *
 * @author Charles
 */
public class AfficherPageModificationDefiAction implements Action, RequestAware, SessionAware {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    @Override
    public String execute() {
        try {
            //Seuls les Capitaines et les Participants peuvent ajouter et modifier leurs lectures.
            if( session.getAttribute("connecte") != null && session.getAttribute("role") != null && request.getParameter("id")!=null)
                if( ( (int)session.getAttribute("role") == Compte.MODERATEUR)|| ( (int)session.getAttribute("role") == Compte.ADMINISTRATEUR) ){
                   Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                   Defi d = new DefiDAO(cnx).read(request.getParameter("id"));
                    
                    
                    //seul celui qui a ajouté la lecture peut la modifier
                    if(d.getIdCompte() == (int)session.getAttribute("connecte"))
                        request.setAttribute("vue", "pageModificationDefi.jsp");
                }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AfficherPageModificationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AfficherPageModificationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            Connexion.close();
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

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
