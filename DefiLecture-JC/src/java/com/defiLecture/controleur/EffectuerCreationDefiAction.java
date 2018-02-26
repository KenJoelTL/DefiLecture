/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.Defi;
import com.defiLecture.modele.DefiDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        if(session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && ( ((int)session.getAttribute("role") == Compte.MODERATEUR)
            || ((int)session.getAttribute("role") == Compte.ADMINISTRATEUR) )
        && request.getParameter("nom")!=null
        && request.getParameter("description")!=null
        && request.getParameter("dateFin")!=null
        && request.getParameter("question")!=null
        && request.getParameter("choixReponseJSON")!=null
        && request.getParameter("reponse")!=null
        && request.getParameter("valeurMinute")!=null
        && request.getParameter("dateDebut")!=null){
            String  nom = request.getParameter("nom"),
                    description = request.getParameter("description"),
                    dateDebut = request.getParameter("dateDebut"),
                    dateFin = request.getParameter("dateFin"),
                    question = request.getParameter("question"),
                    /*choix1 = request.getParameter("choix1"),
                    choix2 = request.getParameter("choix2"),
                    choix3 = request.getParameter("choix3"),
                    choix4 = request.getParameter("choix4"),*/
                    //choixReponse = choix1+";"+choix2+";"+choix3+";"+choix4,
                    choixReponse = request.getParameter("choixReponseJSON"),
                    reponse = request.getParameter("reponse");

            int     idCompte = (int)session.getAttribute("connecte"),
                    valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));



            Defi defi;

            try{

                Connexion.reinit();
                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                dao = new DefiDAO(cnx);
                defi = new Defi();
                defi.setIdCompte(idCompte);
                defi.setNom(nom);
                defi.setDescription(description);
                defi.setDateDebut(dateDebut);
                defi.setDateFin(dateFin);
                defi.setQuestion(question);
                defi.setChoixReponse(choixReponse);
                defi.setReponse(reponse);
                defi.setValeurMinute(valeurMinute);
                if(dao.create(defi)){

                    System.out.println("Un defi a été créé avec succès");
                }
                else
                    System.out.println("Problème de création du défi");

                return "*.do?tache=afficherPageParticipationDefi";
            }
            catch(ClassNotFoundException e){
                System.out.println("Erreur dans le chargement du pilote :"+ e);

                return "*.do?tache=afficherPageCreationDefi";
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerCreationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
                                return "*.do?tache=afficherPageCreationDefi";

            }
            finally{
               Connexion.close();
            }               
        }
        else
            return "*.do?tache=afficherPageCreationDefi";
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
