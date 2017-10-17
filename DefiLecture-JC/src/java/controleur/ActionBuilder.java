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
        if (actionName != null)
            switch (actionName) {
                case "afficherPageConnexion":
                    return new AfficherPageConnexion();
                case "afficherPageEquipe":
                    return new AfficherPageEquipe();
                case "afficherPageInscription":
                    return new AfficherPageInscription();        
                case "effectuerConnexion":
                    return new EffectuerConnexion();                
                case "effectuerCreationLecture":
                    return new EffectuerCreationLecture();                
                case "effectuerDeconnexion":
                    return new EffectuerDeconnexion();
                case "effectuerInscription":
                    return new EffectuerInscription();                
            }
        return new DefaultAction();
    }
}
