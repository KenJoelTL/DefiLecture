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
public class Trieur {
    
    /**
     *
     * @param order
     * @return
     */
    public String OrderBy(String order){
        switch (order) {
            case "desc":
                order = " DESC";
                break;
            case "asc":
                order = " ASC";
                break;
            case "nom":
                order = " ORDER BY NAME";
                break;
            case "role":
                order = " ORDER BY ROLE";
                break;
            case "courriel":
                order = " ORDER BY COURRIEL";
                break;
            default:
                order = "";
        }
        
        return order;
    }
    
    public String OrderBy(){
        return OrderBy("");
    }
}
