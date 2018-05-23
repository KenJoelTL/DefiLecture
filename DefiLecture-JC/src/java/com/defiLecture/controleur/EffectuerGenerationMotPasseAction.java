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
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.util.Util;
import java.io.UnsupportedEncodingException;
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
import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

/**
 *
 * @author Joel
 */
public class EffectuerGenerationMotPasseAction implements Action, RequestAware, SessionAware, DataSender, RequirePRGAction {

    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;
    private HashMap data;

    @Override
    public String execute() {

        String action = "";
        if ((int) session.getAttribute("role") == Compte.ADMINISTRATEUR
                && request.getParameter("idCompte") != null) {
            try {
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                CompteDAO dao = new CompteDAO(cnx);

                Compte compte = dao.read(request.getParameter("idCompte"));
                if (compte != null) {
                    action = ".do?tache=afficherPageModificationCompte&id=" + request.getParameter("idCompte");
                    int longueur = 8;
                    String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
                    StringBuilder builder = new StringBuilder();
                    builder.append("DL-");
                    while (longueur != 0) {
                        int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
                        builder.append(ALPHA_NUMERIC_STRING.charAt(character));
                        longueur--;
                    }
                    String motPasse = builder.toString();
                    String motPasseHash = sha1Hex(motPasse);
                    compte.setMotPasse(motPasseHash);
                    if(dao.update(compte)){
                        data.put("succesGenerationMotPasse", "Nouveau mot de passe : " + motPasse);
                    }
                    else{
                        data.put("erreurGenerationMotPasse", "Une erreur est survenue lors de la modification du compte");
                    }

                } else {
                    data.put("erreurGenerationMotPasse", "Le compte que vous tentez de modifier est introuvable");
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EffectuerGenerationMotPasseAction.class.getName()).log(Level.SEVERE, null, ex);
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
