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
        statut;

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

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }
    
    
}
