/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.modele;

/**
 *
 * @author Joel
 */
public class InscriptionDefi {
    private int idInscriptionDefi,
                idCompte,
                idDefi,
                valeurMinute,
                estReussi;
    private String dateInscription;

    public int getIdInscriptionDefi() {
        return idInscriptionDefi;
    }

    public void setIdInscriptionDefi(int idInscriptionDefi) {
        this.idInscriptionDefi = idInscriptionDefi;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getIdDefi() {
        return idDefi;
    }

    public void setIdDefi(int idDefi) {
        this.idDefi = idDefi;
    }

    public int getValeurMinute() {
        return valeurMinute;
    }

    public void setValeurMinute(int valeurMinute) {
        this.valeurMinute = valeurMinute;
    } 

    public int getEstReussi() {
        return estReussi;
    }

    public void setEstReussi(int estReussi) {
        this.estReussi = estReussi;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    
}
