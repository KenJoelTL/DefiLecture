<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
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
    Document   : pageProfil
    Created on : 2017-10-22, 08:26:14
    Author     : Charles
--%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
<jsp:useBean id="dao" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
    
<c:set var="compte" scope="page" value="${dao.read(param.id)}"/>
<c:set var="currentId" scope="page" value="${sessionScope['currentId']}" />
<c:set var="memeUser" scope="page" value="${false}" />
 
<c:if test="${compte.idCompte == currentId}">
    <c:set var="memeUser" scope="page" value="${true}" />
</c:if>
<c:out value="${memeUser}"/>
<html>
    <head>
        <title>Profil </title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Acme" rel="stylesheet">
    </head>
    <body style='background-color:lightgrey;'>
        <div class="container-fluid contProfil" style="background-color:white;">
            <div class="row contenuEnteteProfil">
                <div class="col-lg-12">
                    <img class="imgProfil" src="<c:url value='${compte.avatar}'/>">
                    <h1><c:out value="${compte.pseudonyme}"/></h1>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-5 col-lg-offset-1">
                    <div class="panel panel-default">
                        <div class="panel-heading">Informations</div>
                        <div class="panInfo">
                            <p class='bold'>Prénom :</p>
                            <p> <c:out value="${compte.prenom}"/></p>
                            <p class='bold'>Nom :</p>
                            <p> <c:out value="${compte.nom}"/></p>
                            <c:if test="${memeUser == true}" >
                                <p class='bold'>Courriel : </p> 
                                <p> <c:out value="${compte.courriel}"/></p>
                            </c:if>
                            <p class='bold'>Département :</p>
                            <p> <c:out value="${compte.programmeEtude}"/></p>
                        </div>
                    </div>
                </div>
                        
                <div class="col-lg-5">
                    <div class="panel panel-default">
                        <div class="panel-heading">Équipe</div>
                    </div>
                </div>
            </div> 
        </div>
    </body>
</html>

