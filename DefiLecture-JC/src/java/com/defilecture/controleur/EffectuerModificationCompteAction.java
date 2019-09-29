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

import com.defilecture.Util;
import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerModificationCompteAction extends Action
    implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {

    if ((userIsConnected()
            && request.getParameter("modifie") != null
            && request
                .getParameter("idCompte")
                .equals(session.getAttribute("currentId").toString()))
        || (!request.getParameter("idCompte").equals(session.getAttribute("currentId").toString())
            && ((Integer) session.getAttribute("role")).intValue() == Compte.ADMINISTRATEUR)) {

      String idCompte = request.getParameter("idCompte"),
          courriel = request.getParameter("courriel"),
          prenom = request.getParameter("prenom"),
          nom = request.getParameter("nom"),
          programmeEtude = request.getParameter("programmeEtude"),
          sel = Util.genererSel(),
          motPasseActuel = request.getParameter("motPasseActuel"),
          motPasseNouveau = request.getParameter("motPasseNouveau"),
          motPasseNouveauConfirmation = request.getParameter("motPasseNouveauConfirmation"),
          pseudonyme = request.getParameter("pseudonyme");

      int idEquipe, minutesRestantes, pointage, role;
      boolean erreurSurvenue = false;
      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

        CompteDAO dao = new CompteDAO(cnx);
        Compte compte = dao.read(idCompte);
        if (compte == null) {
          data.put("compteIntrouvable", "Le compte que vous tentez de modifier est introuvable");
          return ((Integer) session.getAttribute("role")).intValue() > Compte.CAPITAINE
              ? "*.do?tache=afficherPageGestionComptes"
              : "*.do?tache=afficherMarcheASuivre";
        }
        cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        dao.setCnx(cnx);

        if (courriel != null
            && !"".equals(courriel.trim())
            && !courriel.equals(compte.getCourriel())) {
          if (dao.findByCourriel(courriel) == null) {
            compte.setCourriel(Util.toUTF8(courriel));
          } else {
            erreurSurvenue = true;
            data.put("erreurCourriel", "Ce courriel est déjà utilisé par un autre matelot");
          }
        }

        if (prenom != null && !"".equals(prenom.trim()) && !prenom.equals(compte.getPrenom())) {
          compte.setPrenom(Util.toUTF8(prenom));
        }

        if (nom != null && !"".equals(nom.trim()) && !nom.equals(compte.getNom())) {
          compte.setNom(Util.toUTF8(nom));
        }

        if (motPasseNouveau != null && !"".equals(motPasseNouveau.trim())) {
          if (motPasseActuel != null && compte.verifierMotPasse(motPasseActuel) || userIsAdmin()) {
            if (motPasseNouveau.equals(motPasseNouveauConfirmation)) {
              compte.setMotPasse(Util.toUTF8(motPasseNouveau));
            } else {
              erreurSurvenue = true;
              data.put(
                  "erreurMotPasse",
                  "Les champs concernant le nouveau mot de passe doivent être identiques");
            }
          } else {
            erreurSurvenue = true;
            data.put("erreurMotPasse", "Mot de passe incorrect");
          }
        }
        if (pseudonyme != null && !pseudonyme.equals(compte.getPseudonyme())) {
          Compte compteTemp = dao.findByPseudonyme(pseudonyme);
          if (compteTemp != null && (compteTemp.getIdCompte() != compte.getIdCompte())) {
            erreurSurvenue = true;
            data.put("erreurPseudonyme", "Ce pseudonyme est déjà utilisé par un autre matelot");
          } else {
            compte.setPseudonyme(Util.toUTF8(pseudonyme));
          }
        }
        if (programmeEtude != null && !programmeEtude.equals(compte.getProgrammeEtude())) {
          compte.setProgrammeEtude(Util.toUTF8(programmeEtude));
        }

        if (((Integer) session.getAttribute("role")).intValue() == Compte.ADMINISTRATEUR) {
          if (request.getParameter("role") != null) {
            compte.setRole(Integer.parseInt(request.getParameter("role")));
          }

          if (request.getParameter("minutesRestantes") != null) {
            compte.setMinutesRestantes(Integer.parseInt(request.getParameter("minutesRestantes")));
          }

          if (request.getParameter("pointage") != null) {
            compte.setPoint(Integer.parseInt(request.getParameter("pointage")));
          }

          if (request.getParameter("idEquipe") != null) {
            compte.setIdEquipe(Integer.parseInt(request.getParameter("idEquipe")));
          }
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
      } catch (SQLException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        data.put(
            "erreurModification",
            "Un problème est survenu lors de l'enregistrement des informations");
        return "*.do?tache=afficherPageModificationCompte&id=" + request.getParameter("idCompte");
      } catch (NumberFormatException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      return "*.do?tache=afficherPageGestionComptes";
    }
    return "*.do?tache=afficherPageConnexion";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
