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

public class Defi {

  private int idDefi, idCompte, valeurMinute;
  private String nom, description, dateDebut, dateFin, question, choixReponse, reponse;

  public int getIdDefi() {
    return idDefi;
  }

  public void setIdDefi(int idDefi) {
    this.idDefi = idDefi;
  }

  public int getIdCompte() {
    return idCompte;
  }

  public void setIdCompte(int idCompte) {
    this.idCompte = idCompte;
  }

  public int getValeurMinute() {
    return valeurMinute;
  }

  public void setValeurMinute(int valeurMinute) {
    this.valeurMinute = valeurMinute;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDateDebut() {
    return dateDebut;
  }

  public void setDateDebut(String dateDebut) {
    this.dateDebut = dateDebut;
  }

  public String getDateFin() {
    return dateFin;
  }

  public void setDateFin(String dateFin) {
    this.dateFin = dateFin;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getChoixReponse() {
    return choixReponse;
  }

  public void setChoixReponse(String choixReponse) {
    this.choixReponse = choixReponse;
  }

  public String getReponse() {
    return reponse;
  }

  public void setReponse(String reponse) {
    this.reponse = reponse;
  }
}
