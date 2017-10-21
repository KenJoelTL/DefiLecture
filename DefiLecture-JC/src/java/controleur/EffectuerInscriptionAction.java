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
import modele.Compte;
import modele.CompteDAO;

/**
 *
 * @author Joel
 */
public class EffectuerInscriptionAction implements Action, RequestAware, SessionAware {
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private CompteDAO dao;

    @Override
    public String execute() {
        Compte compte;
        String  courriel = request.getParameter("courriel"),
                prenom = request.getParameter("prenom"),
                nom = request.getParameter("nom"),
                motPasse = request.getParameter("motPasse"),
                programmeEtude = request.getParameter("programmeEtude"),
                pseudonyme = request.getParameter("pseudonyme"); 
        
        try{
            //Étape 1 : chargement du pilote JDBC
            Class.forName(Config.DRIVER);
            Connexion.setUrl(Config.URL);
            Connexion.setUser(Config.DB_USER);
            Connexion.setPassword(Config.DB_PWD);
            //Étape 2 : ouverture de la connection vers la base de données
            Connection cnx = Connexion.getInstance();
            dao = new CompteDAO(cnx);
            compte = new Compte();
            compte.setCourriel(courriel);
            compte.setPrenom(prenom);
            compte.setNom(nom);
            compte.setMotPasse(motPasse);
            compte.setPseudonyme(pseudonyme);
            compte.setProgrammeEtude(programmeEtude);
            
            //faire vérification avec des findBy
            
            if(dao.create(compte)){
                System.out.println("Une compte a été créée avec succès");
                request.setAttribute("vue", "connexion.jsp");
            }
            else{
                System.out.println("Problème de création de la compte");
                request.setAttribute("vue", "accueil.jsp");
            }
            return "/index.jsp";
        }
        catch(ClassNotFoundException e){
            System.out.println("Erreur dans le chargement du pilote :"+ e);
            request.setAttribute("vue", "compte.jsp");
            return "/index.jsp";
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
