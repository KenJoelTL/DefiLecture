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
package com.defilecture.modele;

public class InscriptionDefi {
  private int idInscriptionDefi, idCompte, idDefi, valeurMinute, estReussi;
  private String dateInscription;

  public int getIdInscriptionDefi() {
    return idInscriptionDefi;
  }

  public void setIdInscriptionDefi(int idInscriptionDefi) {
    this.idInscriptionDefi = idInscriptionDefi;
  }

  public int getIdCompte() {
    return idCompte;
  }

  public void setIdCompte(int idCompte) {
    this.idCompte = idCompte;
  }

  public int getIdDefi() {
    return idDefi;
  }

  public void setIdDefi(int idDefi) {
    this.idDefi = idDefi;
  }

  public int getValeurMinute() {
    return valeurMinute;
  }

  public void setValeurMinute(int valeurMinute) {
    this.valeurMinute = valeurMinute;
  }

  public int getEstReussi() {
    return estReussi;
  }

  public void setEstReussi(int estReussi) {
    this.estReussi = estReussi;
  }

  public String getDateInscription() {
    return dateInscription;
  }

  public void setDateInscription(String dateInscription) {
    this.dateInscription = dateInscription;
  }
}
