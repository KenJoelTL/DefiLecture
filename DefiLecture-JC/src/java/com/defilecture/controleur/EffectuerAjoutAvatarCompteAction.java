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
import javax.servlet.http.Part;
import jdbc.Config;
import jdbc.Connexion;

@MultipartConfig
public class EffectuerAjoutAvatarCompteAction extends Action
  implements RequirePRGAction, DataSender {

  private HashMap data;

  @Override
  public String execute() {
    String action = "redirection.do?tache=afficherTableauScores";

    // Seul le membre connecté peut modifier son propre avatar
    if (userIsConnected()) {
      OutputStream out = null;
      InputStream filecontent = null;
      String absolutePath = "";
      
      try {
	int idCompte;	
	if(userIsAdmin() || userIsModerateur()){
	  idCompte = Integer.parseInt(request.getParameter("idCompte"));
	}
	else{
	  idCompte = ((Integer) session.getAttribute("currentId")).intValue();
	}
	action = "*.do?tache=afficherPageModificationCompte&id=" + idCompte;

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

        Connection cnx =
	  Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
        CompteDAO dao = new CompteDAO(cnx);
        Compte compte = dao.read(idCompte);
        if (compte != null) {
          compte.setAvatar(relativePath);
          if (dao.update(compte)) {
            data.put("succesAvatar", "L'enregistrement de l'avatar s'est effectué avec succès");
          } else {
            data.put("erreurAvatar", "Impossible d'enregistrer votre avatar");
          }
        } else {
          data.put(
		   "erreurAvatar", "Le compte que vous tentez de modifier l'Avatar est introuvable");
        }

      } catch (FileNotFoundException fne) {
        Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName())
	  .log(Level.SEVERE, "\nImpossible d'atteindre la destination : " + absolutePath, fne);
      } catch (NumberFormatException ex){
        Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName())
	  .log(Level.SEVERE, null, ex);
      } catch (IOException | ServletException | SQLException ex) {
        Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName())
	  .log(Level.SEVERE, null, ex);
      } finally {
        try {
          if (out != null) {
            out.close();
          } else if (filecontent != null) {
            filecontent.close();
          }
        } catch (IOException ex) {
          Logger.getLogger(EffectuerAjoutAvatarCompteAction.class.getName())
	    .log(Level.SEVERE, null, ex);
        }
      }
    }
    return action;
  }

  @Override
  public void setData(Map<String, Object> data) {
    this.data = (HashMap) data;
  }
}
