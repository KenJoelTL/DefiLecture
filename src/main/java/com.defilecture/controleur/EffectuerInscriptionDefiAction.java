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
import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.InscriptionDefi;
import com.defilecture.modele.InscriptionDefiDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerInscriptionDefiAction extends Action implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {
    if (userIsConnected()
        && (userIsParticipant() || userIsCapitaine())
        && request.getParameter("valider") != null) {

      if (LocalDateTime.now().isBefore(getDébutInscriptions())
          || LocalDateTime.now().isAfter(getFinInscriptions())) {
        return "*.do?tache=afficherPageParticipationDefi";
      }

      String reponseParticipant = Util.toUTF8(request.getParameter("reponseParticipant"));
      int idCompte = ((Integer) (session.getAttribute("currentId"))).intValue();
      int idDefi = Integer.parseInt(request.getParameter("idDefi"));
      InscriptionDefi inscriptionDefi = new InscriptionDefi();

      try {
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        DefiDAO daoDefi = new DefiDAO(cnx);
        Defi defi = daoDefi.read(idDefi);

        if (defi != null) {
          // Si le participant a déjà fait le défi, on ne crée pas une nouvelle
          // inscription_defi
          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          InscriptionDefiDAO daoInscriptionDefi = new InscriptionDefiDAO(cnx);
          List<InscriptionDefi> listeInscriptionDefi =
              daoInscriptionDefi.findAllByIdCompte(idCompte);

          for (InscriptionDefi i : listeInscriptionDefi) {
            if (i.getIdDefi() == idDefi) {
              return "*.do?tache=afficherPageParticipationDefi";
            }
          }

          cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
          daoInscriptionDefi = new InscriptionDefiDAO(cnx);

          inscriptionDefi.setIdCompte(idCompte);
          inscriptionDefi.setIdDefi(idDefi);
          // On vérifie si la réponse du participant est la bonne réponse
          // Si non, une inscription_defi est crée, avec le résultat 0 (échoué)
          if (!defi.getReponse().equals(reponseParticipant)) {

            inscriptionDefi.setValeurMinute(0);
            inscriptionDefi.setEstReussi(0);

          } else {
            // Si oui, une inscription_defi est crée, avec le résultat 1 (réussie)
            inscriptionDefi.setValeurMinute(defi.getValeurMinute());
            inscriptionDefi.setEstReussi(1);

            // Mise à jour des points du participant
            // Conversion du nombre de minutes de la lecture en points pour le
            // Participant : 15mins
            // = 1 point
            CompteDAO daoCompte = new CompteDAO(cnx);
            Compte compte = new Compte();
            compte = daoCompte.read(idCompte);
            int pointDefi = defi.getValeurMinute();
            int pointCompte = compte.getPoint() + pointDefi;
            // Les minutes restantes sont gardées en mémoire ici
            compte.setPoint(pointCompte);
            daoCompte.update(compte);

            // Mise à jour des points dans demande_equipe (pour calculer le total des
            // points de
            // l'équipe)
            if (compte.getIdEquipe() > 0) {
              DemandeEquipeDAO demandeDAO = new DemandeEquipeDAO(cnx);
              DemandeEquipe demande = new DemandeEquipe();
              demande = demandeDAO.findByIdCompteEquipe(idCompte, compte.getIdEquipe());
              int pointDemandeEquipe = demande.getPoint() + pointDefi;
              demande.setPoint(pointDemandeEquipe);
              demandeDAO.update(demande);
            }
          }

          // Création de l'inscription_defi dans la base de données
          daoInscriptionDefi.create(inscriptionDefi);
        }

      } catch (SQLException ex) {
        Logger.getLogger(EffectuerInscriptionDefiAction.class.getName())
            .log(Level.SEVERE, null, ex);
      }
    }
    return "*.do?tache=afficherPageParticipationDefi";
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
