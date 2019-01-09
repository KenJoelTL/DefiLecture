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
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.DemandeEquipe;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.Equipe;
import com.defiLecture.modele.EquipeDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joel
 */
public class EffectuerDemandeAdhesionEquipeAction implements Action, SessionAware, 
                                                RequestAware, RequirePRGAction, DataSender,SendAjaxResponse{
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    HashMap data;
    
    @Override
    public String execute() {
        //action envoyée au controleur frontal
        String action = "connexion.do?tache=afficherPageConnexion";
        if(session.getAttribute("connecte") == null 
            || request.getParameter("idEquipe") == null 
            || request.getParameter("idCompte") == null)
            action = "connexion.do?tache=afficherPageConnexion";
        else{
            try {
                String idEquipe = request.getParameter("idEquipe");
                String idCompte = request.getParameter("idCompte");
                
                //Ouverture de la connexion
                Connection cnx = Connexion.startConnection(Config.DB_USER, 
                                 Config.DB_PWD, Config.URL, Config.DRIVER);
                Equipe equipe = new EquipeDAO(cnx).read(idEquipe);
                Compte compte = new CompteDAO(cnx).read(idCompte);
                
                //Cherche si le compte existe
                if(compte == null || equipe == null){
                    action = "connexion.do?tache=afficherPageConnexion";
                }
                else{
                    DemandeEquipe demandeEq;
                    DemandeEquipeDAO demandeDao = new DemandeEquipeDAO(cnx);
                    demandeEq = demandeDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                    
                    // Vérifie si la demande existe déjà comme dans le cas que l'utilisateur 
                    // a quitté l'équipe avec plus de 0 points en contribution
                    if(demandeEq == null){ // si la demande n'existe pas
                        demandeEq = new DemandeEquipe();
                        demandeEq.setIdCompte(compte.getIdCompte());
                        demandeEq.setIdEquipe(equipe.getIdEquipe());
                        
                        //Insertion dans la base de données
                        if(!demandeDao.create(demandeEq))
                            action = "demandeEchouee.do?tache=afficherPageListeEquipes";
                        else{
                            response.setContentType("text/plain");
                            try {
                                String msg= "Une demande d'adhésion a été envoyée à l'équipage " + equipe.getNom();
                                demandeEq = demandeDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                                int idDemandeEquipe = demandeEq.getIdDemandeEquipe();
                                String json = "{\"msg\":\""+msg+"\",\"idDemandeEquipe\" :\""+idDemandeEquipe+"\"}"; 
                                response.getWriter().write(json);
                            } catch (IOException ex) {
                                Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            action = "demandeEnvoyee.do?tache=afficherPageListeEquipes";
                        }
                    }
                    else{//si la demande existe déjà alors on la rend visible
                        demandeEq.setStatutDemande(-1);
                    }
                    
                    
                }
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.
                        class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{ Connexion.close(); }        
        
        }
        return action;
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

    @Override
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }
}
