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
        <div class='row'> 
                <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 inscription-col"> 
                    <h1>Inscription</h1>
                    <form class="inscription-form" action="inscription.do" method="post">
                        <div class="form-group">
                            <label for="prenom">Prénom* :</label>
                            <div class="input-group">
                                <input id="prenom" type="text" class="form-control" name="prenom" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nom">Nom* :</label>
                            <div class="input-group">
                                <input id="nom" type="text" class="form-control" name="nom" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="programmeEtude">Programme d'étude :</label>
                            <div class="input-group">
                                <input id="programmeEtude" type="text" class="form-control" name="programmeEtude">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="courriel">Courriel :</label>
                            <div class="input-group">
                                <input id="courriel" type="email" class="form-control" name="courriel" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pseudonyme">Pseudonyme :</label>
                            <div class="input-group">
                                <input id="pseudonyme" type="text" class="form-control" name="pseudonyme">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="motPasse">Mot de passe* :</label>
                            <div class="input-group">
                                <input id="motPasse" type="password" class="form-control" name="motPasse" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="confirmationMotPasse">Confirmation du mot de passe* :</label>
                            <div class="input-group">
                                <input id="confirmationMotPasse" type="password" class="form-control" name="confirmationMotPasse" required>
                            </div>
                        </div> 

                        <input type="hidden" name="tache" value="effectuerInscription">
               
                       <button type="submit" class="btn btn-danger btn-inscription">S'INSCRIRE</button>
                    </form>
    
                </div>
       </div>
    </body>
</html>
