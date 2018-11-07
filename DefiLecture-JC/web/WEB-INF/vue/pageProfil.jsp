<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%-- 
    Document   : pageProfil
    Created on : 2018-1-11
    Author     : Gabriel Grenier
--%>

<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<!--Regarde si c'est le profil du user connecter-->
<c:if test="${compte.idCompte == currentId}">
    <c:set var="memeUser" scope="page" value="${true}" />
</c:if>

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
                                    <span class="glyphicon glyphicon-eye-open lienAffEquipe"></span>
                                </a>
                            </p>
                        </div>
                        <div class="panInfo">
                            <p class='bold'>Nom de l'équipe :</p>
                            <p><c:out value="${equipe.nom}" /></p>
                            <p class='bold'>Points de l'équpe :</p>
                            <p><c:out value="${equipe.point}" /></p>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
    </body>
</html>

