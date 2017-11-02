/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.sql.Date;

/**
 *
 * @author Charles
 */
public class Lecture {
    
    public static int NON_OBLIGATOIRE = 0;
    public static int OBLIGATOIRE = 1;

    int idLecture; // clé primaire
    int idCompte;
    Date dateInscription; 
    String titre;
    int dureeMinutes;
    int estObligatoire; //indique si la lecture est obligtoire ou non

    
    // Constructeur
    
    
    public Lecture() {
    }

    public Lecture(int idLecture, int idCompte, Date dateInscription, String titre, int dureeMinutes, int estObligatoire) {
        this.idLecture = idLecture;
        this.idCompte = idCompte;
        this.dateInscription = dateInscription;
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.estObligatoire = estObligatoire;
    }

   /* public Lecture(int idCompte, String dateInscription, String titre, int dureeMinutes) {
        this.idCompte = idCompte;
        this.dateInscription = dateInscription;
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.idLecture = 0;
        this.estObligatoire = 0;
    }*/
    

    // Getters et Setters
    
    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public int getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public int getEstObligatoire() {
        return estObligatoire;
    }

    public void setEstObligatoire(int estObligatoire) {
        this.estObligatoire = estObligatoire;
    }
    
    
    
}
