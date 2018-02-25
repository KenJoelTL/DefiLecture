
package com.defiLecture.controleur;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class EffectuerInscriptionAction implements Action, RequestAware, RequirePRGAction {
    //private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public String execute() {
        String action = "echec.do?tache=afficherPageInscription";

        if( request.getParameter("courriel")    != null 
            && request.getParameter("prenom")   != null
            && request.getParameter("nom")      != null
            && request.getParameter("motPasse") != null
            && request.getParameter("confirmationMotPasse")  != null
            && request.getParameter("motPasse").equals(request.getParameter("confirmationMotPasse")) )
        {
            try{
                String  courriel = request.getParameter("courriel"),
                        prenom = request.getParameter("prenom"),
                        nom = request.getParameter("nom"),
                        motPasse = request.getParameter("motPasse"),
                        programmeEtude = request.getParameter("programmeEtude"),
                        pseudonyme = request.getParameter("pseudonyme"); 
                /*
                //Étape 1 : chargement du pilote JDBC
                Class.forName(Config.DRIVER);
                //Étape 2 : configurer les paramètres de la connexion vers la base de données
                Connexion.setUrl(Config.URL);
                Connexion.setUser(Config.DB_USER);
                Connexion.setPassword(Config.DB_PWD);
                //Étape 3 : ouverture de la connexion vers la base de données
                Connection cnx = Connexion.getInstance();*/
                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                CompteDAO dao = new CompteDAO(cnx);
                Compte compte = new Compte();
                compte.setCourriel(courriel);
                compte.setPrenom(prenom);
                compte.setNom(nom);
                compte.setMotPasse(motPasse);
                compte.setPseudonyme(pseudonyme);
                compte.setProgrammeEtude(programmeEtude);

                //faire vérification avec des findBy
                if(dao.create(compte)){
                    System.out.println("Un compte a été créé avec succès");
                    
                    action="succes.do?tache=afficherPageConnexion";
                }
                else{
                    System.out.println("Problème de création de la compte");
                }
            }
            catch(ClassNotFoundException e){
                System.out.println("Erreur dans le chargement du pilote :"+ e);
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerInscriptionAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{}
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
/*
    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }*/
}
