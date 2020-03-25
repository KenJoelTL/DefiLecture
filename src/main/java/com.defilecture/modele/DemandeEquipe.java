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

public class DemandeEquipe {

  public static final int EN_ATTENTE = -1, // -1 : Le demande est visible, elle vient d'être créée
      SUSPENDUE = 0, // 0 : Le participant est suspendu de l'équipe.
      ACCEPTEE = 1; // 1 : Le participant fait présentement partie de l'équipe
  private int idDemandeEquipe, idCompte, idEquipe, point, statutDemande = EN_ATTENTE;

  public int getIdDemandeEquipe() {
    return idDemandeEquipe;
  }

  public void setIdDemandeEquipe(int idDemandeEquipe) {
    this.idDemandeEquipe = idDemandeEquipe;
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

  public int getStatutDemande() {
    return statutDemande;
  }

  public void setStatutDemande(int statutDemande) {
    this.statutDemande = statutDemande;
  }

  public int getPoint() {
    return point;
  }

  public void setPoint(int point) {
    this.point = point;
  }
}
