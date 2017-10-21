/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/**
 *
 * @author Charles
 */
public class Lecture {
    

    int idLecture; // clé primaire
    int idCompte;
    String dateInscription; 
    String titre;
    int dureeMinutes;
    int obligatoire; //indique si la lecture est obligtoire ou non
    int defi; // indique si la lecture fait partie d'un défi

    
    // Constructeur
    
    
    public Lecture() {
    }

    public Lecture(int idLecture, int idCompte, String dateInscription, String titre, int dureeMinutes, int obligatoire, int defi) {
        this.idLecture = idLecture;
        this.idCompte = idCompte;
        this.dateInscription = dateInscription;
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.obligatoire = obligatoire;
        this.defi = defi;
    }

    public Lecture(int idCompte, String dateInscription, String titre, int dureeMinutes) {
        this.idCompte = idCompte;
        this.dateInscription = dateInscription;
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.idLecture = 0;
        this.obligatoire = 0;
        this.defi = 0;
    }
    

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

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
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

    public int estObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(int obligatoire) {
        this.obligatoire = obligatoire;
    }

    public int estDefi() {
        return defi;
    }

    public void setDefi(int defi) {
        this.defi = defi;
    }
    
    

    
    
}
