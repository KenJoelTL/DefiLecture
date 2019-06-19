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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defilecture.controleur;

import java.io.IOException;

/**
 * @author Joel
 * @author Mikaël Nadeau
 */
public class ExempleClasseActionAjax extends Action implements SendAjaxResponse {

  @Override
  public String execute() {
    try {
      // Logique ici

      // construire une chaîne json ou bien faire comme on veut
      String json = "[]";
      String commeOnVeut = "['" + request.getParameter("prenom") + "']";

      // finalement envoyer une reponse en spécifiant le format
      response.setContentType("application/json;UTF-8"); // ou n'importe quoi comme string
      // text/plain(conseillé pour du
      // Json) ou bien html/text
      // response.setCharacterEncoding("UTF-8");
      response.getWriter().write(json);
      response.getWriter().write(commeOnVeut);
    } catch (IOException ex) {
      // Logger.getLogger(ExempleClasseActionAjax.class.getName()).log(Level.SEVERE, null,
      // ex);
      System.out.println("\n" + ex.getMessage());
    }

    return "N'importe quoi, car la chaine ne sera pas prise en compte par le contrôleur frontal"; // return null
  }
}
