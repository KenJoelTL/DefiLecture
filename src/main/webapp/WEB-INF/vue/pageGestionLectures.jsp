<!--
    This file is part of DefiLecture.

    DefiLecture is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    DefiLecture is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
-->
<%-- 
    Document   : pageGestionLectures
    Created on : 2018-04-15, 09:35:38
    Author     : Charles
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<script language="javascript" src="./script/jsPageGestionLectures.js"></script>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  

<jsp:useBean id="daoLecture" scope="page" class="com.defilecture.modele.LectureDAO">
  <jsp:setProperty name="daoLecture" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" scope="page" class="com.defilecture.modele.CompteDAO">
  <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<c:set var="listeLectures" value="${ sessionScope.role >= 3 ? daoLecture.findAll() : daoLecture.findByIdCompteOrderByDate(sessionScope.connecte)}"/>

<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
      <c:choose>
        <c:when test="${ sessionScope.role < 3 }">
          <h1>Liste de mes lectures</h1>
        </c:when>    
        <c:otherwise>
          <h1>Liste des lectures</h1>
        </c:otherwise>
      </c:choose>
      <table class="table">       
        <thead>
          <tr>
            <th>Titre</th>
            <c:if test="${ sessionScope.role >= 3 }"> <th>Courriel</th></c:if>
            <th>Durée</th>
            <th>Date d'inscription</th>
            <th>Obligatoire</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${listeLectures}" var="lecture">
              <tr id="lecture-${lecture.idLecture}">
                <td>${lecture.titre}</td>
                <c:if test="${ sessionScope.role >= 3 }">
                  <c:set var="compte" value="${daoCompte.read(lecture.idCompte)}"/>
                  <td>${compte.courriel}</td>
                 </c:if>
                <td>${lecture.dureeMinutes} minutes</td>
                <td>${lecture.dateInscription} </td>
                <td>${lecture.estObligatoire eq 0 ? "NON" : "OUI"}</td>
                <c:if test="${ sessionScope.connecte eq lecture.idCompte || sessionScope.role >= 3 }">
                  <td><a id="supp" onclick="supprimer(${lecture.idLecture})">Supprimer</a></td>
                </c:if>
              </tr>
          </c:forEach>
        </tbody>
      </table> 
    </div>
</div>
