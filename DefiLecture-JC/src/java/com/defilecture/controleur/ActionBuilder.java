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

public class ActionBuilder {
  public static Action getAction(String actionName) {
    if (actionName != null) {
      switch (actionName) {
        case "afficherPageAccueil":
          return new AfficherPageMarcheASuivreAction();
        case "afficherPageProfil":
          return new AfficherPageProfilAction();
        case "afficherPageCodeConduite":
          return new AfficherPageCodeConduiteAction();
        case "afficherPageGlossaire":
          return new AfficherPageGlossaireAction();
        case "afficherPageContributeurs":
          return new AfficherPageContributeursAction();
        case "afficherPageMarcheASuivre":
          return new AfficherPageMarcheASuivreAction();

          // Inscription
        case "afficherPageInscription":
          return new AfficherPageInscriptionAction();
        case "effectuerInscription":
          return new EffectuerInscriptionAction();

          // Connexion
        case "afficherPageConnexion":
          return new AfficherPageConnexionAction();
        case "effectuerConnexion":
          return new EffectuerConnexionAction();
        case "effectuerDeconnexion":
          return new EffectuerDeconnexionAction();

          // Lecture
        case "afficherPageCreationLecture":
          return new AfficherPageCreationLectureAction();
        case "afficherPageGestionLecture":
          return new AfficherPageGestionLectureAction();
        case "effectuerCreationLecture":
          return new EffectuerCreationLectureAction();
        case "afficherPageModificationLecture":
          return new AfficherPageModificationLectureAction();
        case "afficherPageListeLectures":
          return new AfficherPageListeLecturesAction();
        case "effectuerModificationLecture":
          return new EffectuerModificationLectureAction();
        case "effectuerSuppressionLecture":
          return new EffectuerSuppressionLectureAction();

          // Defi
        case "afficherPageCreationDefi":
          return new AfficherPageCreationDefiAction();
        case "effectuerCreationDefi":
          return new EffectuerCreationDefiAction();
        case "afficherPageParticipationDefi":
          return new AfficherPageParticipationDefiAction();
        case "afficherPageInscriptionDefi":
          return new AfficherPageInscriptionDefiAction();
        case "afficherPageDefisReussis":
          return new AfficherPageDefisReussisAction();
        case "effectuerInscriptionDefi":
          return new EffectuerInscriptionDefiAction();
        case "afficherPageModificationDefi":
          return new AfficherPageModificationDefiAction();
        case "effectuerModificationDefi":
          return new EffectuerModificationDefiAction();
        case "supprimerDefi":
          return new SupprimerDefiAction();

          // Compte
        case "afficherPageGestionListeCompte":
          return new AfficherPageGestionListeComptesAction();
        case "afficherPageAdresseCourriel":
          return new AfficherPageAdresseCourrielAction();
        case "afficherPageModificationCompte":
          return new AfficherPageModificationCompteAction();
        case "effectuerModificationCompte":
          return new EffectuerModificationCompteAction();
        case "effectuerAjoutAvatarCompte":
          return new EffectuerAjoutAvatarCompteAction();
        case "effectuerSuppressionCompte":
          return new EffectuerSuppressionCompteAction();

          // Equipe
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

          // DemandeEquipe
        case "effectuerDemandeAdhesionEquipe":
          return new EffectuerDemandeAdhesionEquipeAction();
        case "afficherPageListeDemandesEquipe":
          return new AfficherPageListeDemandesEquipeAction();
        case "effectuerAcceptationDemandeAdhesion":
          return new EffectuerAcceptationDemandeAdhesionAction();
        case "effectuerSuppressionDemandeAdhesion":
          return new EffectuerSuppressionDemandeAdhesionAction();
        case "effectuerDepartEquipe":
          return new EffectuerDepartEquipeAction();
        case "effectuerSuspensionMembreEquipe":
          return new EffectuerSuspensionMembreEquipeAction();
        case "effectuerReaffectationMembreEquipe":
          return new EffectuerReaffectationMembreEquipeAction();

          // Administation
        case "afficherPageConfiguration":
          return new AfficherPageConfigAction();
        case "effectuerModificationConfig":
          return new EffectuerModificationConfigAction();
        case "effectuerSupressionBD":
          return new EffectuerSuppressionBDAction();
        case "afficherPageGestionListeEquipes":
          return new AfficherPageGestionListeEquipesAction();
        case "effectuerAjoutMembreEquipe":
          return new EffectuerAjoutMembreEquipeAction();
        case "effectuerSuppressionEquipe":
          return new EffectuerSuppressionEquipeAction();

          // Default Action
        default:
          return new DefaultAction();
      }
    }
    return new AfficherPageMarcheASuivreAction();
  }
}
