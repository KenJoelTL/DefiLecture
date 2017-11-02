/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.Config;
import jdbc.Connexion;
import modele.Compte;
import modele.CompteDAO;
import modele.DemandeEquipe;
import modele.DemandeEquipeDAO;
import modele.Lecture;
import modele.LectureDAO;

/**
 *
 * @author Charles
 */
public class EffectuerCreationLectureAction implements Action, RequestAware, SessionAware, RequirePRGAction {
    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    @Override
    public String execute() {
        
        System.out.println("Entrer dans l'action créer lecture");
        
        if(session.getAttribute("connecte") != null
        && session.getAttribute("role") != null
        && ( ((int)session.getAttribute("role") == Compte.PARTICIPANT)
            || ((int)session.getAttribute("role") == Compte.CAPITAINE) )
        && request.getParameter("titre")!=null
        && request.getParameter("dureeMinutes")!=null
        && request.getParameter("obligatoire")!=null){
     
            String  titre = request.getParameter("titre");                
            int     dureeMinutes = Integer.parseInt(request.getParameter("dureeMinutes")),
                    obligatoire = Integer.parseInt(request.getParameter("obligatoire")),
                    idCompte = (int)session.getAttribute("connecte");

            Lecture lecture;
               
        try{

            Connexion.reinit();
            Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
            LectureDAO dao;
            dao = new LectureDAO(cnx);
            lecture = new Lecture();
            lecture.setIdCompte(idCompte);
            lecture.setDureeMinutes(dureeMinutes);
            lecture.setTitre(titre);
            lecture.setEstObligatoire(obligatoire);
             if(dao.create(lecture)){
                
                //Mise à jour des points du participant
                //Conversion du nombre de minutes de la lecture en points pour le Participant : 15mins = 1 point
                CompteDAO daoCompte = new CompteDAO(cnx);
                Compte compte = new Compte();
                compte = daoCompte.read(idCompte);
                if(lecture.getEstObligatoire() == Lecture.NON_OBLIGATOIRE)
                    dureeMinutes*=2;
                int pointLecture = (dureeMinutes + compte.getMinutesRestantes()) / 15;
                int pointCompte = compte.getPoint() + pointLecture;
                //Les minutes restantes sont gardées en mémoire ici
                int minutesRestantes = (dureeMinutes + compte.getMinutesRestantes()) % 15;
                compte.setPoint(pointCompte);
                compte.setMinutesRestantes(minutesRestantes);
                daoCompte.update(compte);
                //Mise à jour des points dans demande_equipe (pour calculer le total des points de l'équipe)
                if(compte.getIdEquipe() > 0){
                    DemandeEquipeDAO demandeDAO = new DemandeEquipeDAO(cnx);
                    DemandeEquipe demande = new DemandeEquipe();
                    demande = demandeDAO.findByIdCompteEquipe(idCompte, compte.getIdEquipe());
                    int pointDemandeEquipe = demande.getPoint() + pointLecture;
                    demande.setPoint(pointDemandeEquipe);
                    demandeDAO.update(demande);

                    System.out.println("Une lecture a été créée avec succès");

                }

                else
                    System.out.println("Problème de création de la lecture");
              }
            }
            
            catch(ClassNotFoundException e){
                System.out.println("Erreur dans le chargement du pilote :"+ e);
                //request.setAttribute("vue", "lecture.jsp");
                return "*.do?tache=afficherPageGestionLecture";
            }
            

        }
        return "*.do?tache=afficherPageGestionLecture";
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
