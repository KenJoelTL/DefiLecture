/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;


/**
 *
 * @author Charles
 */
public class ActionBuilder {
    public static Action getAction(String actionName) {
        System.out.print("entrer dans l'action builder");
        if (actionName != null)
            switch (actionName) {
                
                case "afficherPageAccueil":
                    return new AfficherPageAccueilAction();
                case "afficherPageEquipe":
                    return new AfficherPageEquipeAction();
                case "afficherPageProfil":
                    return new AfficherPageProfilAction();
                case "afficherPageInscription":
                    return new AfficherPageInscriptionAction(); 
                case "effectuerInscription":
                    return new EffectuerInscriptionAction();  
                case "afficherPageConnexion":
                    return new AfficherPageConnexionAction();    
                case "effectuerConnexion":
                    return new EffectuerConnexionAction();
                case "effectuerDeconnexion":
                    return new EffectuerDeconnexionAction();
                case "afficherCreationLecture":
                    return new AfficherCreationLectureAction();
                case "effectuerCreationLecture":
                    return new EffectuerCreationLectureAction();
                case "afficherCreationDefi":
                    return new AfficherCreationDefiAction();
                case "effectuerCreationDefi":
                    return new EffectuerCreationDefiAction();
                case "afficherPageGestionListeCompte":
                    return new AfficherPageGestionListeComptesAction();

                    /*
                     
                case "effectuerCreationLecture":
                    return new EffectuerCreationLectureAction();                
                */
            }
        return new AfficherPageAccueilAction();
    }
}
