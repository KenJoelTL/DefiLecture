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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import jdbc.Config;
import jdbc.Connexion;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerModificationCompteAction extends Action
    implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {

    if (((userIsConnected())
                && request
                    .getParameter("idCompte")
                    .equals(session.getAttribute("connecte").toString())
            || (!request
                    .getParameter("idCompte")
                    .equals(session.getAttribute("connecte").toString())
                && (int) session.getAttribute("role") > Compte.CAPITAINE))
        && (request.getParameter("modifie") != null)) {

      String idCompte = request.getParameter("idCompte"),
          courriel = request.getParameter("courriel"),
          prenom = request.getParameter("prenom"),
          nom = request.getParameter("nom"),
          programmeEtude = request.getParameter("programmeEtude"),
          motPasseActuel = request.getParameter("motPasseActuel"),
          motPasseNouveau = request.getParameter("motPasseNouveau"),
          motPasseNouveauConfirmation = request.getParameter("motPasseNouveauConfirmation"),
          pseudonyme = request.getParameter("pseudonyme");

      if (motPasseActuel != null) {
        motPasseActuel = motPasseActuel;
      }
      if (motPasseNouveau != null) {
        motPasseNouveau = motPasseNouveau;
      }
      if (motPasseNouveauConfirmation != null) {
        motPasseNouveauConfirmation = motPasseNouveauConfirmation;
      }

      int idEquipe, minutesRestantes, pointage, role;
      boolean erreurSurvenue = false;
      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        CompteDAO dao = new CompteDAO(cnx);
        Compte compte = dao.read(idCompte);
        if (compte == null) {
          data.put("compteIntrouvable", "Le compte que vous tentez de modifier est introuvable");
          if ((int) session.getAttribute("role") > Compte.CAPITAINE) {
            return "*.do?tache=afficherPageGestionListeCompte";
          } else {
            return "*.do?tache=afficherMarcheASuivre";
          }
        } else {
          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          dao.setCnx(cnx);

          if (request.getParameter("role") != null) {
            try {
              role = Integer.parseInt(request.getParameter("role"));
              if (role != compte.getRole()) {
                compte.setRole(role);
              }
            } catch (NumberFormatException e) {
            }
          }
          if (request.getParameter("minutesRestantes") != null) {
            try {
              minutesRestantes = Integer.parseInt(request.getParameter("minutesRestantes"));
              if (minutesRestantes != compte.getMinutesRestantes()) {
                compte.setMinutesRestantes(minutesRestantes);
              }
            } catch (NumberFormatException e) {
            }
          }
          if (request.getParameter("pointage") != null) {
            try {
              pointage = Integer.parseInt(request.getParameter("pointage"));
              if (pointage != compte.getPoint()) {
                compte.setPoint(pointage);
              }
            } catch (NumberFormatException e) {
            }
          }
          if (request.getParameter("idEquipe")
              != null) { // lorsque quelqu'un quitte son equipe idEquipe = -1
            try {
              idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
              if (idEquipe != compte.getIdEquipe()) {
                compte.setIdEquipe(idEquipe);
              }
            } catch (NumberFormatException e) {
            }
          }
          if (courriel != null
              && !"".equals(courriel.trim())
              && !courriel.equals(compte.getCourriel())) {
            Compte compteTemp = dao.findByCourriel(courriel);
            if (compteTemp != null && (compteTemp.getIdCompte() != compte.getIdCompte())) {
              erreurSurvenue = true;
              data.put("erreurCourriel", "Ce courriel est déjà utilisé par un autre matelot");
            } else {
              compte.setCourriel(courriel);
            }
          }
          if (prenom != null && !"".equals(prenom.trim()) && !prenom.equals(compte.getPrenom())) {
            compte.setPrenom(prenom);
          }
          if (nom != null && !"".equals(nom.trim()) && !nom.equals(compte.getNom())) {
            compte.setNom(nom);
          }
          if (motPasseNouveau != null && !"".equals(motPasseNouveau.trim())) {
            if (motPasseActuel != null && motPasseActuel.equals(compte.getMotPasse())) {
              if (motPasseNouveau.equals(motPasseNouveauConfirmation)) {
                compte.setMotPasse(motPasseNouveau);
              } else {
                erreurSurvenue = true;
                data.put(
                    "erreurMotPasse",
                    "Les champs concernant le nouveau mot de passe doivent être identiques");
              }
            } else {
              erreurSurvenue = true;
              data.put("erreurMotPasse", "Le mot de passe entré n'est pas le bon");
            }
          }
          if (pseudonyme != null && !pseudonyme.equals(compte.getPseudonyme())) {
            Compte compteTemp = dao.findByPseudonyme(pseudonyme);
            if (compteTemp != null && (compteTemp.getIdCompte() != compte.getIdCompte())) {
              erreurSurvenue = true;
              data.put("erreurPseudonyme", "Ce pseudonyme est déjà utilisé par un autre matelot");
            } else {
              compte.setPseudonyme(pseudonyme);
            }
          }
          if (programmeEtude != null && !programmeEtude.equals(compte.getProgrammeEtude())) {
            compte.setProgrammeEtude(programmeEtude);
          }

          if (erreurSurvenue) {
            return "*.do?tache=afficherPageModificationCompte&id=" + compte.getIdCompte();
          } else if (!dao.update(compte)) {
            data.put(
                "erreurModification",
                "Un problème est survenu lors de l'enregistrement des informations");
            return "*.do?tache=afficherPageModificationCompte&id=" + compte.getIdCompte();
          } else {
            // il faut avertir que les changements ont étés faits
            data.put(
                "succesModification",
                "Le compte du moussaillon " + compte.getCourriel() + " a été correctement modifié");
            return "*.do?tache=afficherPageModificationCompte&id=" + compte.getIdCompte();
          }
        }
      } catch (SQLException ex) {
        data.put(
            "erreurModification",
            "Un problème est survenu lors de l'enregistrement des informations");
        return "*.do?tache=afficherPageModificationCompte&id=" + request.getParameter("idCompte");
      }
    } else {
      return "*.do?tache=afficherPageGestionListeCompte";
    }
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
