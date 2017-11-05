<%-- 
    Document   : pageTableauDesScores
    Created on : 2017-10-27, 21:22:09
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

<%  EquipeDAO dao = new EquipeDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("listeEquipes", dao.findAll());   %>
    
<h2>Tableau des scores</h2>  

    <table class="table">
      <thead>
        <tr>
          <th>Rang</th>
          <th>Nom</th>
          <th>Points</th>
        </tr>
      </thead>
      
      <tbody>
      <c:forEach items="${listeEquipes}" var="equipe">          
      <c:set var="i" value="${i+1}"/>          
        <tr>
          <td>#${i}</td>
          <td><a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
          <td>${equipe.point}</td>
        </tr>
      </c:forEach>  
      </tbody>
      
    </table>
    </body>
</html>
