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

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
  protected Connection cnx;
  protected Trieur trieur;
  protected Paginateur paginateur;

  public DAO() {}

  public DAO(Connection cnx) {
    this.cnx = cnx;
  }

  public Connection getCnx() {
    return cnx;
  }

  public void setCnx(Connection cnx) {
    this.cnx = cnx;
  }

  public abstract boolean create(T x); // INSERT

  public abstract T read(int id); // SELECT

  public T read(String id) {
    if (id == null) {
      return null;
    }
    try {
      return this.read(Integer.parseInt(id));
    } catch (NumberFormatException e) {
      return null;
    }
  }

  public abstract boolean update(T x); // UPDATE

  public abstract boolean delete(T x); // DELETE

  public abstract List<T> findAll(); // SELECT
  //	public abstract List<T> findAll(int startingRow, int nbOfResult);      //SELECT
}
