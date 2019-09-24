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
import com.defilecture.modele.ConfigSiteDAO;
import com.defilecture.modele.DefiDAO;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.EquipeDAO;
import com.defilecture.modele.InscriptionDefiDAO;
import com.defilecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerSuppressionBDAction extends Action implements RequirePRGAction {

  private DefiDAO dDao;
  private CompteDAO daoCompte;
  private DemandeEquipeDAO dEDao;
  private EquipeDAO eDao;
  private InscriptionDefiDAO iDDao;
  private LectureDAO lDao;
  private ConfigSiteDAO cSiteDao;

  @Override
  public String execute() {
    if (userIsAdmin()
        && (request.getParameter("passwordConf") != null
            && !"".equals(request.getParameter("passwordConf")))) {
      try {
        Connexion.reinit();
        Connection cnx =
            Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        daoCompte = new CompteDAO(cnx);
        dDao = new DefiDAO(cnx);
        dEDao = new DemandeEquipeDAO(cnx);
        eDao = new EquipeDAO(cnx);
        iDDao = new InscriptionDefiDAO(cnx);
        lDao = new LectureDAO(cnx);
        cSiteDao = new ConfigSiteDAO(cnx);

        // Vérification du mot de passe de l'usager administrateur
        Compte compteActuel =
            daoCompte.read(((Integer) session.getAttribute("currentId")).intValue());

        if (compteActuel.verifierMotPasse(Util.toUTF8(request.getParameter("passwordConf")))) {
          dDao.deleteTable();
          dEDao.deleteTable();
          eDao.deleteTable();
          iDDao.deleteTable();
          lDao.deleteTable();
          cSiteDao.deleteTable();
          daoCompte.deleteTable();
          return "*.do?tache=effectuerDeconnexion";
        }
      } catch (SQLException ex) {
        Logger.getLogger(EffectuerSuppressionBDAction.class.getName()).log(Level.SEVERE, null, ex);
        return "*.do?tache=afficherPageConfiguration";
      }
    }
    return "*.do?tache=afficherPageConfiguration";
  }
}
