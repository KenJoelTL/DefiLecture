<%-- 
    Document   : pageGestionLecture
    Created on : 2017-10-24, 13:08:08
    Author     : Charles
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="modele.Compte"%>
<%@page import="modele.Lecture"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.LectureDAO"%>
<%@page import="jdbc.Connexion"%>
<h2>Liste de mes lectures</h2>

<% 
    Connexion.reinit();
    Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
    LectureDAO dao = new LectureDAO(cnx);
    List<Lecture> listeLectures = dao.findByIdCompteOrderByDate((int)(session.getAttribute("connecte")));
    pageContext.setAttribute("listeLectures", listeLectures);
  %>
    <table class="table">
        
        <thead>
        <tr>
          <th>Titre</th>
          <th>Durée</th>
          <th>Date d'inscription</th>
          <th>Obligatoire</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${listeLectures}" var="l">
            <tr>
              <td>${l.titre}</td>
              <td>${l.dureeMinutes} minutes</td>
              <td>${l.dateInscription} </td>
              <td>${l.estObligatoire eq 0 ? "NON" : "OUI"}</td>
              <td><a href="*.do?tache=afficherPageModificationLecture&id=${l.idLecture}">Modifier</a></td>
              <td><a href="#">Supprimer</a></td>
            </tr>
        </c:forEach>

      </tbody>
        
    </table>