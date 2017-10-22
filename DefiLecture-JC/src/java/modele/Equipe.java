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
    
    int idEquipe, //clé primaire
        idCapitaine,
        point;
    
    String nom;
    

    //Constructeurs

    public Equipe() {
    }

    public Equipe(int idEquipe, String nom) {
        this.idEquipe = idEquipe;
        this.nom = nom;
    }

    public Equipe(int idEquipe, int idCapitaine, String nom) {
        this.idEquipe = idEquipe;
        this.idCapitaine = idCapitaine;
        this.nom = nom;
        this.point = 0;
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

    public int getIdCapitaine() {
        return idCapitaine;
    }

    public void setIdCapitaine(int idCapitaine) {
        this.idCapitaine = idCapitaine;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
     
    
}
