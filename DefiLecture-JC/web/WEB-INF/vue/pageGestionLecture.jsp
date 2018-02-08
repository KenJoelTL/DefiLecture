<%-- 
    Document   : pageGestionLecture
    Created on : 2017-10-24, 13:08:08
    Author     : Charles
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.Lecture"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.LectureDAO"%>
<%@page import="jdbc.Connexion"%>

<div class="row gestion-lecture-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 gestion-lecture-col">
        <h2>Liste de mes lectures</h2>


  <jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
  <jsp:useBean id="dao" scope="page" class="com.defiLecture.modele.LectureDAO">
      <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
  </jsp:useBean>
  
  <c:set var="listeLectures" value="${dao.findByIdCompteOrderByDate(sessionScope.connecte)}"/>
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
    
    </div>
</div>
