/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;

/**
 *
 * @author Charles
 */
public class Equipe {
    
    String nom; // Clé primaire
    ArrayList<Participant> membres; //Liste des participants qui font partis de l'équipe

    //Constructeurs
    public Equipe(String nom) {
        this.nom = nom;
    }

    public Equipe(String nom, ArrayList<Participant> membres) {
        this.nom = nom;
        this.membres = membres;
    }
    
    //Getters and Setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public ArrayList<Participant> getMembres() {
        return membres;
    }

    public void setMembres(ArrayList<Participant> membres) {
        this.membres = membres;
    }
    
     
    
}
