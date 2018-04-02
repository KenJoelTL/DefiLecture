/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class EffectuerConnexionAction implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HashMap data;

    @Override
    public String execute() {
        String action = "*.do?tache=afficherPageConnexion";
                            data.put("echecConnexion", "L'identifiant et/ou le mot de passe entré est invalide");

        if (session.getAttribute("connecte") == null
                && request.getParameter("identifiant") != null
                && request.getParameter("motPasse") != null) {
            String identifiant = request.getParameter("identifiant"),
                    motPasse = org.apache.commons.codec.digest.DigestUtils.sha1Hex(request.getParameter("motPasse"));
            try {
                CompteDAO dao
                        = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
                Compte compte
                        = dao.findByIdentifiantMotPasse(identifiant, motPasse);

                // On vérifie s'il y a un résultat    
                if (compte != null) {
                    session = request.getSession(true);
                    session.setAttribute("connecte", compte.getIdCompte());
                    session.setAttribute("role", compte.getRole());
                    action = "*.do?tache=afficherTableauScores";
                } else {
                    data.put("echecConnexion", "L'identifiant et/ou le mot de passe entré est invalide");
                    data.put("identifiant", identifiant);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Erreur dans le chargement du pilote :" + e);
                action = "*.do?tache=afficherPageConnexion";
                data.put("echecConnexion", "Un problème est survenu lors de la connexion");

            } catch (SQLException ex) {
                Logger.getLogger(EffectuerConnexionAction.class.getName()).log(Level.SEVERE, null, ex);
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
