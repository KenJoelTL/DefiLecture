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
public class Equipe {
    
    int idEquipe; //clé primaire
    String nom;
    

    //Constructeurs

    public Equipe(int idEquipe, String nom) {
        this.idEquipe = idEquipe;
        this.nom = nom;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    // Getters et Setters
    
    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
     
    
}
