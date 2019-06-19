/**
 * This file is part of DefiLecture.
 *
 * <p>DefiLecture is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>DefiLecture is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with DefiLecture. If
 * not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerSuppressionDemandeAdhesionAction extends Action
    implements RequirePRGAction, DataSender, SendAjaxResponse {

  HashMap data;

  @Override
  public String execute() {
    String action = "bienvenue.do?tache=afficherPageAccueil";
    if (session.getAttribute("connecte") == null
        || session.getAttribute("role") == null
        || request.getParameter("idDemandeEquipe") == null) {
      action = "bienvenue.do?tache=afficherPageAccueil";
    } else {
      try {
        String idDemandeEq = request.getParameter("idDemandeEquipe");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
        DemandeEquipe demandeEq = deDao.read(idDemandeEq);
        response.setContentType("text/plain");

        if (demandeEq != null) {
          EquipeDAO eqDao = new EquipeDAO(cnx);
          Equipe eq = eqDao.read(demandeEq.getIdEquipe());

          CompteDAO compteDao = new CompteDAO(cnx);
          Compte compte = compteDao.read(demandeEq.getIdCompte());

          if ((demandeEq.getIdCompte() == (int) session.getAttribute("connecte"))
              || ((int) session.getAttribute("role") == Compte.CAPITAINE)
              || ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR)) {
            if (!deDao.delete(demandeEq)) {
              action = "annulation.do?tache=afficherPageListeEquipes";
              if ((int) session.getAttribute("role") == Compte.CAPITAINE) {
                action = "annulation.do?tache=afficherPageListeDemandesEquipe&ordre=recu";

                try {
                  String msg =
                      "Impossible de refuser la demande puisqu'elle a été retirée par le matelot";
                  String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"avertissement\"}";
                  response.getWriter().write(json);

                } catch (IOException ex) {
                  Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                      .log(Level.SEVERE, null, ex);
                }
              }
            } else {
              if (request.getParameter("ordre") != null
                  && "recu".equals(request.getParameter("ordre"))
                  && (int) session.getAttribute("role") == Compte.CAPITAINE) {
                try {
                  String msg =
                      "Demande du matelot "
                          + compte.getPrenom()
                          + " «"
                          + compte.getPseudonyme()
                          + "» "
                          + compte.getNom()
                          + " refusée";
                  String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"succes\"}";
                  response.getWriter().write(json);
                } catch (IOException ex) {
                  Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                      .log(Level.SEVERE, null, ex);
                }

                action = "refus.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
              } else {
                try {
                  String msg =
                      "Votre demande d'adhésion à l'équipage " + eq.getNom() + " a été retirée";
                  String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"succes\"}";
                  response.getWriter().write(json);
                } catch (IOException ex) {
                  Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                      .log(Level.SEVERE, null, ex);
                }
                action = "annulation.do?tache=afficherPageListeEquipes";
              }
            }
          }

        } else {
          action = "annulation.do?tache=afficherPageListeEquipes";
          if ((int) session.getAttribute("role") == Compte.CAPITAINE) {
            action = "annulation.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
            try {
              String msg =
                  "Impossible de refuser la demande puisqu'elle a été retirée par le matelot";
              String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"avertissement\"}";
              response.getWriter().write(json);
            } catch (IOException ex) {
              Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                  .log(Level.SEVERE, null, ex);
            }
          }
        }

      } catch (SQLException ex) {
        Logger.getLogger(EffectuerSuppressionDemandeAdhesionAction.class.getName())
            .log(Level.SEVERE, null, ex);
      } finally {
        Connexion.close();
      }
    }
    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
