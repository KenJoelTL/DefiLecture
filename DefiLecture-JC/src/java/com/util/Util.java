/**
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.UTF_16;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class Util {

    /**
     * Cette méthode retourne un String
     * qui représente la chaîne encodée en UTF-8
     *
     * @param someString La chaîne à encoder en UTF-8
     * @return String La chaîne encodée en UTF-8
     */
    public static String toUTF8(String someString){
        byte[] ptext = someString.getBytes(ISO_8859_1); //forme un tableau de bytes selon le format latin [format intermédiaire ± arbitraire]
        String value = new String(ptext, UTF_8);        //forme un String selon le format UTF-8 à partir du tableau de bytes récupéré.

        return value;
    }
    /**
     * Cette méthode retourne un String
     * qui représente la chaîne encodée en UTF-16
     *
     * @param someString La chaîne à encoder en UTF-16
     * @return String La chaîne encodée en UTF-16
     * @throws java.io.UnsupportedEncodingException
     */
    public static String toUTF16(String someString) throws UnsupportedEncodingException{
//        byte[] ptext = someString.getBytes(ISO_8859_1); //forme un tableau de bytes selon le format latin [format intermédiaire ± arbitraire]
        String value = new String(someString.getBytes("UTF-8"), "UTF-16");        //forme un String selon le format UTF-16 à partir du tableau de bytes récupéré.

        return value;
    }

    /**
     * Retourne un String hashé et salé du mot de passe
     * donné en paramètre.
     *
     * @param p_mdp Le mot de passe 
     * @return String Mot de passe hashé en SHA256 et salé
     */

    public static String hasherEtSaler(String p_mdp) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] sel = new byte[16];
            random.nextBytes(sel);
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(sel);
            byte[] mdpHashe = md.digest(p_mdp.getBytes(StandardCharsets.UTF_8));
            
            return new String(mdpHashe);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }
}
