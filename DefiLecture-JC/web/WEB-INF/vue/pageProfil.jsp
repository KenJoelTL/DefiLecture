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

<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
<jsp:useBean id="now" class="java.util.Date" />

<!--Objet du compte-->
<jsp:useBean id="dao" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<c:set var="compte" scope="page" value="${dao.read(param.id)}"/>
<c:set var="currentId" scope="page" value="${sessionScope['currentId']}" />
<c:set var="memeUser" scope="page" value="${false}" />
 
<!--Objet de l'équipe-->
<jsp:useBean id="daoEquipe" class="com.defilecture.modele.EquipeDAO">
        <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>    
<c:set var="equipe" scope="page" value="${daoEquipe.read(compte.idEquipe)}"/>

<!-- Liste des lectures du user -->
<jsp:useBean id="daoLecture" scope="page" class="com.defilecture.modele.LectureDAO">
    <jsp:setProperty name="daoLecture" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<c:set var="listeLectures" value="${daoLecture.findByIdCompte(compte.idCompte)}"/>

<!--Regarde si c'est le profil du user connecter-->
<c:if test="${compte.idCompte == currentId}">
    <c:set var="memeUser" scope="page" value="${true}" />
</c:if>
<html>
    <head>
        <link href="https://fonts.googleapis.com/css?family=Acme" rel="stylesheet">
    </head>
    <body class="connexion-body">
        <div class="page-equipe-col" style="padding:0 5em 0 5em">
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 connexion-col modification-compte-col" style="margin-top:5em;">
                <div class="row contenuEnteteProfil">
                    <div class="col-lg-12">
                        <img class="imgProfil" src="<c:url value='${compte.avatar}'/>">
                        <h1><c:out value="${compte.pseudonyme}"/> </h1>
                    </div>
                    <div class="col-lg-12">
                        <c:if test="${memeUser == true}"> <!--Regarde que cest le user qui est connecté-->
                            <a href="?tache=afficherPageModificationCompte&id=${compte.idCompte}"><!--BTN pour modifier le profil du user-->
                                <button type="button" class="btn btn-primary btnModif">Modifier</button>
                            </a>
                        </c:if>
                    </div>
                </div>
               <hr/>
                <div class="row">
                    <div class="col-lg-5 col-lg-offset-1"> <!-- Informations perso -->
                        <div class="panel panel-default">
                            <div class="panel-heading"><p>Informations</p></div>
                            <div class="panInfo">
                                <p class='bold'>Prénom :</p>
                                <p> <c:out value="${compte.prenom}"/></p>
                                <p class='bold'>Nom :</p>
                                <p> <c:out value="${compte.nom}"/></p>
                                <c:if test="${memeUser == true}" > <!--Affiche seulement le email si c'est le profil du user-->
                                    <p class='bold'>Courriel : </p> 
                                    <p> <c:out value="${compte.courriel}"/></p>
                                </c:if>
                                <p class='bold'>Département :</p>
                                <p> <c:out value="${compte.programmeEtude}"/></p>
                            </div>
                        </div>
                    </div>     
                    <div class="col-lg-5"> <!-- Informations de l'équipe -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <p>
                                    Équipe
                                    <a href="?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">
                                        <span class="glyphicon glyphicon-eye-open glyphProfil"></span>
                                    </a>
                                </p>
                            </div>
                            <c:choose>
                                <c:when test="${!empty equipe}" > <!--Si le user fait partit d'une équipe-->
                                    <div class="panInfo">
                                        <p class='bold'>Nom de l'équipe :</p>
                                        <p><c:out value="${equipe.nom}" /></p>
                                        <p class='bold'>Points de l'équpe :</p>
                                        <p><c:out value="${equipe.point}" /></p>
                                    </div>
                                </c:when>
                                <c:otherwise> <!--Sinon, affiche un message pour avertir-->
                                    <p class="pErreur"><c:out value="${compte.pseudonyme} ne fait pas partie d'une équipe"/></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-1"></div>
                    <div class="col-lg-10">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <p>
                                    Lectures
                                    <c:if test="${memeUser == true}"> <!--Si c'est le profil du user-->
                                        <a href="?tache=afficherPageGestionLecture"> <!--Lien pour edit ses lectures-->
                                            <span class="glyphicon glyphicon-edit glyphProfil"></span>
                                        </a>

					<fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dLecture']}" var="datedebut" type="both"/>
					<fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>			
                                        <a href="?tache=afficherPageCreationLecture" id="ajoutLectures" style="display: ${now ge datedebut && now lt datefin ? 'block' : 'none'}">
                                            <span style="margin-right: 1em;" class="glyphicon glyphicon-plus glyphProfil"></span>
                                        </a>
					
                                    </c:if>
                                </p>
                            </div>
                            <c:choose>
                                <c:when test="${fn:length(listeLectures) > 0}" > <!--Affiche les lectures si le user en a-->
                                    <table class="table table-hover table-bordered table-striped">
                                        <thead class="enteteTabLect">
                                          <tr>
                                            <th scope="col">Titre</th>
                                            <th scope="col">Durée (Mins)</th>
                                            <th scope="col">Date</th>
                                            <th scope="col">Obligatoire</th>
                                          </tr>
                                        </thead>
                                        <tbody> 
                                            <c:forEach items="${listeLectures}" var="lecture">
                                                <tr>
                                                    <td>${lecture.titre}</td>
                                                    <td>${lecture.dureeMinutes}</td>
                                                    <td>${lecture.dateInscription}</td>
                                                    <c:choose>
                                                        <c:when test="${lecture.estObligatoire == 0}">
                                                            <td>Non</td>
                                                        </c:when>
                                                        <c:when test="${lecture.estObligatoire == 1}">
                                                            <td>Oui</td>
                                                        </c:when>
                                                    </c:choose>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:when>
                                <c:otherwise> <!--Si le user n'a pas de lecture, affiche un message d'Avertissement-->
                                    <p class="pErreur"><c:out value="${compte.pseudonyme} n'a pas effectué de lecture." /></p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-lg-1"></div>
                </div>
            </div>
        </div>
    </body>
</html>

