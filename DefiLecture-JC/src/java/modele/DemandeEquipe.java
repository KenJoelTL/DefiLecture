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
        statutDemande,
        point;

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
