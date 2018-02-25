/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.DemandeEquipe;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.Lecture;
import com.defiLecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/**
 *
 * @author Charles
 */
public class EffectuerSupressionLectureAction implements Action, SessionAware, RequestAware, RequirePRGAction{
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    @Override
    public String execute() {
        if(session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && ( ((int)session.getAttribute("role") == Compte.PARTICIPANT)
            || ((int)session.getAttribute("role") == Compte.CAPITAINE) ) )
        {

            String idLecture = request.getParameter("idLecture");
            int idCompte = (int)session.getAttribute("connecte");
  
        
        try {

                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);

                LectureDAO daoLecture = new LectureDAO(cnx);
                Lecture lecture = daoLecture.read(idLecture);
                if(lecture == null) {
  
                    return "*.do?tache=afficherPageGestionLecture";
                }
                else{

                    cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                    
                    CompteDAO daoCompte = new CompteDAO(cnx);
                    Compte compte = new Compte();
                    compte = daoCompte.read(idCompte);
               
                    if(lecture.getEstObligatoire() == Lecture.NON_OBLIGATOIRE){
                        lecture.setDureeMinutes(lecture.getDureeMinutes()*2);
                    }

                    int pointLecture = (lecture.getDureeMinutes()) / 15;
                    compte.setPoint(compte.getPoint() -pointLecture );
                    
                    daoCompte.update(compte);
                    
                    if(compte.getIdEquipe() > 0){
                        DemandeEquipeDAO demandeDAO = new DemandeEquipeDAO(cnx);
                        DemandeEquipe demande = new DemandeEquipe();
                        demande = demandeDAO.findByIdCompteEquipe(idCompte, compte.getIdEquipe());
                        int pointDemandeEquipe = demande.getPoint() - pointLecture;
                        demande.setPoint(pointDemandeEquipe);
                        demandeDAO.update(demande);
                    }

                    daoLecture.setCnx(cnx);
                    

                    if(!daoLecture.delete(lecture)){

                        return "*.do?tache=afficherPageGestionLecture";
                    }
                    else{

                    return "*.do?tache=afficherPageGestionLecture";
        
                    }
                }            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerModificationLectureAction.class.getName()).log(Level.SEVERE, null, ex);
                return "*.do?tache=afficherPageGestionLecture";
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerModificationLectureAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            return "*.do?tache=afficherPageGestionLecture";
        return null;
        
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
    
}
