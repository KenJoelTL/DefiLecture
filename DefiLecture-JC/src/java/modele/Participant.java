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
    
//    String nomUtilisateur;         //ancienne clé primaire
    String pseudonyme;              //l'utilisateur peut aussi s'inscrire avec un pseudo unique
    String nom;
    String prenom;
    String courriel;
    String motPasse;
    String programmeEtude;               //programme scolaire
    String avatar;                  //Pour l'instant l'avatar sera représenté par le chemin vers l'image
    int role;                       //Utilisateur==0 | Participant==1 | Capitaine==2 | Animateur==3 | Administrateur==4
    int pointage;                   //Sommes des points gagné par les lectures
    int minutesRestantes;
    
    public Participant(){
    
    }

    public Participant(int idParticipant, int idEquipe, String pseudonyme, String nom, String prenom,
                            String courriel, String programme, String avatar, int role, int pointage, int minutesRestantes) {
        this.idParticipant = idParticipant;
        this.idEquipe = idEquipe;
        this.pseudonyme = pseudonyme;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.programmeEtude = programme;
        this.avatar = avatar;
        this.role = role;
        this.pointage = pointage;
        this.minutesRestantes = minutesRestantes;
    }

    public Participant(int idEquipe, String pseudonyme, String nom, String prenom, String courriel,
                            String programme, String avatar, int role, int pointage, int minutesRestantes) {
        this.idEquipe = idEquipe;
        this.pseudonyme = pseudonyme;
        this.nom = nom;
        this.prenom = prenom;
        this.courriel = courriel;
        this.programmeEtude = programme;
        this.avatar = avatar;
        this.role = role;
        this.pointage = pointage;
        this.minutesRestantes = minutesRestantes;
    }

    public int getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(int idParticipant) {
        this.idParticipant = idParticipant;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getMinutesRestantes() {
        return minutesRestantes;
    }

    public void setMinutesRestantes(int minutesRestantes) {
        this.minutesRestantes = minutesRestantes;
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

    public String getProgrammeEtude() {
        return programmeEtude;
    }

    public void setProgrammeEtude(String programmeEtude) {
        this.programmeEtude = programmeEtude;
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

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }
       
    
}
