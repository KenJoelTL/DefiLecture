<%-- 
    Document   : gestionListeComptes
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">    
       <h2>Gestionnaire de comptes</h2>  

        <table class="table cacherSurMobile">

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
              
          <c:set var="listeComptes" value="${dao.findAll()}"/>
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
                    <a href="details.do?tache=afficherPageModificationCompte&id=${compte.idCompte}">Modifier</a>
                  </c:if>
              </td>
            </tr>

          </c:forEach>   

          </tbody>

        </table>
    
    </div>
</div>
