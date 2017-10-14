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
    
    String dateInscription; //clé primaire double
    int idParticipant; // clé primaire double pas de stockage d'objet dans la base de données
    String titre;
    int dureeMinutes;
    boolean obligatoire; // variable qui indique si la lecture faite était obligtoire ou non
    
    //Constructeur
    public Lecture(String dateInscription, int idParticipant, String titre, int dureeMinutes, boolean obligatoire) {
        this.dateInscription = dateInscription;

        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.obligatoire = obligatoire;
    }

    public String getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(String dateInscription) {
        this.dateInscription = dateInscription;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
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

    public boolean estObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(boolean obligatoire) {
        this.obligatoire = obligatoire;
    }
    
    
    
    
    
    
}
