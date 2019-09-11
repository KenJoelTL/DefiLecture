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
    Document   : gestionListeComptes
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
        <c:if test="${!empty requestScope.data['suppressionSucces']}">
            <div class="alert alert-success"><strong>${requestScope.data['suppressionSucces']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['suppressionEchec']}">
            <div class="alert alert-danger"><strong>${requestScope.data['suppressionEchec']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['compteIntrouvable']}">
            <div class="alert alert-warning"><strong>${requestScope.data['compteIntrouvable']}</strong></div>
        </c:if>
        <h2>Gestionnaire de comptes</h2>  

        <table class="table cacherSurMobile">

            <thead>
                <tr>
                    <th>Prenom</th>
                    <th>Nom</th>
                    <th>Pseudonyme</th>
                    <th>Courriel</th>
                    <th>Rôle</th>
                    <th>Points</th>
                </tr>
            </thead>
            <tbody>

                <c:set var="listeComptes" value="${dao.findAll()}"/>
                <c:forEach items="${listeComptes}" var="compte">

                    <c:choose>
                        <c:when test="${compte.role eq 1}">
                            <c:choose>
                                <c:when test="${compte.devenirCapitaine eq 1}">
                                    <c:set var="role" value="Participant***"></c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="role" value="Participant"></c:set>   
                                </c:otherwise>
                            </c:choose>
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

                    <c:set var="permissionAccordee" value="${sessionScope.role gt Compte.CAPITAINE and sessionScope.role gt compte.role}"></c:set>
                    <tr>
                        <td>${compte.prenom}</td>
                        <td>${compte.nom}</td>
                        <td>${ empty compte.pseudonyme ? "---" : compte.pseudonyme}</td>
                        <td>${compte.courriel}</td>
                        <td>${role}</td>
                        <td>${compte.point}</td>
                        <td><c:if test="${ (sessionScope.connecte eq compte.idCompte ) or (permissionAccordee) }">
                                <a href="details.do?tache=afficherPageModificationCompte&id=${compte.idCompte}">Modifier</a>
                            </c:if>
                        </td>
                    </tr>

                </c:forEach>   

            </tbody>

        </table>

    </div>
</div>
