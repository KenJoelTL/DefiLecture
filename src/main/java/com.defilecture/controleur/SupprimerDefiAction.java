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
import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.InscriptionDefi;
import com.defilecture.modele.InscriptionDefiDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class SupprimerDefiAction extends Action implements RequirePRGAction {
  private HashMap data;

  @Override
  public String execute() {
    String action = "*.do?tache=afficherPageParticipationDefi";
    try {
      Connection cnx =
          Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
      InscriptionDefiDAO IDdao = new InscriptionDefiDAO(cnx);
      CompteDAO Cdao = new CompteDAO(cnx);
      DefiDAO Ddao = new DefiDAO(cnx);
      DemandeEquipeDAO Dedao = new DemandeEquipeDAO(cnx);
      Defi defi;
      defi = Ddao.read(Integer.valueOf(request.getParameter("idDefiSup")));

      // Permets de passer au travers de toutes les inscriptions du défi qui doit être
      // supprimé
      List<InscriptionDefi> listID =
          IDdao.findByIdDefi(Integer.valueOf(request.getParameter("idDefiSup")));
      listID.forEach(
          (insDe) -> {
            Compte profil = Cdao.read(insDe.getIdCompte());
            IDdao.delete(insDe);

            // Permets d'enlever les points de l'équipe, gagner par le défi
            List<DemandeEquipe> listDe = Dedao.findByIdCompte(profil.getIdCompte());
            listDe.forEach(
                (demandeE) -> {
                  demandeE.setPoint(demandeE.getPoint() - insDe.getValeurMinute());
                  Dedao.update(demandeE);
                });

            // Permets d'enlever les points gagnés par le défi aux comptes qui s'y sont
            // inscrits
            profil.setPoint(profil.getPoint() - insDe.getValeurMinute());
            Cdao.update(profil);
          });
      Ddao.delete(defi);
    } catch (SQLException ex) {
      Logger.getLogger(SupprimerDefiAction.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(SupprimerDefiAction.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      Connexion.close();
    }
    return action;
  }

  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
