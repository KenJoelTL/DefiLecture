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

import com.defilecture.Util;

public class Compte {
  public static int PARTICIPANT = 1;
  public static int CAPITAINE = 2;
  public static int MODERATEUR = 3;
  public static int ADMINISTRATEUR = 4;
  public static String AVATAR_DEFAUT = "/images/avatars/avatarCompte_defaut.png";

  private int idCompte, idEquipe = -1;
  private int role = Compte.PARTICIPANT, point, minutesRestantes, devenirCapitaine;
  private String pseudonyme,
      nom,
      prenom,
      courriel,
      motPasse,
      programmeEtude,
      avatar = AVATAR_DEFAUT;

  String sel;

  public Compte() {}

  public Compte(
      int idCompte,
      int idEquipe,
      String pseudonyme,
      String motPasse,
      String sel,
      String nom,
      String prenom,
      String courriel,
      String programme,
      String avatar,
      int role,
      int pointage,
      int minutesRestantes,
      int devenirCapitaine) {
    this.idCompte = idCompte;
    this.idEquipe = idEquipe;
    this.pseudonyme = pseudonyme;
    this.motPasse = motPasse;
    this.sel = sel;
    this.nom = nom;
    this.prenom = prenom;
    this.courriel = courriel;
    this.programmeEtude = programme;
    this.avatar = avatar;
    this.role = role;
    this.point = pointage;
    this.minutesRestantes = minutesRestantes;
    this.devenirCapitaine = devenirCapitaine;
  }

  public Compte(
      int idCompte,
      int idEquipe,
      String pseudonyme,
      String nom,
      String prenom,
      String courriel,
      String programme,
      String avatar,
      int role,
      int pointage,
      int minutesRestantes) {
    this(
        idCompte,
        idEquipe,
        pseudonyme,
        null,
        null,
        nom,
        prenom,
        courriel,
        programme,
        avatar,
        role,
        pointage,
        minutesRestantes,
        0);
  }

  public Compte(
      int idEquipe,
      String pseudonyme,
      String nom,
      String prenom,
      String courriel,
      String programme,
      String avatar,
      int role,
      int pointage,
      int minutesRestantes) {
    this(
        -1,
        idEquipe,
        pseudonyme,
        nom,
        prenom,
        courriel,
        programme,
        avatar,
        role,
        pointage,
        minutesRestantes);
  }

  public int getIdCompte() {
    return idCompte;
  }

  public void setIdCompte(int idCompte) {
    this.idCompte = idCompte;
  }

  public int getIdEquipe() {
    return idEquipe;
  }

  public void setIdEquipe(int idEquipe) {
    this.idEquipe = idEquipe;
  }

  public int getMinutesRestantes() {
    return minutesRestantes;
  }

  public void setMinutesRestantes(int minutesRestantes) {
    this.minutesRestantes = minutesRestantes;
  }

  public String getPseudonyme() {
    return pseudonyme;
  }

  public void setPseudonyme(String pseudonyme) {
    this.pseudonyme = pseudonyme;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public String getCourriel() {
    return courriel;
  }

  public void setCourriel(String courriel) {
    this.courriel = courriel;
  }

  public String getProgrammeEtude() {
    return programmeEtude;
  }

  public void setProgrammeEtude(String programmeEtude) {
    this.programmeEtude = programmeEtude;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }

  public String getSel() {
    return sel;
  }

  public String getMotPasse() {
    return motPasse;
  }

  public void setMotPasse(String motPasseClair) {
    this.sel = Util.genererSel();
    this.motPasse = Util.hasherAvecSel(motPasseClair, this.sel);
  }

  public int getDevenirCapitaine() {
    return devenirCapitaine;
  }

  public void setDevenirCapitaine(int devenirCapitaine) {
    this.devenirCapitaine = devenirCapitaine;
  }

  public boolean verifierMotPasse(String motPasseClair) {
    return Util.hasherAvecSel(motPasseClair, this.sel).equals(this.motPasse);
  }
}
