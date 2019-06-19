/**
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;
import com.defilecture.modele.Compte;
import com.defilecture.modele.CompteDAO;
import com.defilecture.modele.DemandeEquipe;
import com.defilecture.modele.DemandeEquipeDAO;
import com.defilecture.modele.Equipe;
import com.defilecture.modele.EquipeDAO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Joel
 * @author Mikaël Nadeau
 */
public class EffectuerSuppressionCompteAction extends Action implements RequirePRGAction, DataSender {

    private HashMap data;

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
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                CompteDAO compteDao = new CompteDAO(cnx);
                Compte compte = compteDao.read(idCompte);

                if (compte != null) {
                    if (compteDao.delete(compte)) {
                        action = "succes.do?tache=afficherPageGestionListeCompte";
                        data.put("suppressionSucces", "Le compte "+ compte.getCourriel() +" a bien été supprimé");
                    } else {
                        action = "echec.do?tache=afficherPageGestionListCompte";
                        data.put("suppressionEchec", "Une erreur est survenue lors de la suppression");
                    }

                } else {
                    action = "echec.do?tache=afficherPageGestionListCompte";
                    data.put("suppressionEchec", "Le compte que vous tentez de supprimer n'existe pas");
                }

            } catch(SQLException ex) {
                Logger.getLogger(EffectuerSuppressionCompteAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                Connexion.close();
            }

        }

        return action;
    }

    @Override
    public void setData(Map<String, Object> data) {
         this.data = (HashMap)data;
    }
}
