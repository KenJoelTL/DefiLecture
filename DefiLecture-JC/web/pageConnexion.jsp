<%-- 
    Document   : pageConnexion
    Created on : 2017-10-15
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:if test="${ !empty sessionScope.connecte }">
        <p>
            Bonjour <%=session.getAttribute("connecte")%>
        </p>
        </c:if>
-<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de connexion</title>
    </head>
    <body>
        <h1>Connexion</h1>
            
        <form action="connexion.do" method="post">
            Identifiant : <input type="text" name="identifiant" />
            Mot de passe : <input type="password" name="motPasse" />
            <input type="hidden" name="tache" value="effectuerConnexion">
            <input type="submit" value=" Connexion" />
        </form>
    </body>
</html>
