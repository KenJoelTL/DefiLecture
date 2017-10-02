/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;

/**
 *
 * @author Joel
 */
public class Participant {
    String nom_utilisateur;         //clé primaire
    
    String pseudonyme;              //l'utilisateur peut aussi s'inscrire avec un pseudo unique
    String nom;
    String prenom;
    String courriel;
    String programme;               //programme scolaire
    Equipe equipe;                  //équipe dont le participant fait partie
    String avatar;                  //Pour l'instant l'avatar sera représenté par le chemin vers l'image
    int role;                       //Utilisateur==0 | Participant==1 | Capitaine==2 | Animateur==3 | Administrateur==4
    //ArrayList<Lecture> lectures;    //liste des lectures faites // ==> accessible avec clé secondaire 
    int pointage;                   //Sommes des points gagné par les lectures
    int minutes_restantes;
    
    public Participant(){
    
    }


    public Participant(String nom_utilisateur, String pseudonyme, String nom, String prenom, String courriel, String programme, Equipe equipe, String avatar, int role, ArrayList<Lecture> lectures, int pointage, int minutes_restantes) {
        this.nom_utilisateur = nom_utilisateur;
        this.pseudonyme = pseudonyme;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.programme = programme;
        this.equipe = equipe;
        this.avatar = avatar;
        this.role = role;
        //this.lectures = lectures;
        this.pointage = pointage;
        this.minutes_restantes = minutes_restantes;
    }
    
     
    
    

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
/*
    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }
*/
    public int getPointage() {
        return pointage;
    }

    public void setPointage(int pointage) {
        this.pointage = pointage;
    }

    public int getMinutes_restantes() {
        return minutes_restantes;
    }

    public void setMinutes_restantes(int minutes_restantes) {
        this.minutes_restantes = minutes_restantes;
    }
    
    
       
    
}
