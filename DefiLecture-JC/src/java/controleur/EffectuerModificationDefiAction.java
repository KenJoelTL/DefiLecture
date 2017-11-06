/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import modele.Compte;
import modele.Defi;
import modele.DefiDAO;
import modele.Lecture;
import modele.LectureDAO;

/**
 *
 * @author Charles
 */
public class EffectuerModificationDefiAction implements Action, RequestAware, SessionAware, RequirePRGAction {

     private HttpSession session;
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() {
  
        
        if(session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && ( ((int)session.getAttribute("role") == Compte.MODERATEUR)
            || ((int)session.getAttribute("role") == Compte.ADMINISTRATEUR) )
        && request.getParameter("modifie")!=null){
            
            String nom = request.getParameter("nom"),
                   description = request.getParameter("titre"),
                    dateDebut = request.getParameter("dateDebut"),
                    dateFin = request.getParameter("dateFin"),
                    question = request.getParameter("question"),
                    reponse = request.getParameter("reponse"),
                    choixReponse = request.getParameter("choixReponseJSON"),
                    idDefi = request.getParameter("idDefi");
                   
            int valeurMinute; 
                
              
            try {
                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                
                Defi defi = new DefiDAO(cnx).read(idDefi);
                if(defi == null){
                    
                    return "*.do?tache=afficherPageParticipationDefi";
                }
                else{
                    
                   
                    if(nom != null && !"".equals(nom.trim()) && !nom.equals(defi.getNom()))
                        defi.setNom(nom);
                    
                    if(request.getParameter("valeurMinute") != null){
                        try{
                            valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));
                            if(valeurMinute != defi.getValeurMinute())
                                defi.setValeurMinute(valeurMinute);
                        }
                        catch(NumberFormatException e){
                        }
                    }
                    
                    if(dateDebut != defi.getDateDebut())
                        defi.setDateDebut(dateDebut);
                    if(dateFin != defi.getDateFin())
                        defi.setDateFin(dateFin);
                    
                 
                 
                    if(description != null && !"".equals(description.trim()) && !description.equals(defi.getDescription()))
                        defi.setDescription(description);
                 
                    if(question != null && !"".equals(question.trim()) && !question.equals(defi.getQuestion()))
                        defi.setQuestion(question);
                 
                    if(choixReponse != defi.getChoixReponse())
                        defi.setChoixReponse(choixReponse);
                    if(reponse != defi.getReponse())
                        defi.setReponse(reponse);
                 
                    cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                    
                    DefiDAO dao = new DefiDAO(cnx);
                    if(!dao.update(defi)){
                 
                        //request.setAttribute("vue", "accueil.jsp");
                        return "*.do?tache=afficherPageParticipationDefi";
                    }
                    else{
                 
                    //il faut avertir que les changements ont étés faits
                    //    request.setAttribute("vue", "pageProfil.jsp"); //faire PRG
                    return "*.do?tache=afficherPageParticipationDefi";
        
                    }
                }            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerModificationLectureAction.class.getName()).log(Level.SEVERE, null, ex);
                return "*.do?tache=afficherPageParticipationDefi";
            }
        }
        else
            return "*.do?tache=afficherPageParticipationDefi";
        
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
