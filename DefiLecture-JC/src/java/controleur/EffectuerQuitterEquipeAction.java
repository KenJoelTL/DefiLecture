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
import modele.EquipeDAO;
import modele.DemandeEquipeDAO;
import modele.Equipe;

/**
 *
 * @author Joel
 */
public class EffectuerQuitterEquipeAction implements Action, RequestAware, RequirePRGAction, SessionAware{
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    @Override
    public String execute() {
        String action = "echec.do?tache=afficherPageAccueil";
        if(session.getAttribute("connecte") == null
            || session.getAttribute("role") == null
            || request.getParameter("idEquipe") == null
            || request.getParameter("idCompte") == null){ }
        else if(!request.getParameter("idCompte")
            .equals(session.getAttribute("connecte")+"")
            && ((int)session.getAttribute("role") != Compte.CAPITAINE)
            && ((int)session.getAttribute("role") != Compte.ADMINISTRATEUR)){}
        else{
            try {
                String idCompte = request.getParameter("idCompte");
                String idEquipe = request.getParameter("idEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                EquipeDAO equipeDao = new EquipeDAO(cnx);
                CompteDAO compteDao = new CompteDAO(cnx);
                Compte compte = compteDao.read(idCompte);
                Equipe equipe = equipeDao.read(idEquipe); 
                if(compte != null && equipe != null 
                    && equipe.getIdEquipe() == compte.getIdEquipe()){
                    
                    DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
                    DemandeEquipe demandeEquipe = 
                            demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());
                    
                    if(demandeEquipe != null){
                        if(demandeEqpDao.delete(demandeEquipe)){
                            compte.setIdEquipe(-1);
                            compteDao.update(compte);
                            action="auRevoir.do?tache=afficherPageEquipe&idEquipe="+idEquipe;
                        }
                        else
                            action="tuRestes.do?tache=afficherPageEquipe&idEquipe="+idEquipe;
                       // demandeEquipe.setStatutDemande(0); //met à 0 si l'utilisateur est suspendu
                        //si l'un des enregistrements échouent alors on revient à l'état initial 
                     /*   if(!demandeEqpDao.update(demandeEquipe) || !compteDao.update(compte)){
                            demandeEquipe.setStatutDemande(1);
                            compte.setIdEquipe(equipe.getIdEquipe());
                            demandeEqpDao.update(demandeEquipe);
                            compteDao.update(compte);
                            action = "echec.do?tache=afficherPageEquipe&idEquipe="+idEquipe; 
                        }
                        else{}*/
                    }
                }
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerQuitterEquipeAction.class.getName())
                        .log(Level.SEVERE, null, ex);
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
    
    
}
