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

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.Defi;
import com.defiLecture.modele.DefiDAO;
import com.defiLecture.modele.DemandeEquipe;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.InscriptionDefi;
import com.defiLecture.modele.InscriptionDefiDAO;
import com.defiLecture.modele.Lecture;
import com.defiLecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

public class SupprimerDefiAction implements Action, SessionAware, RequestAware, RequirePRGAction {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;
    private HashMap data;
    
    @Override
    public String execute() {
        String action = "*.do?tache=afficherPageParticipationDefi";
        try {
            Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
            InscriptionDefiDAO IDdao= new InscriptionDefiDAO(cnx);
            CompteDAO Cdao= new CompteDAO(cnx);
            DefiDAO Ddao= new DefiDAO(cnx);
            DemandeEquipeDAO Dedao= new DemandeEquipeDAO(cnx);
            Defi defi;
            defi = Ddao.read(Integer.valueOf(request.getParameter("idDefiSup")));
            
            // Permets de passer au travers de toutes les inscriptions du défi qui doit être supprimé
            List<InscriptionDefi> listID = IDdao.findByIdDefi(Integer.valueOf(request.getParameter("idDefiSup")));
            listID.forEach((insDe) -> {
                Compte profil = Cdao.read(insDe.getIdCompte());
                IDdao.delete(insDe);
                
                // Permets d'enlever les points de l'équipe, gagner par le défi  
                List<DemandeEquipe> listDe = Dedao.findByIdCompte(profil.getIdCompte());
                listDe.forEach((demandeE) -> {
                    demandeE.setPoint(demandeE.getPoint()-insDe.getValeurMinute()); 
                    Dedao.update(demandeE);
                 });
                
                // Permets d'enlever les points gagnés par le défi aux comptes qui s'y sont inscrits
                profil.setPoint(profil.getPoint()-insDe.getValeurMinute());
                Cdao.update(profil);
            });
            boolean delete = Ddao.delete(defi);
            System.out.println(delete);
        } 
        catch (SQLException ex) {
            Logger.getLogger(SupprimerDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex) {
            Logger.getLogger(SupprimerDefiAction.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            Connexion.close();
        }
        return action;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session; 
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request; 
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }
    
}
