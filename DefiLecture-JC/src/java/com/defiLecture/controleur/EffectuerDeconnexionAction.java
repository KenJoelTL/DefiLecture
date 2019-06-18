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
package com.defiLecture.controleur;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** @author Joel */
public class EffectuerDeconnexionAction
    implements Action, RequestAware, SessionAware, RequirePRGAction {
  private HttpSession session;
  private HttpServletRequest request;
  private HttpServletResponse response;

  @Override
  public String execute() {
    String action = "accueil.do?tache=afficherPageAccueil";

    if (session != null) session.invalidate();
    session = request.getSession(false);

    return action;
  }

  @Override
  public void setSession(HttpSession session) {
    this.session = session;
  }

  @Override
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setResponse(HttpServletResponse response) {
    this.response = response;
  }
}
