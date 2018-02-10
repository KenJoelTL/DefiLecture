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
    <body class="connexion-body">
        <div class='row'> 
        <c:choose>
            
        <c:when test="${ empty sessionScope.connecte }">
       <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 connexion-col"> 
           <h1>Connexion</h1>
        <form class="connexion-form" action="connexion.do" method="post">
            <div class="form-group">
                <label for="identifiant">Nom d'utilisateur :</label>
                <div class="input-group">
          
                    <input id="identifiant" type="text" class="form-control" name="identifiant">
                </div>
            </div>
            
            <div class="form-group">
                <label for="motPasse">Mot de passe :</label>                
                <div class="input-group">
                    
                    <input id="motPasse" type="password" class="form-control" name="motPasse"  >
                </div>
            </div>
            <a href="#">
                Mot de passe oublié?.
            </a>
            <input type="hidden" name="tache" value="effectuerConnexion">
            <button type="submit" class="btn btn-danger">ENVOYER</button>
            
        </form>
      </div>
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
