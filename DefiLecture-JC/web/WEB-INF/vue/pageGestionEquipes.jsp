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
<%@ page pageEncoding="UTF-8" %>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.Equipe"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<script language="javascript" src="./script/jsPageGestionEquipes.js"></script>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="daoEquipe" scope="page" class="com.defilecture.modele.EquipeDAO">
    <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<div class="row listeEquipe-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
        <c:if test="${!empty requestScope.data['suppressionSucces']}">
            <div class="alert alert-success"><strong>${requestScope.data['suppressionSucces']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['suppressionEchec']}">
            <div class="alert alert-danger"><strong>${requestScope.data['suppressionEchec']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['equipeIntrouvable']}">
            <div class="alert alert-warning"><strong>${requestScope.data['equipeIntrouvable']}</strong></div>
        </c:if>
        <h2>Gestion des &eacute;quipes</h2>  
        <table class="table cacherSurMobile">
            <thead>
                <tr>
                    <th>Nom</th>
                    <th>Nombre de membres</th>
                    <th>Nombre de points</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="listeEquipes" value="${daoEquipe.findAll()}"/>
                <c:forEach items="${listeEquipes}" var="equipe">
                    <tr>
                        <td>${equipe.getNom()}</td>
                        <td>${equipe.getNbMembres()}</td>
                        <td>${equipe.getScore()}</td>
                        <td><a href="*.do?tache=afficherPageModificationEquipe&idEquipe=${equipe.getIdEquipe()}">Modifier</a></td>
                        <td><a onclick="supprimerEquipe(${equipe.getIdEquipe()})">Supprimer</a></td>
                    </tr>
                </c:forEach>   
            </tbody>
        </table>
    </div>
</div>
