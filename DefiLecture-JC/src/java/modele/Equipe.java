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
public class Equipe {
    public static int NB_MAX_MEMBRES = 3;
    int idEquipe, //clé primaire
        point,
        nbMembres;
    
    String nom;
    

    //Constructeurs

    public Equipe() {
    }

    public Equipe(int idEquipe, String nom) {
        this.idEquipe = idEquipe;
        this.nom = nom;
        this.nbMembres = 0;
        this.point = 0;
    }
    
    // Getters et Setters
    public int getIdEquipe() {
        return idEquipe;
    }
    
    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getNbMembres() {
        return nbMembres;
    }

    public void setNbMembres(int nbMembres) {
        this.nbMembres = nbMembres;
    }
    
     
    
}
