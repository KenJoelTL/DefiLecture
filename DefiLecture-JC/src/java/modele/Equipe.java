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
    
    int idEquipe; //clé primaire
    int idCapitaine;
    int idMembre1;
    int idMembre2;
    String nom;

    public Equipe(int idEquipe, int idCapitaine, int idMembre1, int idMembre2, String nom) {
        this.idEquipe = idEquipe;
        this.idCapitaine = idCapitaine;
        this.idMembre1 = idMembre1;
        this.idMembre2 = idMembre2;
        this.nom = nom;
    }

    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getIdCapitaine() {
        return idCapitaine;
    }

    public void setIdCapitaine(int idCapitaine) {
        this.idCapitaine = idCapitaine;
    }

    public int getIdMembre1() {
        return idMembre1;
    }

    public void setIdMembre1(int idMembre1) {
        this.idMembre1 = idMembre1;
    }

    public int getIdMembre2() {
        return idMembre2;
    }

    public void setIdMembre2(int idMembre2) {
        this.idMembre2 = idMembre2;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    

    
    
     
    
}
