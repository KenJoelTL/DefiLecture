/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Date;

/**
 *
 * @author Charles
 */
public class Lecture {
    
    int idLecture; // clé primaire
    int idParticipant;
    Date date_inscription;
    String titre;
    int duree_minutes;
    boolean obligatoire; // indique si la lecture est obligtoire ou non
    boolean defi; // indique si la lecture fait partie d'un défi
    
    //Constructeur
    
    public Lecture(int idLecture, int idParticipant, Date date_inscription, String titre, int duree_minutes, boolean obligatoire, boolean defi) {
        this.idLecture = idLecture;
        this.idParticipant = idParticipant;
        this.date_inscription = date_inscription;
        this.titre = titre;
        this.duree_minutes = duree_minutes;
        this.obligatoire = obligatoire;
        this.defi = defi;
    }


    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
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

    public boolean estDefi() {
        return defi;
    }

    public void setDefi(boolean defi) {
        this.defi = defi;
    }


   

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getDuree_minutes() {
        return duree_minutes;
    }

    public void setDuree_minutes(int duree_minutes) {
        this.duree_minutes = duree_minutes;
    }

    public boolean estObligatoire() {
        return obligatoire;
    }

    public void setObligatoire(boolean obligatoire) {
        this.obligatoire = obligatoire;
    }
    
    
    
    
    
    
}
