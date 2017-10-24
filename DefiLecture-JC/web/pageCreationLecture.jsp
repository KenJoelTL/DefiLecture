<%-- 
    Document   : pageCreationLecture
    Created on : 2017-10-24, 13:07:39
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
        <h1>Inscription d'une lecture</h1>
        <form action="*.do" method="post">
            <div>
                Titre* : <input type="text" name="titre" required />
            </div>
            <div>
                Durée de la lecture (en minutes)* : <input type="text" name="dureeMinutes" required />
            </div>
            <div>
                <br>
                La lecture était-elle obligatoire?
                <div class="radio">
                    <label><input type="radio" name="obligatoire" value="1" required >oui</label>
                </div>
                <div class="radio">
                    <label><input type="radio" name="obligatoire" value="0" required >non</label>
                </div>
            </div>
           
            <input type="hidden" name="tache" value="effectuerCreationLecture">
            <input type="submit" value="Ajouter" />
        </form>
    </body>
</html>