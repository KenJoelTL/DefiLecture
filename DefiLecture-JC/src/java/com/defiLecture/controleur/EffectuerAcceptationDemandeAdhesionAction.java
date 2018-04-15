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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Joel
 */
public class EffectuerAcceptationDemandeAdhesionAction implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender{
    HttpServletResponse response;
    HttpServletRequest request;
    HttpSession session;
    HashMap data;

    @Override
    public String execute() {
        String action = ".do?tache=afficherPageAccueil";
        if(session.getAttribute("connecte") == null
            || session.getAttribute("role") == null
            || ( ((int)session.getAttribute("role") != Compte.CAPITAINE)
                && ((int)session.getAttribute("role") != Compte.ADMINISTRATEUR))
            || request.getParameter("idDemandeEquipe") == null){
            action = ".do?tache=afficherPageAccueil";}
        else{
            try {
                String idDemandeEq = request.getParameter("idDemandeEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, 
                                 Config.DB_PWD, Config.URL, Config.DRIVER);
                
                DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
                DemandeEquipe demandeEq = deDao.read(idDemandeEq);
                
                if(demandeEq == null) {
                    action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                    data.put("avertissementDemande", "Impossible d'accepter la demande puisqu'elle a été retirée par le matelot");
                }
                else{
                    CompteDAO compteDao = new CompteDAO(cnx);
                    Compte cpt = compteDao.read(demandeEq.getIdCompte());
                    action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                    if(cpt == null) {
                        action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                        data.put("erreurDemande", "Le matelot auteur de cette demande est à la retraite.");
                    }
                    else if(cpt.getIdEquipe() !=-1){
                        data.put("erreurDemande", "Le matelot "+ cpt.getPrenom() +"«"+ cpt.getPseudonyme() +"»"+ cpt.getNom() +" fait déjà partie d'un équipage.");
                    }
                    else{
                        int idEquipe = demandeEq.getIdEquipe();
                        int nbMembre = compteDao.countCompteByIdEquipe(idEquipe);
                        if (nbMembre < Equipe.NB_MAX_MEMBRES) {
                            cpt.setIdEquipe(idEquipe);
                            demandeEq.setStatutDemande(DemandeEquipe.ACCEPTEE);
                            if(deDao.update(demandeEq) && compteDao.update(cpt)){
                                action = "ajoutReussi.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                                data.put("succesDemande", "Le matelot "+ cpt.getPrenom() +" «"+ cpt.getPseudonyme() +"» "+ cpt.getNom() +" fait maintenant partie de votre équipage !");
                                // suppression des autres demandes
                                LinkedList<DemandeEquipe> listeDemandes = (LinkedList<DemandeEquipe>) deDao.findByIdCompte(cpt.getIdCompte());
                                listeDemandes.forEach(demande->{
                                    if (demande.getIdDemandeEquipe() != demandeEq.getIdDemandeEquipe()) {
                                        deDao.delete(demande);
                                }});
                                
                            }
                        }else{
                            data.put("avertissementDemande", "Impossible d'accepter la demande puisque votre équipe est pleine");
                        }
                   
                    }   
                }
                    
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction
                        .class.getName()).log(Level.SEVERE, null, ex);
                action = "echec.do?tache=afficherPageAcceuil";
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{Connexion.close();}
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

    @Override
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }
}
