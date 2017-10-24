/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import modele.Defi;
import modele.DefiDAO;
import modele.Lecture;
import modele.LectureDAO;

/**
 *
 * @author Charles
 */
public class EffectuerCreationDefiAction implements Action, RequestAware, SessionAware, RequirePRGAction {
    
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private DefiDAO dao;
    
    @Override
    public String execute() {
        
        String  nom = request.getParameter("nom"),
                dateDebut = request.getParameter("dateDebut"),
                dateFin = request.getParameter("dateFin"),
                question = request.getParameter("question"),
                choix1 = request.getParameter("choix1"),
                choix2 = request.getParameter("choix2"),
                choix3 = request.getParameter("choix3"),
                choix4 = request.getParameter("choix4"),
                questionDB = question+";"+choix1+";"+choix2+";"+choix3+";"+choix4,
                
                reponse = request.getParameter("reponse");
        int     idCompte = (int)session.getAttribute("connecte"),
                point = Integer.parseInt(request.getParameter("point"));

        
        
        Defi defi;
        
        try{

            Connexion.reinit();
            Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
            dao = new DefiDAO(cnx);
            defi = new Defi();
            defi.setIdCompte(idCompte);
            defi.setNom(nom);
            defi.setDateDebut(dateDebut);
            defi.setDateFin(dateFin);
            defi.setQuestion(questionDB);
            defi.setReponse(reponse);
            defi.setPoint(point);
            if(dao.create(defi))
                System.out.println("Un defi a été créé avec succès");
            else
                System.out.println("Problème de création du défi");

            return "*.do?tache=afficherPageCreationDefi";
        }
        catch(ClassNotFoundException e){
            System.out.println("Erreur dans le chargement du pilote :"+ e);

            return "*.do?tache=afficherPageCreationDefi";
        }
                
        
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
