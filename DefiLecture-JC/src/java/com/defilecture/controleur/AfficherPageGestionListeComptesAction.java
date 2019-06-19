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

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.Config;
import jdbc.Connexion;
import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import java.sql.SQLException;

/**
 *
 * @author Joel
 * @author Mikaël
 */
public class AfficherPageGestionListeComptesAction implements Action  {
    @Override
    public String execute() {
            //Exclusivement pour l'Admin et le Modérateur.
        if( session.getAttribute("connecte") != null && session.getAttribute("role") != null) {
            try {
                if( ( (int)session.getAttribute("role") == Compte.MODERATEUR) 
                 || ( (int)session.getAttribute("role") == Compte.ADMINISTRATEUR)) {  

                    Class.forName(Config.DRIVER);
                    Connexion.setUrl(Config.URL);
                    Connexion.setUser(Config.DB_USER);
                    Connexion.setPassword(Config.DB_PWD);
                    Connection cnx = Connexion.getInstance();
                    CompteDAO dao = new CompteDAO(cnx);

                    if(dao.read((int)session.getAttribute("connecte"))!=null)
                        request.setAttribute("vue", "pageGestionListeCompte.jsp");

                    return "/index.jsp";
                    }
            }
            catch (ClassNotFoundException ex) {
                System.out.println("Erreur dans le chargement du pilote");
                Logger.getLogger(AfficherPageGestionListeComptesAction.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("vue", "accueil.jsp");
                return "/index.jsp";
            }
            catch(NullPointerException ex){
                System.out.println("L'utilisateur n'existe pas");
                return "/index.jsp";
            }
            catch (SQLException ex) {
                Logger.getLogger(AfficherPageGestionListeComptesAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally{
                Connexion.close();
            }
        }
        return "/index.jsp";
    
    }
}
