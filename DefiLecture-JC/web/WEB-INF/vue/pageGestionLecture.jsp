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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="com.defilecture.modele.Lecture"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="com.defilecture.modele.LectureDAO"%>
<%@page import="jdbc.Connexion"%>
<script language="javascript" src="./script/jsPageGestionLectures.js"></script>

<div class="row gestion-lecture-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 gestion-lecture-col">
        <h1>Liste de mes lectures</h1>


  <jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
  <jsp:useBean id="dao" scope="page" class="com.defilecture.modele.LectureDAO">
      <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
  </jsp:useBean>
  
  <c:set var="listeLectures" value="${dao.findByIdCompteOrderByDate(sessionScope.connecte)}"/>
            <table class="table cacherSurMobile">

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
                    <tr id="lecture-${l.idLecture}">
                      <td>${l.titre}</td>
                      <td>${l.dureeMinutes} minutes</td>
                      <td>${l.dateInscription} </td>
                      <td>${l.estObligatoire eq 0 ? "NON" : "OUI"}</td>
                      <td><a id="supp" onclick="supprimer(${l.idLecture})">Supprimer</a></td>
                    </tr>
                </c:forEach>

              </tbody>

            </table>
    
    </div>
</div>

      


