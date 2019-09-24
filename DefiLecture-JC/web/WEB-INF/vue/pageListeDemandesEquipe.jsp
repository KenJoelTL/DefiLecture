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

<%@page import="com.defilecture.modele.Equipe"%>
<%@page import="com.defilecture.modele.DemandeEquipe"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="com.defilecture.modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script language="javascript" src="./script/jsPageListeDemandeEquipe.js"></script>
<c:if test="${empty sessionScope.connecte or (!(sessionScope.role eq 2) and (requestScope.ordre eq 'recu'))}">
    <jsp:forward page="*.do?tache=afficherPageAccueil"></jsp:forward>
</c:if>
<!-- Faire la connexion -->
<jsp:useBean id="connexion" class="jdbc.Connexion"/>

<!-- Cree les DAOs -->
<jsp:useBean id="eqpDao" class="com.defilecture.modele.EquipeDAO" scope="page">
    <jsp:setProperty name="eqpDao" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>
<jsp:useBean id="demandeEqDao" class="com.defilecture.modele.DemandeEquipeDAO" scope="page">
    <jsp:setProperty name="demandeEqDao" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>

<c:choose>
    <c:when test="${param.ordre eq 'recu'}">
        <!-- Cree les DAOs -->
        <jsp:useBean id="cptDao" class="com.defilecture.modele.CompteDAO" scope="page">
            <jsp:setProperty name="cptDao" property="cnx" value="${connexion.connection}"/>
        </jsp:useBean>
	<!-- Declarer les variables-->
        <jsp:useBean id="compte" class="com.defilecture.modele.Compte" scope="page"/>
        <jsp:useBean id="equipe" class="com.defilecture.modele.Equipe" scope="page"/>
        <!-- Si l'equipe n'est pas scpecifier -->
        <c:if test="${empty param.idEquipe}">
            <c:set var="compte" value="${cptDao.read(sessionScope.connecte)}"/>
        </c:if>
        
        <c:set var="equipe" value="${eqpDao.read(compte.getIdEquipe())}" scope="page"/>
        <c:set var="listeDemandes" value="${demandeEqDao.findByIdEquipeStatutDemande(compte.getIdEquipe(),DemandeEquipe.EN_ATTENTE)}"/>
    </c:when>
    
    <c:otherwise>
        <c:set var="listeDemandes" value="${demandeEqDao.findByIdCompte(sessionScope.connecte)}"/>
    </c:otherwise>
</c:choose>






<div class="row demandes-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 demandes-col">
        <h2>Liste des demandes</h2>
        
        <div id='notification'></div>
        
        <c:if test="${(param.ordre eq 'recu') and (sessionScope.role eq Compte.CAPITAINE) }">
            <c:choose>
                <c:when test="${ equipe.nbMembres-1 lt applicationScope['com.defilecture.nbMatelots'] }">
		    <table class="table">

			<thead>
			    <tr>
				<th>Demandes d'adh&eacute;sion</th>
				<th>&Eacute;tat de la demande</th>
				<th></th>
			    </tr>
			</thead>

			<tbody>
			    <c:set var="i" value="0"/>
			    <c:forEach items="${listeDemandes}" var="demande">
				<c:set var="auteur" value="${cptDao.read(demande.idCompte)}"/>
				<tr id='utilisateur-${i}'>
				    <td>Demande envoy&eacute;e par ${auteur.prenom} ${auteur.nom}</td>
				    <td>
					<a id="btnAccepter" onclick="Accepter(${demande.idDemandeEquipe},${i})">Accepter</a>
					<a id="btnRefuser" onclick="Refuser(${demande.idDemandeEquipe},${i})">Refuser</a>
				    </td>
				</tr>
				<c:set var="i" value="${i + 1}"/>
			    </c:forEach>
			</tbody>

		    </table>
		</c:when>
                <c:otherwise>
                    <div class="alert alert-warning" style="text-align:center"><% out.println(application.getAttribute("vocEquipeComplete"));%> <div>
                </c:otherwise>
            </c:choose>
        </c:if>

        
		    </div>
		    </div>

