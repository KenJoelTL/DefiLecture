<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%-- 
    Document   : pageProfil
    Created on : 2018-1-11
    Author     : Gabriel Grenier
--%>

<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>

<!--Objet du compte-->
<jsp:useBean id="dao" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<c:set var="compte" scope="page" value="${dao.read(param.id)}"/>
<c:set var="currentId" scope="page" value="${sessionScope['currentId']}" />
<c:set var="memeUser" scope="page" value="${false}" />
 
<!--Objet de l'équipe-->
<jsp:useBean id="daoEquipe" class="com.defiLecture.modele.EquipeDAO">
        <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>    
<c:set var="equipe" scope="page" value="${daoEquipe.read(compte.idEquipe)}"/>

<!-- Liste des lectures du user -->
<jsp:useBean id="daoLecture" scope="page" class="com.defiLecture.modele.LectureDAO">
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
    <body>
        <div class="container-fluid contProfil" style="background-color:white;">
            <div class="row contenuEnteteProfil">
                <div class="col-lg-12">
                    <img class="imgProfil" src="<c:url value='${compte.avatar}'/>">
                    <h1><c:out value="${compte.pseudonyme}"/> </h1>
                </div>
                <c:if test="${memeUser == true}">
                    <a href="?tache=afficherPageModificationCompte&id=${compte.idCompte}">
                        <button type="button" class="btn btn-primary btnModif">Modifier</button>  
                    </a>
                </c:if>
            </div>
            
            <div class="row">
                <div class="col-lg-5 col-lg-offset-1"> <!-- Informations perso -->
                    <div class="panel panel-default">
                        <div class="panel-heading"><p>Informations</p></div>
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
                <div class="col-lg-5"> <!-- Informations de l'équipe -->
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <p>
                                Équipe
                                <a href="?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">
                                    <span class="glyphicon glyphicon-eye-open lienTitreProfil"></span>
                                </a>
                            </p>
                        </div>
                        <c:choose>
                            <c:when test="${!empty equipe}" >
                                <div class="panInfo">
                                    <p class='bold'>Nom de l'équipe :</p>
                                    <p><c:out value="${equipe.nom}" /></p>
                                    <p class='bold'>Points de l'équpe :</p>
                                    <p><c:out value="${equipe.point}" /></p>
                                </div>
                            </c:when>
                            <c:otherwise>
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
                                <c:if test="${memeUser == true}">
                                <a href="?tache=afficherPageGestionLecture">
                                    <span class="glyphicon glyphicon-edit lienTitreProfil"></span>
                                </a>
                                <a href="?tache=afficherPageCreationLecture">
                                    <span style="margin-right: 1em;" class="glyphicon glyphicon-plus lienTitreProfil"></span>
                                </a>
                                </c:if>
                            </p>
                        </div>
                        <c:choose>
                        <c:when test="${fn:length(listeLectures) > 0}" >
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
                                            <th scope="row">${lecture.titre}</th>
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
                            <c:otherwise>
                                <p class="pErreur"><c:out value="${compte.pseudonyme} n'a pas effectué de lecture." /></p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="col-lg-1"></div>
            </div>
        </div>
    </body>
</html>

