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
public class Participant {
    
    int idParticipant;             //clé primaire
    int idEquipe;                  //équipe dont le participant fait partie
    
    String nomUtilisateur;         //ancienne clé primaire
    String pseudonyme;              //l'utilisateur peut aussi s'inscrire avec un pseudo unique
    String nom;
    String prenom;
    String courriel;
    String programme;               //programme scolaire
    String avatar;                  //Pour l'instant l'avatar sera représenté par le chemin vers l'image
    int role;                       //Utilisateur==0 | Participant==1 | Capitaine==2 | Animateur==3 | Administrateur==4
    int pointage;                   //Sommes des points gagné par les lectures
    int minutesRestantes;
    
    public Participant(){
    
    }

    public Participant(int idParticipant, int idEquipe, String nomUtilisateur, String pseudonyme, String nom, String prenom,
                            String courriel, String programme, String avatar, int role, int pointage, int minutesRestantes) {
        this.idParticipant = idParticipant;
        this.idEquipe = idEquipe;
        this.nomUtilisateur = nomUtilisateur;
        this.pseudonyme = pseudonyme;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.programme = programme;
        this.avatar = avatar;
        this.role = role;
        this.pointage = pointage;
        this.minutesRestantes = minutesRestantes;
    }

    public Participant(int idEquipe, String nomUtilisateur, String pseudonyme, String nom, String prenom, String courriel,
                            String programme, String avatar, int role, int pointage, int minutesRestantes) {
        this.idEquipe = idEquipe;
        this.nomUtilisateur = nomUtilisateur;
        this.pseudonyme = pseudonyme;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.programme = programme;
        this.avatar = avatar;
        this.role = role;
        this.pointage = pointage;
        this.minutesRestantes = minutesRestantes;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
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

    public int getPointage() {
        return pointage;
    }

    public void setPointage(int pointage) {
        this.pointage = pointage;
    }

    public int getMinutes_restantes() {
        return minutesRestantes;
    }

    public void setMinutes_restantes(int minutesRestantes) {
        this.minutesRestantes = minutesRestantes;
    }
    
    
       
    
}
