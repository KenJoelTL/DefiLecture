<%-- 
    Document   : index
    Created on : 2017-10-14, 12:23:05
    Author     : Joel & Charles
--%>

<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@page import="jdbc.Config"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ !empty sessionScope.connecte}">
<% CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("compteConnecte", dao.read(session.getAttribute("connecte").toString())); %>
</c:if> 
    
<!DOCTYPE html>
<!-- Layout -->
<html>
    <head>
        <title>D&eacute;fi Lecture</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body style="background-color: whitesmoke;">

        <div class='container-fluid'  style="margin-bottom: 50px" >
        <nav class="navbar navbar-inverse navbar-fixed-top">
         <div class="container-fluid">
          
          <div class="navbar-header">
            <a class="navbar-brand" href='*.do?tache=""'>D&eacute;fi-Lecture</a>
            <!-- Apparait lorsque la fenêtre devient de la taille d'un téléphone mobile -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>                       
            </button>
          </div>          
             
          <!-- Options contenues dans le bouton à son activation -->   
          <div class="collapse navbar-collapse" id="optionsNavigation">
            <ul class="nav navbar-nav">
                <li class="active"><a href='*.do?tache=""'>Acceuil</a></li>
           
             <c:if test="${ !empty sessionScope.connecte }">
                
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lectures
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href="*.do?tache=afficherPageCreationLecture">Cr&eacute;er une lecture</a></li>
                      <li><a href="*.do?tache=afficherPageGestionLecture">Voir mes lectures</a></li>
                    </ul>
                </li>
                
                
            </c:if>
           
                <li><a href="scoreboard.do?tache=afficherPageTableauScores">
                    <span class="glyphicon glyphicon-stats"></span> 
                    Tableau des scores
                    </a>
                </li>
             
            <c:choose>
                <c:when test="${ !empty sessionScope.connecte }">
                    <li><a href="*.do?tache=afficherPageProfil">Page de profil</a></li>
                    <c:choose>
                        <c:when test="${ (sessionScope.role eq 2) or (sessionScope.role eq 4) }">
                            <c:choose>
                                <c:when test="${compteConnecte.idEquipe gt -1}">
                                    <li><a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                                            Page d'&eacute;quipe</a>
                                    </li>                        
                                    <li><a href="joindreEquipe.do?tache=afficherPageListeDemandesEquipe&ordre=recu">
                                            Acc&eacute;der aux demandes</a>
                                    </li>                                              
                                </c:when>
                                <c:otherwise>
                                    <li><a href="creationEquipe.do?tache=afficherPageCreationEquipe">Cr&eacute;er une equipe</a></li>                                            
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${compteConnecte.idEquipe gt -1}">
                                    <li><a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                                            Page d'&eacutequipe</a>
                                    </li>                        
                                </c:when>
                                <c:otherwise>
                                    <li><a href="joindreEquipe.do?tache=afficherPageListeEquipes">Joindre une &eacute;quipe</a></li>                                              
                                    <li><a href="joindreEquipe.do?tache=afficherPageListeDemandesEquipe&ordre=envoyee">Acc&eacute;der aux demandes</a></li>                                              
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <li style="background-color: #349737;">
                        <a href='*.do?tache=afficherPageInscription' style="color: #fff;" >
                            <span class="glyphicon glyphicon-education"></span> S'incrire</a>
                    </li>
                </c:otherwise>
            </c:choose>
                    
            <c:if test="${ !empty sessionScope.role }">
                 <c:if test="${ sessionScope.role eq 4 }">
                     <li class="active"><a href="*.do?tache=afficherPageGestionListeCompte">G&eacute;rer les comptes</a></li>
                 </c:if>
                 <c:if test="${ sessionScope.role ge 1 }">
                    
                    
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">D&eacute;fis
                        <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <c:if test="${ sessionScope.role ge 3 }">
                          <li><a href="*.do?tache=afficherPageCreationDefi">Cr&eacute;er un d&eacute;fi</a></li>
                          </c:if>
                          <li><a href="*.do?tache=afficherPageParticipationDefi">Voir les d&eacute;fis</a></li>
                        </ul>
                    </li>
                    
                 </c:if>
            </c:if>
            
                
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <c:choose>
                <c:when test="${ empty sessionScope.connecte }">
                    <li><a href='*.do?tache=afficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>        
                </c:when>
                <c:otherwise>
                    <li><a href='*.do?tache=effectuerDeconnexion'><span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter</a></li>
                </c:otherwise>
            </c:choose>
   
            
            </ul>   
          </div>
             
         </div>
        </nav>
        </div>
        
            
                
                <div class="container-fluid">
                    
                    <c:choose>
                        <c:when test="${ !empty requestScope.vue }">
                            <c:set var="vue" value="/WEB-INF/vue/${requestScope.vue}"/>
                            <jsp:include page="${vue}" ></jsp:include>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="/WEB-INF/vue/accueil.jsp" ></jsp:include>
                        </c:otherwise>
                    </c:choose>
                    
               

                </div> 

                
            
        
    </body>
</html>
