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
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.DemandeEquipe;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.Equipe;
import com.defiLecture.modele.EquipeDAO;

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
         if((int)session.getAttribute("role")== Compte.CAPITAINE 
            || (int)session.getAttribute("role")== Compte.ADMINISTRATEUR){
            String nom = request.getParameter("nom");
            if(nom != null){
                Equipe equipe = new Equipe();
                equipe.setNom(nom);
             // equipe.setIdCapitaine((int)session.getAttribute("id"));
                try {
                    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                    EquipeDAO daoEquipe = new EquipeDAO(cnx);
                    //idéalement créer le tout seulement si et seulement si toutes les conditions sont vraies
                    if(daoEquipe.create(equipe)){
                        equipe = daoEquipe.findByNom(equipe.getNom());
                        if(equipe != null){
                            CompteDAO daoCompte = new CompteDAO(cnx);
                            Compte compte = daoCompte.read((int)session.getAttribute("connecte"));

                            compte.setIdEquipe(equipe.getIdEquipe());

                            if(daoCompte.update(compte)){
                                DemandeEquipeDAO daoDemandeEquipe = new DemandeEquipeDAO(cnx);
                                DemandeEquipe demande = new DemandeEquipe();
                                demande.setIdCompte(compte.getIdCompte());
                                demande.setIdEquipe(compte.getIdEquipe());
                                
                                demande.setStatutDemande(1);
                                if(daoDemandeEquipe.create(demande))
                                    return"creationEquipeCompletee.do?tache=afficherPageEquipe&idEquipe="+equipe.getIdEquipe(); //soit afficher le page avec utilisateur pour pouvoir enoyer une demande
                            }
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AfficherPageCreationEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
                    return"creation.do?tache=afficherPageCreationEquipe";
                }            
                finally{
                    Connexion.close();
                }
            }
            return"creation.do?tache=afficherPageCreationEquipe";
         }
         return"creation.do?tache=afficherPageAccueil";
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
