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
    
    
    Date date_inscription; //clé primaire double
    Participant participant; // clé primaire double
    
    String titre;
    int duree_minutes;
    boolean obligatoire; // variable qui indique si la lecture faite était obligtoire ou non
    
    //Constructeur
    public Lecture(Date date_inscription, Participant participant, String titre, int duree_minutes, boolean obligatoire) {
        this.date_inscription = date_inscription;
        this.participant = participant;
        this.titre = titre;
        this.duree_minutes = duree_minutes;
        this.obligatoire = obligatoire;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
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
