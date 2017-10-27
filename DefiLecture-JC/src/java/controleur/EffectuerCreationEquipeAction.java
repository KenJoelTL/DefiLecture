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
import modele.CompteDAO;
import modele.Equipe;
import modele.EquipeDAO;

/**
 *
 * @author Joel
 */
public class EffectuerCreationEquipeAction implements Action, RequestAware, SessionAware, RequirePRGAction {
    private HttpSession session;
    private HttpServletResponse response;
    private HttpServletRequest request;
    
    
    @Override
    public String execute() {
        if(session.getAttribute("connecte") != null){
         if((int)session.getAttribute("role")==2 || (int)session.getAttribute("role")==4){
            String nom = request.getParameter("nom");
            if(nom != null){
                Equipe equipe = new Equipe();
                equipe.setNom(nom);
 //           equipe.setIdCapitaine((int)session.getAttribute("id"));
                try {
                    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                    EquipeDAO daoEquipe = new EquipeDAO(cnx);

                    if(daoEquipe.create(equipe)){
                        /*Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                        equipe = dao.find()*/
                        //return"profilEquipe.do?tache=afficherPageEquipe&idEquipe="+equipe.getIdEquipe(); //envoyer sur la page de l'équipe.
                        //daoEquipe.setCnx(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
                        equipe = daoEquipe.findByNom(equipe.getNom());
                        
                        CompteDAO daoCompte = new CompteDAO(cnx);
                        Compte compte = daoCompte.read((int)session.getAttribute("connecte"));
                        
                        compte.setIdEquipe(equipe.getIdEquipe());
                        //daoCompte = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
                        if(daoCompte.update(compte))
                            return"creationEquipeCompletee.do?tache=afficherPageEquipe&idEquipe="+equipe.getIdEquipe(); //soit afficher le page avec utilisateur pour pouvoir enoyer une demande
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AfficherPageCreationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                    return"creation.do?tache=afficherPageCreationEquipe";
                }            
                finally{
                    Connexion.close();
                }
            }
         }
            return"creation.do?tache=afficherPageCreationEquipe";
        }
        return"connexion.do?tache=afficherPageConnexion";

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
