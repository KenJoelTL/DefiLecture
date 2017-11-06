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
    
    public static int EN_ATTENTE = -1, //-1 : Le demande est visible, elle vient d'être créée 
               SUSPENDUE = 0  , // 0 : Le participant est suspendu de l'équipe.
               ACCEPTEE = 1   ; // 1 : Le participant fait présentement partie de l'équipe */
               
    private int idDemandeEquipe,
        idCompte,
        idEquipe,
        point,
        statutDemande = EN_ATTENTE; 

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
