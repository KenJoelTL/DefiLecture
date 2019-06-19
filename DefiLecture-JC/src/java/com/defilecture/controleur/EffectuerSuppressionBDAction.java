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
package com.defiLecture.controleur;

import com.defiLecture.modele.Compte;
import com.defiLecture.modele.CompteDAO;
import com.defiLecture.modele.ConfigSiteDAO;
import com.defiLecture.modele.DefiDAO;
import com.defiLecture.modele.DemandeEquipeDAO;
import com.defiLecture.modele.EquipeDAO;
import com.defiLecture.modele.InscriptionDefiDAO;
import com.defiLecture.modele.LectureDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;

public class EffectuerSuppressionBDAction implements Action , RequestAware, SessionAware, RequirePRGAction{
    
    private HttpServletResponse response;
    private HttpServletRequest request;
    private HttpSession session;
    private DefiDAO dDao;
    private CompteDAO daoCompte;
    private DemandeEquipeDAO dEDao;
    private EquipeDAO eDao;
    private InscriptionDefiDAO iDDao;
    private LectureDAO lDao;
    private ConfigSiteDAO cSiteDao;

    @Override
    public String execute() {
        if((session.getAttribute("connecte") != null && session.getAttribute("role") != null)&& (int) session.getAttribute("role") >3)
        {
            try{
                Connexion.reinit();
                Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
                daoCompte = new CompteDAO(cnx);
                dDao = new DefiDAO(cnx);
                dEDao = new DemandeEquipeDAO(cnx);
                eDao = new EquipeDAO(cnx);
                iDDao = new InscriptionDefiDAO(cnx);
                lDao = new LectureDAO(cnx);
                cSiteDao = new ConfigSiteDAO(cnx);
                
                //Vérification du mot de passe de l'usager administrateur
                Compte compteAdmin = daoCompte.read((int)session.getAttribute("connecte"));
                Compte verif = daoCompte.findByIdentifiantMotPasse(compteAdmin.getPseudonyme(), org.apache.commons.codec.digest.DigestUtils.sha1Hex(request.getParameter("passwordConf")));
                if(verif != null){
                    dDao.deleteTable();
                    dEDao.deleteTable();
                    eDao.deleteTable();
                    iDDao.deleteTable();
                    lDao.deleteTable();
                    cSiteDao.deleteTable();
                    daoCompte.deleteTable();
                    return "*.do?tache=effectuerDeconnexion";
                }
            }
            catch(ClassNotFoundException e){
                System.out.println("Erreur dans le chargement du pilote :"+ e);
                return "*.do?tache=afficherPageConfiguration";
            } 
            catch (SQLException ex) {
                Logger.getLogger(EffectuerSuppressionBDAction.class.getName()).log(Level.SEVERE, null, ex);
                return "*.do?tache=afficherPageConfiguration";

            }
        }
       return "*.do?tache=afficherPageConfiguration";
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
    
}
