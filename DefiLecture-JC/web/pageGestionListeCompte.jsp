<%-- 
    Document   : gestionListeComptes
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<%  CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("listeComptes", dao.findAll());   %>
    
<h2>Gestionnaire de comptes</h2>  

    <table class="table">
        
      <thead>
        <tr>
          <th>Prenom</th>
          <th>Nom</th>
          <th>Pseudonyme</th>
          <th>Courriel</th>
          <th>Rôle</th>
        </tr>
      </thead>
      <tbody>
              
      <c:forEach items="${listeComptes}" var="compte">

        <c:choose>
            <c:when test="${compte.role eq 1}">
                <c:set var="role" value="Participant"></c:set>        
            </c:when>
            <c:when test="${compte.role eq 2}">
                <c:set var="role" value="Capitaine"></c:set>        
            </c:when>
            <c:when test="${compte.role eq 3}">
                <c:set var="role" value="Moderateur"></c:set>        
            </c:when>
            <c:when test="${compte.role eq 4}">
                <c:set var="role" value="Administrateur"></c:set>        
            </c:when>
            <c:otherwise>
                <c:set var="role" value="Participant"></c:set>
            </c:otherwise>
        </c:choose>

          
        <tr>
          <td>${compte.prenom}</td>
          <td>${compte.nom}</td>
          <td>${ empty compte.pseudonyme ? "---" : compte.pseudonyme}</td>
          <td>${compte.courriel}</td>
          <td>${role}</td>
          <td><c:if test="${ (sessionScope.connecte eq compte.idCompte ) or (sessionScope.role gt compte.role)}">
                <a href="*.do?tache=afficherPageGestionConfigurationCompte&id=${compte.idCompte}">Modifier</a>
              </c:if>
          </td>
        </tr>
      
      </c:forEach>   
      
      </tbody>
        
    </table>
    