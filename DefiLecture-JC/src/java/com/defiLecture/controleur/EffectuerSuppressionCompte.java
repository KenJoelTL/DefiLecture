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

/**
 *
 * @author Joel
 */
public class EffectuerSuppressionCompte implements Action, RequestAware, SessionAware, RequirePRGAction {

    private HttpServletResponse reponse;
    private HttpServletRequest request;
    private HttpSession session;

    @Override
    public String execute() {
        String action = "Acceuil.do?tache=afficherPageAccueil";
        if (session.getAttribute("connecte") == null
                || session.getAttribute("role") == null
                || request.getParameter("idCompte") == null) {
        } else if (!request.getParameter("idCompte")
                .equals(session.getAttribute("connecte") + "")
                && ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR)) {
            try {
                String idCompte = request.getParameter("idCompte");
                String idEquipe = request.getParameter("idEquipe");
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                CompteDAO compteDao = new CompteDAO(cnx);
                Compte compte = compteDao.read(idCompte);

                if (compte != null) {
                    if (compteDao.delete(compte)) {
                        action = "succes.do?tache=afficherPageGestionListComptes";
                    } else {
                        action = "echec.do?tache=afficherPageGestionListComptes";
                    }

                } else {
                    action = "echec.do?tache=afficherPageGestionListComptes";
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerDepartEquipeAction.class.getName())
                        .log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerSuppressionCompte.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
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
        this.reponse = response;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
}
