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
package com.defilecture;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Classe d'utilitaires spécifiques au projet. */
public class Util {
  /**
   * Cette méthode retourne un String qui représente la chaîne encodée en UTF-8.
   *
   * @param someString La chaîne à encoder en UTF-8
   * @return String La chaîne encodée en UTF-8; null si someString est null.
   */
  public static String toUTF8(String someString) {
    if (someString == null) {
      return null;
    }

    // forme un tableau de bytes selon le format latin [format intermédiaire ±arbitraire]
    byte[] ptext = someString.getBytes(ISO_8859_1);
    // forme un String selon le format UTF-8 à partir du tableau de bytes récupéré.
    String value = new String(ptext, UTF_8);

    return value;
  }

  /**
   * Cette méthode retourne un String qui représente la chaîne encodée en UTF-16.
   *
   * @param someString La chaîne à encoder en UTF-16
   * @return String La chaîne encodée en UTF-16
   */
  public static String toUTF16(String someString) {
    //        byte[] ptext = someString.getBytes(ISO_8859_1); //forme un tableau de bytes selon le
    // format latin [format intermédiaire ± arbitraire]
    String value = null;
    try {
      // forme un String selon le format UTF-16 à partir du tableau de bytes récupérés.

      value = new String(someString.getBytes("UTF-8"), "UTF-16");
    } catch (UnsupportedEncodingException e) {
      // impossible, l'encodage est codé en dur.
    }

    return value;
  }

  /**
   * Retourne un String hashé en SHA256 et salé du mot de passe donné en paramètre.
   *
   * @param mdp : Le mot de passe à hasher avec sel
   * @param sel : Un sel qui a déjà été généré
   * @return String : Mot de passe hashé en SHA256 et salé
   */
  public static String hasherAvecSel(String mdp, String sel) {
    return genererSHA256(mdp + sel);
  }

  /**
   * Retourne un hash SHA256 de la chaîne de caractères fournie en paramètre.
   *
   * @param chaine : La chaîne à hasher
   * @return String : Chaîne hashée en SHA256
   */
  public static String genererSHA256(String chaine) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(chaine.getBytes("UTF-8"));
      return octetsVersHex(hash);
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
      Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
      throw new RuntimeException("Problème d'encodage non supporté ou d'algorithme inconnu.");
    }
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
