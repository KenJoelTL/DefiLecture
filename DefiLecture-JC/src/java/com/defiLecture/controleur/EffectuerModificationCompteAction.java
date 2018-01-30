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
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;

/**
 *
 * @author Joel
 */
public class EffectuerModificationCompteAction implements Action, RequestAware, RequirePRGAction{
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() {
        if(request.getParameter("modifie")!=null){
            String idCompte = request.getParameter("idCompte"),
                   courriel = request.getParameter("courriel"),
                   prenom = request.getParameter("prenom"),
                   nom = request.getParameter("nom"),
                   programmeEtude = request.getParameter("programmeEtude"),
                   motPasse = request.getParameter("motPasse"),
                   pseudonyme = request.getParameter("pseudonyme");
            int idEquipe, 
                minutesRestantes, 
                pointage,
                role;
              
            try {
                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                
                CompteDAO dao = new CompteDAO(cnx);
                Compte compte = dao.read(idCompte);
                if(compte == null)
                    return"*.do?tache=afficherPageGestionListeCompte";
                else{
                    cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                    dao.setCnx(cnx);
                    
                    if(request.getParameter("role") != null){
                        try{
                            role = Integer.parseInt(request.getParameter("role"));
                            if(role != compte.getRole())
                                compte.setRole(role);
                        }
                        catch(NumberFormatException e){
                        }
                    }
                    if(request.getParameter("minutesRestantes") != null){
                        try{
                            minutesRestantes = Integer.parseInt(request.getParameter("minutesRestantes"));
                            if(minutesRestantes != compte.getMinutesRestantes())
                                compte.setMinutesRestantes(minutesRestantes);
                        }
                        catch(NumberFormatException e){
                        }
                    }
                    if(request.getParameter("pointage") != null){
                        try{
                            pointage = Integer.parseInt(request.getParameter("pointage"));
                            if(pointage != compte.getPoint())
                                compte.setPoint(pointage);
                        }
                        catch(NumberFormatException e){
                        }
                    }
                    if(request.getParameter("idEquipe") != null){ // lorsque quelqu'un quitte son equipe idEquipe = -1
                        try{
                            idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
                            if(idEquipe != compte.getIdEquipe())
                                compte.setIdEquipe(idEquipe);
                        }
                        catch(NumberFormatException e){
                        }
                    }
                    if(courriel != null && !"".equals(courriel.trim()) && !courriel.equals(compte.getCourriel()))
                        compte.setCourriel(courriel);
                    if(prenom != null && !"".equals(prenom.trim()) && !prenom.equals(compte.getPrenom()))
                        compte.setPrenom(prenom);
                    if(nom != null && !"".equals(nom.trim()) && !nom.equals(compte.getNom()))
                        compte.setNom(nom);
                    if(motPasse != null && !"".equals(motPasse.trim()) && !motPasse.equals(compte.getMotPasse()))
                        compte.setMotPasse(motPasse);
                    if(pseudonyme != null && !pseudonyme.equals(compte.getPseudonyme()))
                        compte.setPseudonyme(pseudonyme);
                    if(programmeEtude != null && !programmeEtude.equals(compte.getProgrammeEtude()))
                        compte.setProgrammeEtude(programmeEtude);
                    
                    if(!dao.update(compte)){
                        request.setAttribute("message", "Problèmes dans l'enregistrement des informations"); //mettre un message d'erreur
                        return"*.do?tache=afficherPageModificationCompte&id="+compte.getIdCompte();
                    }
                    else{
                    //il faut avertir que les changements ont étés faits
                        return"*.do?tache=afficherPageGestionListeCompte";
        //                request.setAttribute("vue", "gestionConfigurationCompte.jsp");
                    }
                }            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerModificationCompteAction.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Problèmes dans l'enregistrement des informations"); //mettre un message d'erreur
                return"*.do?tache=afficherPageGestionConfigurationCompte&id="+request.getParameter("idCompte");
            }
        }
        else
            return"*.do?tache=afficherPageGestionListeCompte";
        

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
