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
import com.defilecture.modele.Defi;
import com.defilecture.modele.DefiDAO;
import java.sql.SQLException;
import com.util.Util;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Charles
 * @author Mikaël Nadeau
 */
public class EffectuerModificationDefiAction extends Action implements RequirePRGAction, DataSender {

    private HashMap data;

    @Override
    public String execute() {

        if (session.getAttribute("connecte") != null
                && session.getAttribute("role") != null
                && (((int) session.getAttribute("role") == Compte.MODERATEUR)
                || ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR))
                && request.getParameter("modifie") != null) {

            String nom = request.getParameter("nom"),
                    description = request.getParameter("description"),
                    heureDebut = request.getParameter("heureDebut"),
                    dateDebut = request.getParameter("dateDebut") + " " + heureDebut,
                    heureFin = request.getParameter("heureFin"),
                    dateFin = request.getParameter("dateFin") + " " + heureFin,
                    question = request.getParameter("question"),
                    reponse = request.getParameter("reponse"),
                    choixReponse = request.getParameter("choixReponseJSON"),
                    idDefi = request.getParameter("idDefi");

            int valeurMinute;

            try {
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

                Defi defi = new DefiDAO(cnx).read(idDefi);
                if (defi == null) {

                    return "*.do?tache=afficherPageParticipationDefi";
                } else {

                    if (nom != null && !"".equals(nom.trim()) && !nom.equals(defi.getNom())) {
                        defi.setNom(Util.toUTF8(nom));
                    }

                    if (request.getParameter("valeurMinute") != null) {
                        try {
                            valeurMinute = Integer.parseInt(request.getParameter("valeurMinute"));
                            if (valeurMinute != defi.getValeurMinute()) {
                                defi.setValeurMinute(valeurMinute);
                            }
                        } catch (NumberFormatException e) {
                        }
                    }

                    if (dateDebut != defi.getDateDebut()) {
                        defi.setDateDebut(dateDebut);
                    }
                    if (dateFin != defi.getDateFin()) {
                        defi.setDateFin(dateFin);
                    }

                    if (description != null && !"".equals(description.trim()) && !description.equals(defi.getDescription())) {
                        defi.setDescription(Util.toUTF8(description));
                    }

                    if (question != null && !"".equals(question.trim()) && !question.equals(defi.getQuestion())) {
                        defi.setQuestion(Util.toUTF8(question));
                    }

                    if (choixReponse != defi.getChoixReponse()) {
                        defi.setChoixReponse(Util.toUTF8(choixReponse));
                    }
                    if (reponse != defi.getReponse()) {
                        defi.setReponse(Util.toUTF8(reponse));
                    }

                    cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);

                    DefiDAO dao = new DefiDAO(cnx);
                    if (!dao.update(defi)) {
                        return "*.do?tache=afficherPageParticipationDefi";
                    } else {
                        return "*.do?tache=afficherPageParticipationDefi";
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(EffectuerModificationDefiAction.class.getName()).log(Level.SEVERE, null, ex);
                return "*.do?tache=afficherPageParticipationDefi";

            }
        } else {
            return "*.do?tache=afficherPageParticipationDefi";
        }

    }

    @Override
    public void setData(Map<String, Object> data) {
        this.data = (HashMap) data;
    }

}
