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
    Document   : index
    Created on : 2017-10-14, 12:23:05
    Author     : Joel & Charles
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Theme"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ !empty sessionScope.connecte}">
<% CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("compteConnecte", dao.read(session.getAttribute("connecte").toString())); %>
</c:if> 

<%
    Theme t= new Theme();
    Iterator<Map.Entry<String, String>> it = t.getTheme().entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry<String, String> pair = it.next();
         application.setAttribute(pair.getKey(), pair.getValue());
             }
 %>  
<!DOCTYPE html>
<!-- Layout -->
<html>
    <head>
        <title>D&eacute;fi Lecture</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="./css/styles.css">
        <link rel="stylesheet" href="./css/defiLectureStyles.css" type="text/css"> </head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body >

        <div class='container-fluid'  style="margin-bottom: 90px" >
        <nav class="navbar navbar-inverse navbar-fixed-top">
         <div class="container-fluid">
          
          <div class="navbar-header">
            <a class="navbar-brand logo-navigation" href='*.do?tache=afficherPageMarcheASuivre'></a>
            <!-- Apparait lorsque la fenêtre devient de la taille d'un téléphone mobile -->
            <button id="btn-burger" type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>                       
            </button>
          </div>          
             
          <!-- Options contenues dans le bouton à son activation -->   
          <div class="collapse navbar-collapse" id="optionsNavigation">
            <ul class="nav navbar-nav">
           
             <c:if test="${ !empty sessionScope.connecte && sessionScope.role le 2 }">
                
          
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lectures
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href="*.do?tache=afficherPageCreationLecture">Ajouter une lecture</a></li>
                      <li><a href="*.do?tache=afficherPageGestionLecture">Voir mes lectures</a></li>
                    </ul>
                </li>
      

                
                
            </c:if>
           
                <li><a href="scoreboard.do?tache=afficherPageTableauScores">
                    <% out.println(application.getAttribute("vocBanque"));%>
                    </a>
                </li>
             
            <c:choose>
                <c:when test="${ !empty sessionScope.connecte }">
                   <!-- <li><a href="*.do?tache=afficherPageProfil">Page de profil</a></li> -->
                    <c:choose>
                        <c:when test="${ (sessionScope.role eq 2) }">
                            <c:choose>
                                <c:when test="${compteConnecte.idEquipe gt -1}">
                                    <li><a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                                            Page <% out.println(application.getAttribute("vocEquipe1"));%></a>
                                    </li>                        
                                    <li><a href="joindreEquipe.do?tache=afficherPageListeDemandesEquipe&ordre=recu">
                                            Acc&eacute;der aux demandes</a>
                                    </li>                                              
                                </c:when>
                                <c:otherwise>
                                    <li><a href="creationEquipe.do?tache=afficherPageCreationEquipe">Cr&eacute;er <% out.println(application.getAttribute("vocEquipe3"));%></a></li>                                            
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${sessionScope.role < 3}">
                                    <c:choose>
                                        <c:when test="${compteConnecte.idEquipe > -1}">
                                            <li><a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                                                    Page <% out.println(application.getAttribute("vocEquipe1"));%></a>
                                            </li>                        
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="joindreEquipe.do?tache=afficherPageListeEquipes">Joindre<% out.println(application.getAttribute("vocEquipe3"));%></a></li>                                              
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                        </c:otherwise>
                    
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <li >
                        <a href='*.do?tache=afficherPageInscription' " >
                             S'inscrire</a>
                    </li>
                </c:otherwise>
            </c:choose>
                    
            <c:if test="${ !empty sessionScope.role }">
                 <c:if test="${ sessionScope.role eq 4 }">
                     
                     <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">Comptes
                        <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                          <li ><a href="*.do?tache=afficherPageGestionListeCompte">G&eacute;rer les comptes</a></li>
                          <li ><a href="*.do?tache=afficherPageAdresseCourriel">Liste d'adresses</a></li>
                          <li ><a href="*.do?tache=afficherPageListeLectures">Liste des lectures</a></li>
                        </ul>
                    </li>
                 </c:if>
                 <c:if test="${ sessionScope.role ge 1 }">
                    
                    
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">D&eacute;fis
                        <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <c:if test="${ sessionScope.role ge 3 }">
                          <li><a href="*.do?tache=afficherPageCreationDefi">Cr&eacute;er un d&eacute;fi</a></li>
                          <li><a href="*.do?tache=afficherPageDefisReussis">Liste défis réussis</a></li>
                          </c:if>
                          <li><a href="*.do?tache=afficherPageParticipationDefi">Voir les d&eacute;fis</a></li>
                        </ul>
                    </li>
                    
                 </c:if>
            </c:if>
            
                
            </ul>
            <ul class="nav navbar-nav navbar-right">
                
                <li id='li-facebook'>
                    <a id='facebook'  target="_blank" href='https://www.facebook.com/DefiLectureCollegeRosemont/'></a>
                </li>
                 
                 <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><% out.println(application.getAttribute("vocCode"));%>   
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href='*.do?tache=afficherPageMarcheASuivre'>Marche à suivre</a></li>
                      <li><a href='*.do?tache=afficherPageCodeConduite'>Code de conduite</a></li>
                      <li><a href='*.do?tache=afficherPageGlossaire'>Glossaire</a></li>
                      <li><a href='*.do?tache=afficherPageContributeurs'>Contributeurs</a></li>
                    </ul>
                </li>
                
            <c:choose>
                <c:when test="${ empty sessionScope.connecte }">
                    <li><a href='*.do?tache=afficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>        
                </c:when>
                <c:otherwise>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">  
                    <span class="glyphicon glyphicon-cog"></span>
                    <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                      <li><a href='details.do?tache=afficherPageModificationCompte&id=${sessionScope.connecte}'>
                              <span class="glyphicon glyphicon-user"></span> Mon Compte</a>
                      </li>
                      <li><a href='*.do?tache=effectuerDeconnexion'>
                              <span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter</a>
                      </li>
                    </ul>
                </li>
                </c:otherwise>
            </c:choose>
   
            
            </ul>   
          </div>
             
         </div>
        </nav>
        </div>
        
            
                
                <div class="container">
                    
                    <c:choose>
                        <c:when test="${ !empty requestScope.vue }">
                            <c:set var="vue" value="/WEB-INF/vue/${requestScope.vue}"/>
                            <jsp:include page="${vue}" ></jsp:include>
                        </c:when>
                        <c:otherwise>
                            <jsp:include page="/WEB-INF/vue/pageMarcheASuivre.jsp" ></jsp:include>
                        </c:otherwise>
                    </c:choose>
                    
               

                </div> 

                
            
        
    </body>
</html>