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
        
        <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
                <div class="creation-lecture-form">
                    <h1>Inscription d'une lecture</h1>
                    <form action="*.do" method="post">
                        <div class="form-group">
                <label for="nom">Nom du défi* : </label>
                <input class="form-control" type="text" name="nom" required />
            </div>
                        
                        <div class="form-group">
                            <label for="titre">Titre* : </label>
                            <input class="form-control" type="text" name="titre" required />
                        </div>
                        <div class="form-group">
                            <label for="dureeMinutes">Durée de la lecture (en minutes)* : </label>
                            <input class="form-control" type="text" name="dureeMinutes" required />
                        </div>
                        <div class="lectureObligatoire">
                            <div class="form-group">
                                <label for="obligatoire">La lecture était-elle obligatoire? : </label>

                                <div class="radio">
                                    <label><input type="radio" name="obligatoire" value="1" required >oui</label>
                                </div>
                                <div class="radio">
                                    <label><input type="radio" name="obligatoire" value="0" required >non</label>
                                </div>
                            </div>
                        </div>

                        <input type="hidden" name="tache" value="effectuerCreationLecture">
                        <button type="submit" class="btn btn-success" value="Ajouter" >Ajouter</button>
                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>