/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
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
        if((int)session.getAttribute("role")==2 || (int)session.getAttribute("role")==4){
            String nom = request.getParameter("nom");
            if(nom != null){
                Equipe equipe = new Equipe();
                equipe.setNom(nom);
 //           equipe.setIdCapitaine((int)session.getAttribute("id"));
                try {
                    EquipeDAO dao = new EquipeDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));

                    if(dao.create(equipe)){
                        /*Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                        equipe = dao.find()*/
                        //return"profilEquipe.do?tache=afficherPageEquipe&idEquipe="+equipe.getIdEquipe(); //envoyer sur la page de l'équipe.
                        return"creationEquipeCompletee.do"; //soit afficher le page avec utilisateur pour pouvoir enoyer une demande

                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AfficherPageCreationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                    //return"creation.do?tache=afficherPageCreationEquipe";
                    return"creation.do?tache=afficherPageCreationEquipe";

                }
            }
        }
        return"creation.do?tache=afficherPageCreationEquipe";
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
