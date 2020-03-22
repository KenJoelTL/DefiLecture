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
package com.defilecture.controleur;

import com.defilecture.modele.Compte;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class Action implements Executable, RequestAware, SessionAware, DataReceiver {
  protected HttpServletRequest request;
  protected HttpServletResponse response;
  protected HttpSession session;

  public abstract String execute();

  protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }

  @Override
  public void setSession(HttpSession session) {
    this.session = session;
  }

  protected boolean userIsAdmin() {
    return ((Integer) session.getAttribute("role")).intValue() == Compte.ADMINISTRATEUR;
  }

  protected boolean userIsModerateur() {
    return ((Integer) session.getAttribute("role")).intValue() == Compte.MODERATEUR;
  }

  protected boolean userIsCapitaine() {
    return ((Integer) session.getAttribute("role")).intValue() == Compte.CAPITAINE;
  }

  protected boolean userIsParticipant() {
    return ((Integer) session.getAttribute("role")).intValue() == Compte.PARTICIPANT;
  }

  protected boolean userIsConnected() {
    return session.getAttribute("connecte") != null;
  }

  protected LocalDateTime getDébutLectures() {
    return LocalDateTime.parse(
        (String) session.getServletContext().getAttribute("com.defilecture.dLecture"), formatter);
  }

  protected LocalDateTime getFinLectures() {
    return LocalDateTime.parse(
        (String) session.getServletContext().getAttribute("com.defilecture.fLecture"), formatter);
  }

  protected LocalDateTime getDébutInscriptions() {
    return LocalDateTime.parse(
        (String) session.getServletContext().getAttribute("com.defilecture.dInscription"),
        formatter);
  }

  protected LocalDateTime getFinInscriptions() {
    return LocalDateTime.parse(
        (String) session.getServletContext().getAttribute("com.defilecture.fLecture"), formatter);
  }

  protected int getLimiteLectureHard() {
    return Integer.parseInt(
        session.getServletContext().getAttribute("com.defilecture.limiteHard").toString());
  }

  protected int getLimiteLectureSoft() {
    return Integer.parseInt(
        session.getServletContext().getAttribute("com.defilecture.limiteSoft").toString());
  }

  protected int getNbMatelotsMax() {
    return Integer.parseInt(
        (String) session.getServletContext().getAttribute("com.defilecture.nbMatelots"));
  }
}
