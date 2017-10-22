<%-- 
    Document   : lecture
    Created on : 2017-10-17, 21:52:12
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Création d'une lecture</title>
    </head>
    <body>
        <h1>Inscription</h1>
        <form action="*.do" method="post">
            <div>
                Titre* : <input type="text" name="titre" required/>
            </div>
            <div>
                Durée de la lecutre (en minute)* : <input type="text" name="dureeMinutes" required />
              
            </div>
           
            <input type="hidden" name="tache" value="effectuerCreationLecture">
            <input type="submit" value="Ajouter" />
        </form>
    </body>
</html>
