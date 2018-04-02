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
        
        <div class='row creation-lecture-row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
                <h1>Cr&eacuteation d'un &eacute;quipage</h1>
        <form action="creationEquipe.do" method="post">
        <div class="form-group">
        <label for="nom">Nom de l'&eacute;quipage* :</label>
        <input type="text" class="form-control" id="nom" placeholder="Entrez le nom de votre nouvel &eacute;quipage" name="nom" required/>
        </div>
            <input type="hidden" name="tache" value="effectuerCreationEquipe">
            <button type="submit" class="btn btn-success">CRÉER</button>
        </form>
            </div>
        </div>
    </body>
</html>
