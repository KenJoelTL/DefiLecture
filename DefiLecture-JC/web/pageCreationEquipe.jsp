<%-- 
    Document   : pageCreationEquipe
    Created on : 2017-10-26, 00:10:33
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Cr&eacuteation d'une &eacute;quipe</h1>
        <form action="creationEquipe.do" method="post">
        <div class="form-group">
        <label for="Nom de l'&eacutequipe*">Nom de l'&eacutequipe* :</label>
        <input type="text" class="form-control" id="nom" placeholder="Entrez le nom de votre nouvelle &eacute;quipe" name="nom" required/>
        </div>
            <input type="hidden" name="tache" value="effectuerCreationEquipe">
            <input type="submit" value="Créer" />
        </form>
    </body>
</html>
