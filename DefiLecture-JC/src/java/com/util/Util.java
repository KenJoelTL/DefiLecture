package com.util;

import java.io.UnsupportedEncodingException;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.UTF_16;

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

}
