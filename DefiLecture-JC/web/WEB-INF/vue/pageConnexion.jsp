<%-- 
    Document   : pageConnexion
    Created on : 2017-10-15
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de connexion</title>
    </head>
    <body>
        <c:choose>
            
        <c:when test="${ !empty sessionScope.connecte }">
        <p>
            Bonjour <%=session.getAttribute("connecte")%>
        </p>
        <h1>Connexion</h1>
            
        <form action="connexion.do" method="post">
            Identifiant : <input type="text" name="identifiant" />
            Mot de passe : <input type="password" name="motPasse" />
            <input type="hidden" name="tache" value="effectuerConnexion">
            <input type="submit" value=" Connexion" />
        </form>
        </c:when>
        <c:otherwise>
            <a href="accueil.do?tache=afficherPageAccueil">
                Retourner &agrave; la page d'accueil.
            </a>
        </c:otherwise>
        
        </c:choose>
    </body>
</html>
