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
import modele.DemandeEquipe;
import modele.DemandeEquipeDAO;
import modele.Equipe;
import modele.EquipeDAO;

/**
 *
 * @author Joel
 */
public class EffectuerDemandeAdhesionEquipeAction implements Action, SessionAware, 
                                                RequestAware, RequirePRGAction {
    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    
    @Override
    public String execute() {
        //action envoyée au controleur frontal
        String action = "connexion.do?tache=afficherPageConnexion";
        if(session.getAttribute("connecte") == null 
            || request.getParameter("idEquipe") == null 
            || request.getParameter("idCompte") == null)
            action = "connexion.do?tache=afficherPageConnexion";
        else{
            try {
                String idEquipe = request.getParameter("idEquipe");
                String idCompte = request.getParameter("idCompte");
                
                //Ouverture de la connexion
                Connection cnx = Connexion.startConnection(Config.DB_USER, 
                                 Config.DB_PWD, Config.URL, Config.DRIVER);
                Equipe equipe = new EquipeDAO(cnx).read(idEquipe);
                Compte compte = new CompteDAO(cnx).read(idCompte);
                
                //Cherche si le compte existe
                if(compte == null || equipe == null){
                    action = "connexion.do?tache=afficherPageConnexion";
                }
                else{
                    DemandeEquipe demandeEq = new DemandeEquipe();
                    demandeEq.setIdCompte(compte.getIdCompte());
                    demandeEq.setIdEquipe(equipe.getIdEquipe());
                    DemandeEquipeDAO demandeDao = new DemandeEquipeDAO(cnx);
                    
                    //Insertion dans la base de données
                    if(!demandeDao.create(demandeEq))
                        action = "demandeEchouee.do?tache=afficherPageListeEquipes";
                    else
                        action = "demandeEnvoyee.do?tache=afficherPageListeEquipes";
                }
        
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.
                        class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{ Connexion.close(); }        
        
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
