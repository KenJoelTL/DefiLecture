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
    Document   : pageGestionDefi
    Created on : 2017-10-24, 17:34:47
    Author     : Charles
--%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.DefiDAO"%>
<%@page import="com.defiLecture.modele.InscriptionDefiDAO"%>
<%@page import="com.defiLecture.modele.InscriptionDefi"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.Defi"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>

<!-- Connexion -->
<jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>

<!-- Declaration des daos -->
<jsp:useBean id="daoInscriptionDefi" class="com.defiLecture.modele.InscriptionDefiDAO">
    <jsp:setProperty  name="daoInscriptionDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoDefi" class="com.defiLecture.modele.DefiDAO">
    <jsp:setProperty  name="daoDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty  name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<!-- Declaration des entiters -->
<jsp:useBean id="compte" class="com.defiLecture.modele.Compte"></jsp:useBean>

<!-- Setter le compte -->
<c:set var="compte" value="${daoCompte.read(sessionScope.connecte)}"/>
<!-- Mettre le role du compte dans la page -->
<c:set var="role" scope="page" value="${compte.role}"/>

<!-- Prendre la liste complete des defis -->
<jsp:useBean id="listeDefi" class="java.util.LinkedList" />
<c:choose>
   <c:when test="${role<3}">
      <c:set var="listeDefi" scope="page" value="${daoDefi.findHistorique()}"></c:set>
   </c:when>
   <c:otherwise>
      <c:set var="listeDefi" scope="page" value="${daoDefi.findAllByIdCompte(sessionScope.connecte)}"></c:set>
   </c:otherwise>
</c:choose>

<!-- Trouver la liste des defis que le compte cetait inscrit -->
<c:set var="listeInscriptionDefi" scope="page" value="${daoInscriptionDefi.findAllByIdCompte(sessionScope.connecte)}"/>

<!-- Création des liste réussi et echoue, déterminer si le partcipant a déjà participé au défis -->
<jsp:useBean id="listeReussi" class="java.util.LinkedList" />
<jsp:useBean id="listeEchoue" class="java.util.LinkedList" />
<c:forEach items="${pageScope.listeInscriptionDefi}" var="defi">
   <c:choose>
       <c:when test="${defi.getEstReussi() == 1}">
           <c:out value="${(listeReussi.add(defi.getIdDefi())?'':'')}"></c:out>
       </c:when>
       <c:otherwise>
           <c:out value="${(listeEchoue.add(defi.getIdDefi())?'':'')}"></c:out>
       </c:otherwise>
   </c:choose>
</c:forEach>

<div class="row participationDefi-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 participationDefi-col">
        <h1>Liste des défis</h1>
        <table class="table cacherSurMobile">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Valeur</th>
                    <th>Début</th>
                    <th>Fin</th>
                    <th></th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                
                <%-- variable qui indique la date d'aujourd'hui, pour faire des comparaisons--%>
                <jsp:useBean id="now" class="java.util.Date" />
                <fmt:formatDate var="dateMaintenant" value="${now}" pattern="yyyy-MM-dd' 'HH:mm:ss.S" />
                <c:forEach items="${listeDefi}" var="d">
                    <%-- Condition qui permet au participant de voir tous les défis qu'il a réussi ou échoué, et de voir les nouveaux défis à relever--%>
                    <c:if test="${(pageScope.role lt 3) and (listeReussi.contains(d.idDefi) or listeEchoue.contains(d.idDefi) or d.dateFin gt dateMaintenant) }">
                        <tr>
                            <td>${d.nom}</td>
                            <td>+ ${d.valeurMinute} doublons</td>
                            <c:catch>
                                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateDebut}" var="dateDebutPARSE" />
                            </c:catch>

                            <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
                            <td>${dateDebut} </td>
                            <c:catch>
                                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateFin}" var="dateFinPARSE" />
                            </c:catch>

                            <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
                            <td >${dateFin} </td>

                            <c:choose>
                                <c:when test="${listeReussi.contains(d.idDefi)}">
                                    <td ><span class="glyphicon glyphicon-ok"></span></td>
                                </c:when>
                                <c:when test="${listeEchoue.contains(d.idDefi)}">
                                    <td ><span class="glyphicon glyphicon-remove"></span></td>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${d.dateFin lt dateMaintenant}">
                                            <td> TERMINÉ </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td> <a class="btn btn-info" role="button" href="*.do?tache=afficherPageInscriptionDefi&id=${d.idDefi}">Relever le défi</a></td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:if>


                    <%-- Condition qui permet au modérateur ou à l'administrateur de voir tous les défis qu'il a créé--%>                              
                    <c:if test="${pageScope.role ge 3}">
                        <tr>
                            <td>${d.nom}</td>
                            <td>+ ${d.valeurMinute} doublons</td>
                            <c:catch>
                                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateDebut}" var="dateDebutPARSE" />
                            </c:catch>
                            <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
                            <td>${dateDebut} </td>
                            <c:catch>
                                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateFin}" var="dateFinPARSE" />
                            </c:catch>
                            <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
                            <td>${dateFin} </td>

                            <%-- Si le compte est un compte admin ou moderateur, il ne peut pas relever de défi, mais il peut les modifier--%>
                            <td><a href="*.do?tache=afficherPageModificationDefi&id=${d.idDefi}">modifier</a></td>

                             <%-- Sert à identifier si les défi sont en cours, en attente, ou terminé--%>
                            <c:choose>
                                <c:when test="${(d.dateDebut lt dateMaintenant) and (d.dateFin gt dateMaintenant)}">
                                    <td class="bg-success"> EN COURS </td>
                                </c:when>
                                <c:when test="${d.dateFin lt dateMaintenant}">
                                    <td class="bg-danger"> TERMINÉ </td>
                                </c:when>
                                <c:otherwise>
                                    <td> EN ATTENTE </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>