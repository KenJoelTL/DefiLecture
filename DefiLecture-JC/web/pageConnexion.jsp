<%-- 
    Document   : pageConnexion
    Created on : 2017-10-15
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page de connexion</title>
    </head>
    <body>
        <h1>Connexion</h1>
        
        <%if(session.getAttribute("connecte") != null){%>
        <p>
            Bonjour <%=session.getAttribute("connecte")%>
        </p>
        <%}
        %>
        
        <form action="connexion.Frontal" method="post">
            Identifiant : <input type="text" name="identifiant" />
            Mot de passe : <input type="password" name="motPasse" />
            <input type="hidden" name="action" value="EffectuerConnexion">
            <input type="submit" value=" Connexion" />
        </form>
    </body>
</html>
