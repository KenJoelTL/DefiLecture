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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class ControleurFrontal extends HttpServlet {

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String t = request.getParameter("tache");

    // initialise le map s'il est null | Ce "tableau associatif" est propre à chaque session
    if ((HashMap) request.getSession(true).getAttribute("data") == null) {
      Map<String, Object> data = new HashMap(); // un peu comme le view bag
      request.getSession(true).setAttribute("data", data);
    }

    if (t != null) {

      Action action = ActionBuilder.getAction(t);
      if (action instanceof RequestAware) {
        if (action instanceof DataReceiver) {
          Map<String, Object> data =
              new HashMap((HashMap) request.getSession(true).getAttribute("data"));
          request.setAttribute("data", data);
        }
        // On vide le contenu
        ((HashMap) request.getSession(true).getAttribute("data")).clear();

        ((RequestAware) action).setRequest(request);
        ((RequestAware) action).setResponse(response);
      }
      if (action instanceof SessionAware) {
        ((SessionAware) action).setSession(request.getSession(true));
      }
      if (action instanceof DataSender) {
        ((DataSender) action).setData((HashMap) request.getSession(true).getAttribute("data"));
      }

      if (action instanceof SendAjaxResponse) {
        action.execute();
      } else {
        String vue = action.execute();

        if (action instanceof RequirePRGAction) {
          response.sendRedirect(vue);
        } else {
          this.getServletContext().getRequestDispatcher(vue).forward(request, response);
        }
      }
    } else {
      Action action = ActionBuilder.getAction("");
      ((RequestAware) action).setRequest(request);
      ((RequestAware) action).setResponse(response);

      String vue = action.execute();
      this.getServletContext().getRequestDispatcher(vue).forward(request, response);
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the
  // left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  } // </editor-fold>
}
