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
    String nom_utilisateur;     //clé primaire
    
    String pseudonyme;
    String nom;
    String prenom;
    String courriel;
    String programme;
    //Equipe equipe;
    //String avatar;            //Pour l'instant l'avatar sera représenté par le chemin vers l'image
    int role;                   //utilisateur
    //Lecture lectures;
    int pointage;
    int minutes_restantes;
    
    Participant(){
    
    }
    
    Participant(String nom_utilisateur){
        this.nom_utilisateur = nom_utilisateur;
    }
    
}
