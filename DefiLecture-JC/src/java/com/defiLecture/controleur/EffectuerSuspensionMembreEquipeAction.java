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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joel
 */
public class EffectuerSuspensionMembreEquipeAction implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;
    private HashMap data;

    @Override
    public String execute() {
        String action = "Acceuil.do?tache=afficherPageAccueil";
        if(session.getAttribute("connecte") == null
            || session.getAttribute("role") == null
            || request.getParameter("idEquipe") == null
            || request.getParameter("idCompte") == null){}
        else if(!request.getParameter("idCompte")
            .equals(session.getAttribute("connecte")+"")
            &&( ((int)session.getAttribute("role") == Compte.CAPITAINE)
            || ((int)session.getAttribute("role") == Compte.ADMINISTRATEUR) ) )
        {    
            try {
                String idCompte = request.getParameter("idCompte");
                String idEquipe = request.getParameter("idEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                EquipeDAO equipeDao = new EquipeDAO(cnx);
                CompteDAO compteDao = new CompteDAO(cnx);
                Compte compte = compteDao.read(idCompte);
                Compte compteSup = compteDao.read((int)session.getAttribute("connecte"));
                Equipe equipe = equipeDao.read(idEquipe); 
                
                //si le compte connecté est au niveau de Capitaine, alors il faut qu'il soit membre de la même équipe pour suspendre un membre.
                //si le compte connecté est un Administrateur alors il peut suspendre un membre de n'importe quelle équipe
                if(compte != null && equipe != null && compteSup != null
                    && equipe.getIdEquipe() == compte.getIdEquipe() 
                        && ( (compte.getIdEquipe() == compteSup.getIdEquipe() && compteSup.getRole() == Compte.CAPITAINE)
                              || compteSup.getRole() == Compte.ADMINISTRATEUR) ){  
                    
                    DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
                    DemandeEquipe demandeEquipe = 
                            demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                    
                    if(demandeEquipe != null && demandeEquipe.getStatutDemande() == DemandeEquipe.ACCEPTEE){
                        demandeEquipe.setStatutDemande(DemandeEquipe.SUSPENDUE); 
                        //si l'un des enregistrements échouent alors on revient à l'état initial 
                        if(demandeEqpDao.update(demandeEquipe)){
                            /*
                            demandeEquipe.setStatutDemande(1);
                            compte.setIdEquipe(equipe.getIdEquipe());
                            demandeEqpDao.update(demandeEquipe);
                            compteDao.update(compte);*/
                            data.put("succesSuspension", "Le matelot "+ compte.getPrenom() + " " + compte.getNom() + " est maintenant à la cale");
                            action = "suspension.do?tache=afficherPageModificationEquipe&idEquipe="+idEquipe; 
                        }
                        else{
                            data.put("erreurSuspension", "Un erreur est arrivé lors de la tentative d'envoie matelot "+ compte.getPrenom() + " " + compte.getNom() + " à la cale");
                            action="tuRestes.do?tache=afficherPageEquipe&idEquipe="+idEquipe;
                        }
                    }
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerDepartEquipeAction.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerSuspensionMembreEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                Connexion.close();
            }

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
