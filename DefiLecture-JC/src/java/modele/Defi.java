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
public class Defi {
    
    private int idDefi,
                idCompte,
                point;
    private String nom,
                   description,
                   dateDebut,
                   dateFin,
                   question,
                   choixReponse,
                   reponse;
    public Defi(){
    
    }

    public int getIdDefi() {
        return idDefi;
    }

    public void setIdDefi(int idDefi) {
        this.idDefi = idDefi;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoixReponse() {
        return choixReponse;
    }

    public void setChoixReponse(String choixReponse) {
        this.choixReponse = choixReponse;
    }
    
    

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    

}
