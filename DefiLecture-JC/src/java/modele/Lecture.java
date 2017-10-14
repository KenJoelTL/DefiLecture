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
    int idParticipant;
    String dateInscription; 
    String titre;
    int dureeMinutes;
    boolean obligatoire; //indique si la lecture est obligtoire ou non
    boolean defi; // indique si la lecture fait partie d'un défi

    public Lecture(int idLecture, int idParticipant, String dateInscription, String titre, int dureeMinutes, boolean obligatoire, boolean defi) {
        this.idLecture = idLecture;
        this.idParticipant = idParticipant;
        this.dateInscription = dateInscription;
        this.titre = titre;
        this.dureeMinutes = dureeMinutes;
        this.obligatoire = obligatoire;
        this.defi = defi;
    }

    public int getIdLecture() {
        return idLecture;
    }

    public void setIdLecture(int idLecture) {
        this.idLecture = idLecture;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
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

    public boolean estObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public boolean estDefi() {
        return defi;
    }

    public void setDefi(boolean defi) {
        this.defi = defi;
    }
    
    

    
    
}
