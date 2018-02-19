<%-- 
    Document   : pageTableauScores
    Created on : 2017-10-27, 21:22:09
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


 <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
 <jsp:useBean id="dao" class="com.defiLecture.modele.EquipeDAO">
     <jsp:setProperty  name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
 </jsp:useBean>
 <c:set var="listeEquipes" value="${dao.findAll()}"></c:set>
        
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <div class="row scores-row">
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col">
                <h2>Tableau des scores</h2>  

                    <table class="table">
                      <thead>
                        <tr>
                          <th>Rang</th>
                          <th>Nom</th>
                          <th>Points</th>
                        </tr>
                      </thead>

                      <tbody>
                      <c:forEach items="${listeEquipes}" var="equipe">          
                      <c:set var="i" value="${i+1}"/>          
                        <tr>
                          <td>#${i}</td>
                          <td><a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
                          <td>${equipe.point}</td>
                        </tr>
                      </c:forEach>  
                      </tbody>

                    </table>
            </div>
        </div> 
    </body>
</html>
