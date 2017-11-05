
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;


/**
 *
 * @authors Charles & Joel
 */
public class ActionBuilder {
    public static Action getAction(String actionName) {
        System.out.print("entrer dans l'action builder");
        if (actionName != null)
            switch (actionName) {
                
                case "afficherPageAccueil":
                    return new AfficherPageAccueilAction();
                case "afficherPageProfil":
                    return new AfficherPageProfilAction();
       //Inscription
                case "afficherPageInscription":
                    return new AfficherPageInscriptionAction(); 
                case "effectuerInscription":
                    return new EffectuerInscriptionAction();  
       //Connexion
                case "afficherPageConnexion":
                    return new AfficherPageConnexionAction();    
                case "effectuerConnexion":
                    return new EffectuerConnexionAction();
                case "effectuerDeconnexion":
                    return new EffectuerDeconnexionAction();
       //Lecture             
                case "afficherPageCreationLecture":
                    return new AfficherPageCreationLectureAction();
                case "afficherPageGestionLecture":
                    return new AfficherPageGestionLectureAction();
                case "effectuerCreationLecture":
                    return new EffectuerCreationLectureAction();
                case "afficherPageModificationLecture":
                    return new AfficherPageModificationLectureAction();
                case "effectuerModificationLecture":
                    return new EffectuerModificationLectureAction();
       //Defi
                case "afficherPageCreationDefi":
                    return new AfficherPageCreationDefiAction();
                case "effectuerCreationDefi":
                    return new EffectuerCreationDefiAction();
                case "afficherPageParticipationDefi":
                    return new AfficherPageParticipationDefiAction();
                case "afficherPageInscriptionDefi":
                    return new AfficherPageInscriptionDefiAction();
                case "effectuerInscriptionDefi":
                    return new EffectuerInscriptionDefiAction();
                case "afficherPageModificationDefi":
                    return new AfficherPageModificationDefiAction();
                case "effectuerModificationDefi":
                    return new EffectuerModificationDefiAction();
       //Compte        
                case "afficherPageGestionListeCompte":
                    return new AfficherPageGestionListeComptesAction();
                case "afficherPageGestionConfigurationCompte":
                    return new AfficherPageGestionConfigurationCompteAction();
                case "effectuerModificationCompte":
                    return new EffectuerModificationCompteAction();
       //Equipe
                case "afficherPageEquipe":
                    return new AfficherPageEquipeAction();
                case "afficherPageCreationEquipe":
                    return new AfficherPageCreationEquipeAction();
                case "effectuerCreationEquipe":
                    return new EffectuerCreationEquipeAction();
                case "afficherPageTableauScores":
                    return new AfficherPageTableauScoresAction();
                case "afficherPageListeEquipes":
                    return new AfficherPageListeEquipesAction();
                case "afficherPageModificationEquipe":
                    return new AfficherPageModificationEquipeAction();
                case "effectuerModificationEquipe":
                    return new EffectuerModificationEquipeAction();
               
                case "effectuerDepartEquipe":
                    return new EffectuerQuitterEquipeAction();
                case "effectuerSuspensionMembreEquipe":
                    return new EffectuerSuspensionMembreEquipeAction();
                case "effectuerReaffectationMembreEquipe":
                    return new EffectuerReaffectationMembreEquipeAction();
      //DemandeEquipe          
                case "effectuerDemandeAdhesionEquipe":
                    return new EffectuerDemandeAdhesionEquipeAction();
                case "afficherPageListeDemandesEquipe":
                    return new AfficherPageListeDemandesEquipeAction();
                case "effectuerAcceptationDemandeAdhesion":
                    return new EffectuerAcceptationDemandeAdhesionAction();
                case "effectuerSuppressionDemandeAdhesion":
                    return new EffectuerSuppressionDemandeAdhesionAction();
      //Test Ajax
                case "testAjax":
                    return new ExempleClasseActionAjax();
                case "ajouterImage":
                    return new AjouterImageAction();
                    
                default:
                    return new DefaultAction();
                
            }
        
        return new AfficherPageAccueilAction();
    }
}
