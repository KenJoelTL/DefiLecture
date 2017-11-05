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

/**
 *
 * @author Joel
 */
public class EffectuerModificationEquipeAction implements Action, RequestAware, SessionAware, RequirePRGAction{
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;

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
                        equipe.setNom(request.getParameter("nom"));
                        
                        if(equipeDao.update(equipe))
                            action = "*.do?tache=afficherPageModificationEquipe&idEquipe="+request.getParameter("idEquipe");
                        else
                            System.out.println("\n=========================134654564654656");//mettre un message d'erreur
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(EffectuerModificationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                    action = "*.do?tache=afficherPageModificationEquipe&idEquipe="+request.getParameter("idEquipe");
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
}
