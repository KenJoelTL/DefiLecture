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
public class EffectuerSuppressionDemandeAdhesionAction implements Action, SessionAware, RequestAware, RequirePRGAction, DataSender {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    HashMap data;

    @Override
    public String execute() {
        String action = "*.do?tache=afficherPageAccueil";
        if (session.getAttribute("connecte") == null
                || session.getAttribute("role") == null
                || request.getParameter("idDemandeEquipe") == null) {
            action = ".do?tache=afficherPageAccueil";
        } else {
            try {
                String idDemandeEq = request.getParameter("idDemandeEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

                DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
                DemandeEquipe demandeEq = deDao.read(idDemandeEq);

                if (demandeEq != null) {
                    EquipeDAO eqDao = new EquipeDAO(cnx);
                    Equipe eq;
                    eq = eqDao.read(demandeEq.getIdEquipe());

                    CompteDAO compteDao = new CompteDAO(cnx);
                    Compte compte;
                    compte = compteDao.read(demandeEq.getIdCompte());

                    if ((demandeEq.getIdCompte() == (int) session.getAttribute("connecte"))
                            || ((int) session.getAttribute("role") == Compte.CAPITAINE)
                            || ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR)) {
                        if (!deDao.delete(demandeEq)) {
                            action = "*.do?tache=afficherPageAccueil";
                        } else {
                            if (request.getParameter("ordre") != null && "recu".equals(request.getParameter("ordre")) && (int) session.getAttribute("role") == Compte.CAPITAINE) {
                                data.put("succesRefus", "Demande du matelot " + compte.getPrenom() + " " + compte.getNom() + " refusée");
                                action = "refus.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
                            } else {
                                data.put("succesAnnulation", "Votre demande d'adhésion à l'équipage " + eq.getNom() + " est annulée");
                                action = "annulation.do?tache=afficherPageListeEquipes";
                            }
                        }
                    }

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction.class.getName()).log(Level.SEVERE, null, ex);
                action = "echec.do?tache=afficherPageAcceuil";
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerSuppressionDemandeAdhesionAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Connexion.close();
            }
        }
        return action;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
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
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }

}
