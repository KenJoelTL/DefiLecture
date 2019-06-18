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
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

/** @author Joel */
public class EffectuerAcceptationDemandeAdhesionAction
    implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender, SendAjaxResponse {
  HttpServletResponse response;
  HttpServletRequest request;
  HttpSession session;
  HashMap data;

  @Override
  public String execute() {
    String action = ".do?tache=afficherPageAccueil";
    if (session.getAttribute("connecte") == null
        || session.getAttribute("role") == null
        || (((int) session.getAttribute("role") != Compte.CAPITAINE)
            && ((int) session.getAttribute("role") != Compte.ADMINISTRATEUR))
        || request.getParameter("idDemandeEquipe") == null) {
      action = ".do?tache=afficherPageAccueil";
    } else {
      try {
        String idDemandeEq = request.getParameter("idDemandeEquipe");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        DemandeEquipeDAO deDao = new DemandeEquipeDAO(cnx);
        DemandeEquipe demandeEq = deDao.read(idDemandeEq);
        response.setContentType("text/plain");

        if (demandeEq == null) {
          action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
          try {
            String msg =
                "Impossible d'accepter la demande puisqu'elle a été retirée par le matelot";
            String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"avertissement\"}";
            response.getWriter().write(json);

          } catch (IOException ex) {
            Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                .log(Level.SEVERE, null, ex);
          }
        } else {
          CompteDAO compteDao = new CompteDAO(cnx);
          Compte cpt = compteDao.read(demandeEq.getIdCompte());
          action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
          if (cpt == null) {
            action = "*.do?tache=afficherPageListeDemandesEquipe&ordre=recu";
            try {
              String msg = "Le matelot auteur de cette demande est à la retraite.";
              String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"danger\"}";
              response.getWriter().write(json);

            } catch (IOException ex) {
              Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                  .log(Level.SEVERE, null, ex);
            }
          } else if (cpt.getIdEquipe() != -1) {
            try {

              String msg =
                  "Le matelot "
                      + cpt.getPrenom()
                      + "«"
                      + cpt.getPseudonyme()
                      + "»"
                      + cpt.getNom()
                      + " fait déjà partie d'un équipage.";
              String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"danger\"}";
              response.getWriter().write(json);
            } catch (IOException ex) {
              Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                  .log(Level.SEVERE, null, ex);
            }
          } else {
            int idEquipe = demandeEq.getIdEquipe();
            int nbMembre = compteDao.countCompteByIdEquipe(idEquipe);
            if (nbMembre < Equipe.NB_MAX_MEMBRES) {
              cpt.setIdEquipe(idEquipe);
              demandeEq.setStatutDemande(DemandeEquipe.ACCEPTEE);
              if (deDao.update(demandeEq) && compteDao.update(cpt)) {
                action = "ajoutReussi.do?tache=afficherPageListeDemandesEquipe&ordre=recu";

                try {
                  String msg =
                      "Le matelot "
                          + cpt.getPrenom()
                          + "«"
                          + cpt.getPseudonyme()
                          + "»"
                          + cpt.getNom()
                          + " fait maintenant partie de votre équipage !";
                  String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"succes\"}";
                  response.getWriter().write(json);

                } catch (IOException ex) {
                  Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                      .log(Level.SEVERE, null, ex);
                }
                ArrayList<DemandeEquipe> listeDemandes =
                    (ArrayList<DemandeEquipe>) deDao.findByIdCompte(cpt.getIdCompte());
                listeDemandes.forEach(
                    demande -> {
                      if (demande.getIdDemandeEquipe() != demandeEq.getIdDemandeEquipe()) {
                        deDao.delete(demande);
                      }
                    });
              }
            } else {
              try {
                String msg = "Impossible d'accepter la demande puisque votre équipe est pleine";
                String json = "{\"msg\":\"" + msg + "\",\"typeAlert\" :\"avertissement\"}";
                response.getWriter().write(json);

              } catch (IOException ex) {
                Logger.getLogger(EffectuerDemandeAdhesionEquipeAction.class.getName())
                    .log(Level.SEVERE, null, ex);
              }
            }
          }
        }

      } catch (ClassNotFoundException ex) {
        Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction.class.getName())
            .log(Level.SEVERE, null, ex);
        action = "echec.do?tache=afficherPageAcceuil";
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerAcceptationDemandeAdhesionAction.class.getName())
            .log(Level.SEVERE, null, ex);
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
