/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.modele;

/**
 *
 * @author Joel
 */
public class Equipe implements Comparable<Equipe>{
    public static int NB_MAX_MEMBRES = 3;
    private int idEquipe, //clé primaire
                point,
                nbMembres;
    
    private String nom;
    

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

    @Override
    public int compareTo(Equipe equipe) {
        int valeur = 0;    
        if(this.point > equipe.point)
            valeur = 1;
        else if(this.point < equipe.point)
            valeur = -1;
        return valeur;
    }

    @Override
    public boolean equals(Object obj) {
        if(this != null && obj != null)
            if(obj instanceof Equipe)
                return (this.idEquipe == ((Equipe)obj).idEquipe) 
                        /*|| (this.nom.equals(equipe.nom))*/;
        
        return false;
        
    }
    
     
    
}
