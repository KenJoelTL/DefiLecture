/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.Config;
import jdbc.Connexion;
import modele.Compte;
import modele.CompteDAO;

/**
 *
 * @author Joel
 */
public class EffectuerModificationCompteAction implements Action, RequestAware{
    private HttpServletResponse response;
    private HttpServletRequest request;

    @Override
    public String execute() {
        if(request.getParameter("modifie")!=null){
            String idCompte = request.getParameter("idCompte");
   /*                 role = Integer.parseInt(request.getParameter("role"));
//            int idEquipe = Integer.parseInt(request.getParameter("idEquipe"));
       */       String  courriel = request.getParameter("courriel"),
                prenom = request.getParameter("prenom"),
                nom = request.getParameter("nom");/*
                //motPasse = request.getParameter("motPasse"),
                //programmeEtude = request.getParameter("programmeEtude"),
                pseudonyme = request.getParameter("pseudonyme");
   */           
            try {
                Class.forName(Config.DRIVER);
                Connexion.setUrl(Config.URL);
                Connexion.setUser(Config.DB_USER);
                Connexion.setPassword(Config.DB_PWD);
                Connection cnx = Connexion.getInstance();

                CompteDAO dao = new CompteDAO(cnx);
                Compte compte = dao.read(idCompte);
                if(compte == null)
                    request.setAttribute("vue", "gestionListeComptes.jsp");
                else{
           //         compte.setRole(role);
            //        compte.setCourriel(courriel);
                    compte.setPrenom(prenom);
                    compte.setNom(nom);
                    System.out.println("\n_______________________________________________________________________");
                    System.out.println("\n"+prenom);
                    System.out.println(""+compte.getPrenom());
                    System.out.println("\n"+nom);
                    System.out.println(""+compte.getNom());
                   // compte.setMotPasse(motPasse);
                   // compte.setPseudonyme(pseudonyme);
                   // compte.setProgrammeEtude(programmeEtude);
                    if(!dao.update(compte)){
        //                request.setAttribute("vue", "gestionConfigurationCompte.jsp"); //mettre un message d'erreur
                        request.setAttribute("vue", "accueil.jsp");
                    }
                    else{
                    //il faut avertir que les changements ont étés faits
                        request.setAttribute("vue", "gestionListeComptes.jsp");
        //                request.setAttribute("vue", "gestionConfigurationCompte.jsp");
                    }
                }            
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(EffectuerModificationCompteAction.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("vue", "gestionConfigurationCompte.jsp");
            }
        }
        else
            request.setAttribute("vue", "gestionListeComptes.jsp");
        
        return("/index.jsp");
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    
}
