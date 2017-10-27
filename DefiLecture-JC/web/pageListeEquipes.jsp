<%-- 
    Document   : pageListeEquipes
    Created on : 2017-10-24, 20:46:22
    Author     : Joel
--%>

<%@ page pageEncoding="UTF-8" %>
<%@page import="modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%  EquipeDAO dao = new EquipeDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("listeEquipes", dao.findAll());   %>
    
<h2>Gestionnaire de comptes</h2>  

    <table class="table">
      <thead>
        <tr>
          <th>Rang</th>
          <th>Nom</th>
          <th>Points</th>
        </tr>
      </thead>
      
      <c:set var="i" value="0"/>          
      <tbody>
      <c:forEach items="${listeEquipes}" var="equipe">          
        <tr>
          <td>#${i}</td>
          <td><a href="pageEquipe.do?afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
          <td>${equipe.point}</td>
        </tr>${i+=1}
      </c:forEach>  
      </tbody>
      
    </table>
