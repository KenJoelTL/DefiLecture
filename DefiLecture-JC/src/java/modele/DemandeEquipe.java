/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Joel
 */
public class DemandeEquipe {
    int idDemandeEquipe,
        idCompte,
        idEquipe,
        point,
        statutDemande = -1; /* -1 : Le demande est visible, elle vient d'être (re)créée 
                                0 : Le participant a contribué, mais a quitté l'equipe
                                1 : Le participant fait présentement partie de l'équipe */

    public int getIdDemandeEquipe() {
        return idDemandeEquipe;
    }

    public void setIdDemandeEquipe(int idDemandeEquipe) {
        this.idDemandeEquipe = idDemandeEquipe;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(int statutDemande) {
        this.statutDemande = statutDemande;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
    
}
