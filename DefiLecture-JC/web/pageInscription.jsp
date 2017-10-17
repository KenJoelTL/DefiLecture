<%-- 
    Document   : pageInscription
    Created on : 2017-10-15, 17:49:43
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page D'inscription</title>
    </head>
    <body>
        <h1>Inscription</h1>
        <form action="connexion.Frontal" method="post">
            <div>
                Prenom : <input type="text" name="prenom" required/>*
            
                Nom : <input type="text" name="nom" required/>*
            </div>
            <div>
                Programme d'étude : <input type="text" name="programmeEtude" />

                Courriel : <input type="email" name="courriel" required/>*
            </div>
            <div>    
                Pseudonyme : <input type="text" name="pseudonyme" />
            </div>
            <div>
                Mot de passe : <input type="password" name="motPasse" required/>*
            
                Confirmation du mot de passe : <input type="password" name="confirmationMotPasse" required/>*
            </div>
            <input type="hidden" name="action" value="EffectuerInscription">
            <input type="submit" value=" S'inscrire" />
        </form>
    </body>
</html>
