/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *
 * @author usager
 */
public class Util {
    
    /**
     * Cette méthode retourne un String 
     * qui représente la chaîne encodée en UTF-8
     * 
     * @param string La chaîne à encoder en UTF-8 
     */
    public static String toUTF8(String string){
        byte[] ptext = string.getBytes(ISO_8859_1); 
        String value = new String(ptext, UTF_8); 
        
        return value;
    }
    
}
