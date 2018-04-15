/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.util.Util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import jdbc.Config;
import jdbc.Connexion;

/**
 *
 * @author Joel
 */
@MultipartConfig
public class EffectuerAjoutAvatarCompteAction implements Action, RequestAware, SessionAware, RequirePRGAction, DataSender {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private HashMap data;

    @Override
    public String execute() {
        String action = "redirection.do?tache=afficherTableauScores";
//      Seul le membre connecté peut modifier son propre avatar
        if (session.getAttribute("connecte") != null) {
            int idCompte = (int)session.getAttribute("connecte");
            action = "*.do?tache=afficherPageModificationCompte&id=" + idCompte;
            OutputStream out = null;
            InputStream filecontent = null;
            String absolutePath = "";

            try {
                final Part filePart = request.getPart("nomFichier");
                final String path = "/images/avatars";
                final String fileName = "avatarCompte_" + idCompte;

                String relativePath = path + "/" + fileName;
                absolutePath = session.getServletContext().getRealPath(relativePath);

                out = new FileOutputStream(new File(absolutePath));
                filecontent = filePart.getInputStream();

                int nbOctetsLus = 0;
                final byte[] bytes = new byte[1024];

                while ((nbOctetsLus = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, nbOctetsLus);
                }
                Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
                CompteDAO dao = new CompteDAO(cnx);
                Compte compte = dao.read(idCompte);
                if (compte != null) {
                    compte.setAvatar(Util.toUTF8(relativePath));
                    if (dao.update(compte)) {
                        data.put("succesAvatar","L'enregistrement de l'avatar s'est effectué avec succès");
                    } else {
                        data.put("erreurAvatar", "Impossible d'enregistrer votre avatar");
                    }
                }
                else{
                    data.put("erreurAvatar", "Le compte que vous tentez de modifier l'Avatar est introuvable");
                }

            } catch (FileNotFoundException fne) {
                System.out.println("\nImpossible d'atteindre la destination : " + absolutePath);
            } catch (IOException | ServletException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (filecontent != null) {
                    try {
                        filecontent.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
