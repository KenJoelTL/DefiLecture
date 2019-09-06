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

import java.sql.Date;

public class Lecture {

  public static final int NON_OBLIGATOIRE = 0;
  public static final int OBLIGATOIRE = 1;

  int idLecture; // clé primaire
  int idCompte;
  Date dateInscription;
  String titre;
  int dureeMinutes;
  int estObligatoire; // indique si la lecture est obligtoire ou non

  // Constructeur

  public Lecture() {}

  public Lecture(
      int idLecture,
      int idCompte,
      Date dateInscription,
      String titre,
      int dureeMinutes,
      int estObligatoire) {
    this.idLecture = idLecture;
    this.idCompte = idCompte;
    this.dateInscription = dateInscription;
    this.titre = titre;
    this.dureeMinutes = dureeMinutes;
    this.estObligatoire = estObligatoire;
  }

  /* public Lecture(int idCompte, String dateInscription, String titre, int dureeMinutes) {
      this.idCompte = idCompte;
      this.dateInscription = dateInscription;
      this.titre = titre;
      this.dureeMinutes = dureeMinutes;
      this.idLecture = 0;
      this.estObligatoire = 0;
  }*/

  // Getters et Setters

  public int getIdLecture() {
    return idLecture;
  }

  public void setIdLecture(int idLecture) {
    this.idLecture = idLecture;
  }

  public int getIdCompte() {
    return idCompte;
  }

  public void setIdCompte(int idCompte) {
    this.idCompte = idCompte;
  }

  public Date getDateInscription() {
    return dateInscription;
  }

  public void setDateInscription(Date dateInscription) {
    this.dateInscription = dateInscription;
  }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public int getDureeMinutes() {
    return dureeMinutes;
  }

  public void setDureeMinutes(int dureeMinutes) {
    this.dureeMinutes = dureeMinutes;
  }

  public int getEstObligatoire() {
    return estObligatoire;
  }

  public void setEstObligatoire(int estObligatoire) {
    this.estObligatoire = estObligatoire;
  }
}
