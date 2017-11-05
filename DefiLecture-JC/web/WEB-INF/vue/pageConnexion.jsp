<%-- 
    Document   : pageConnexion
    Created on : 2017-10-15
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de connexion</title>
    </head>
    <body>
        <div class='row'> 
        <c:choose>
            
        <c:when test="${ empty sessionScope.connecte }">
            <div class="col-md-6 col-md-offset-3"> </div>
        <h1>Connexion</h1>
        <form action="connexion.do" method="post">
            <div class="form-group">
                <label for="identifiant">Identifiant :</label>
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input id="identifiant" type="text" class="form-control" name="identifiant" placeholder="Courriel ou Pseudonyme">
                </div>
            </div>
            
            <div class="form-group">
                <label for="motPasse">Mot de passe :</label>                
                <div class="input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                    <input id="motPasse" type="password" class="form-control" name="motPasse" placeholder="Mot de passe" >
                </div>
            </div>

            <input type="hidden" name="tache" value="effectuerConnexion">
            <button type="submit" class="btn btn-success">Connexion</button>
        </form>
        </c:when>
        <c:otherwise>
            <a href="accueil.do?tache=afficherPageAccueil">
                Retourner &agrave; la page d'accueil.
            </a>
        </c:otherwise>
        
        </c:choose>
            </div>
    </body>
</html>
