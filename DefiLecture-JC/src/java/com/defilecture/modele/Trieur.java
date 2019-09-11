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

public class Trieur {

  /**
   * @param order
   * @return
   */
  public String OrderBy(String order) {
    switch (order) {
      case "desc":
        order = " DESC";
        break;
      case "asc":
        order = " ASC";
        break;
      case "nom":
        order = " ORDER BY NAME";
        break;
      case "role":
        order = " ORDER BY ROLE";
        break;
      case "courriel":
        order = " ORDER BY COURRIEL";
        break;
      default:
        order = "";
    }

    return order;
  }

  public String OrderBy() {
    return OrderBy("");
  }
}
