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
                
                case "afficherPageEquipe":
                    return new AfficherPageEquipeAction();
                case "afficherPageInscription":
                    return new AfficherPageInscriptionAction(); 
                case "afficherPageConnexion":
                    return new AfficherPageConnexionAction();    
                case "effectuerConnexion":
                    return new EffectuerConnexionAction();
                case "afficherPageAccueil":
                    return new AfficherPageAccueilAction();
                    /*
                     
                case "effectuerCreationLecture":
                    return new EffectuerCreationLecture();                
                case "effectuerDeconnexion":
                    return new EffectuerDeconnexion();
                case "effectuerInscription":
                    return new EffectuerInscription();  
                */
            }
        return new AfficherPageAccueilAction();
    }
}
