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

<%  try{
        EquipeDAO dao = new EquipeDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
        pageContext.setAttribute("listeEquipes", dao.findAll());
    }
    catch(SQLException e){
        response.sendError(response.SC_INTERNAL_SERVER_ERROR,"Erreur interne" + e.getMessage());
	out.close();
    } %>

        
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Défi lecture - Tableau de pointages</title>
    </head>
    <body>


        <div class="row scores-row">
             <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 accueil-col"> 
               
    
                </div>
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col">
                <h2>Tableau de pointages</h2>  

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
