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
import com.defiLecture.modele.Equipe;
import com.defiLecture.modele.EquipeDAO;
import com.util.Util;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joel
 */
public class EffectuerModificationEquipeAction implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender{
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    HashMap data;

    @Override
    public String execute() {
        String action = "*.do?tache=afficherPageAccueil";
        if(request.getParameter("idEquipe") !=null){
            action = "*.do?tache=afficherPageEquipe&idEquipe="+request.getParameter("idEquipe");
         if(request.getParameter("modifier") !=null){
            if(session.getAttribute("connecte") != null && session.getAttribute("role") !=null 
                && (int)session.getAttribute("role") == Compte.CAPITAINE && request.getParameter("nom") != null){
                try {
                    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                    Compte compte = new CompteDAO(cnx).read((int)session.getAttribute("connecte"));
                    EquipeDAO equipeDao = new EquipeDAO(cnx);
                    Equipe equipe = equipeDao.findByNom(request.getParameter("nom"));
                    if(compte != null && equipe ==null && compte.getIdEquipe() != -1){
                        equipe = equipeDao.read(compte.getIdEquipe());
                        equipe.setNom(Util.toUTF8(request.getParameter("nom")));
                        
                        if(equipeDao.update(equipe)){
                            action = "*.do?tache=afficherPageModificationEquipe&idEquipe="+request.getParameter("idEquipe");
                            data.put("succesNom", "L'enregistrement du nouveau nom s'est fait avec succès");
                        }
                        else{
                            data.put("erreurModification", "Un problème est survenu lors de la modification des informations");
                        }
                    }else{
                        data.put("erreurNom", "Le nom "+ Util.toUTF8(request.getParameter("nom")) +" est déjà utilisé par un autre équipage");
                        action = "*.do?tache=afficherPageModificationEquipe&idEquipe="+request.getParameter("idEquipe");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(EffectuerModificationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                    action = "*.do?tache=afficherPageModificationEquipe&idEquipe="+request.getParameter("idEquipe");
                } catch (SQLException ex) {
                    Logger.getLogger(EffectuerModificationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
          }
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
