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
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.DemandeEquipe;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.Equipe;
import com.defiLecture.modele.EquipeDAO;
import java.sql.Connection;
import java.sql.SQLException;
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
public class EffectuerDepartEquipeAction
    implements Action, RequestAware, RequirePRGAction, SessionAware, DataSender {
  private HttpServletRequest request;
  private HttpServletResponse response;
  private HttpSession session;
  private HashMap data;

  @Override
  public String execute() {
    String action = "echec.do?tache=afficherPageAccueil";
    if (session.getAttribute("connecte") == null
        || session.getAttribute("role") == null
        || request.getParameter("idEquipe") == null
        || request.getParameter("idCompte") == null) {
    } else if (!request.getParameter("idCompte").equals(session.getAttribute("connecte") + "")
        && ((int) session.getAttribute("role") != Compte.CAPITAINE)
        && ((int) session.getAttribute("role") != Compte.ADMINISTRATEUR)) {
    } else {
      try {
        String idCompte = request.getParameter("idCompte");
        String idEquipe = request.getParameter("idEquipe");
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        EquipeDAO equipeDao = new EquipeDAO(cnx);
        CompteDAO compteDao = new CompteDAO(cnx);
        Compte compte = compteDao.read(idCompte);
        Equipe equipe = equipeDao.read(idEquipe);
        if (compte != null && equipe != null && equipe.getIdEquipe() == compte.getIdEquipe()) {

          DemandeEquipeDAO demandeEqpDao = new DemandeEquipeDAO(cnx);
          DemandeEquipe demandeEquipe =
              demandeEqpDao.findByIdCompteEquipe(compte.getIdCompte(), equipe.getIdEquipe());

          if (demandeEquipe != null) {
            if (demandeEqpDao.delete(demandeEquipe)) {
              compte.setIdEquipe(-1);
              compteDao.update(compte);
              action = "auRevoir.do?tache=afficherPageEquipe&idEquipe=" + idEquipe;
              data.put(
                  "succesRetrait",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " a été envoyé par-dessus bord");
            } else {
              action = "tuRestes.do?tache=afficherPageEquipe&idEquipe=" + idEquipe;
              data.put(
                  "erreurRetrait",
                  "Le matelot "
                      + compte.getPrenom()
                      + " "
                      + compte.getNom()
                      + " n'a pas été envoyé par-dessus bord");

              // demandeEquipe.setStatutDemande(0); //met à 0 si l'utilisateur est suspendu
              // si l'un des enregistrements échouent alors on revient à l'état initial
              /*   if(!demandeEqpDao.update(demandeEquipe) || !compteDao.update(compte)){
              demandeEquipe.setStatutDemande(1);
              compte.setIdEquipe(equipe.getIdEquipe());
              demandeEqpDao.update(demandeEquipe);
              compteDao.update(compte);
              action = "echec.do?tache=afficherPageEquipe&idEquipe="+idEquipe; */
            }
          }
        }

      } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(EffectuerDepartEquipeAction.class.getName()).log(Level.SEVERE, null, ex);
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
