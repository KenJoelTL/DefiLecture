/**
 * This file is part of DefiLecture.
 *
 * <p>DefiLecture is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * <p>DefiLecture is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * <p>You should have received a copy of the GNU General Public License along with DefiLecture. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package com.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.UTF_16;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Base64;
import java.io.UnsupportedEncodingException;

/** 
 *  @author Joel
 *  @author Alexandre Dupré
 */
public class Util {

    /**
     * Retourne un String hashé en SHA256 et salé du mot de passe
     * donné en paramètre.
     *
     * @param p_mdp : Le mot de passe à hasher avec sel
     * @param p_sel : Un sel qui a déjà été généré 
     * @return String : Mot de passe hashé en SHA256 et salé
     */
    public static String hasherAvecSel(String p_mdp, String p_sel) {
         return genererSHA256(p_mdp + p_sel);  
    }
    
    /**
     * Retourne un hash SHA256 de la chaîne de 
     * caractères fournie en paramètre.
     *
     * @param p_chaine : La chaîne à hasher
     * @return String : Chaîne hashée en SHA256
     */
    public static String genererSHA256(String p_chaine) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(p_chaine.getBytes("UTF-8"));
            return octetsVersHex(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Une valeur nulle est retournée si on sort du try/catch.
    }
    
    /**
     * Retourne un sel généré aléatoirement.
     * 
     * @return String : Sel généré aléatoirement
     */
    public static String genererSel() {
        SecureRandom random = new SecureRandom();
        
        byte[] sel = new byte[15];
        random.nextBytes(sel);
               
        return Base64.getEncoder().encodeToString(sel);
    }
    
    /**
     * Convertit un tableau d'octets en chaîne de caractères.
     * 
     * @param hash : Le hash sous forme de tableau d'octets
     * @return String : Chaîne de caractères du tableau d'octets 
     */
    private static String octetsVersHex(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        
        return sb.toString();
    }
}
