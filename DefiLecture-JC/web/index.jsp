<%-- 
    Document   : index
    Created on : 2017-10-14, 12:23:05
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Layout -->
<html>
    <head>
        <title>Index</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
         <div class="container-fluid">
          
          <div class="navbar-header">
            <a class="navbar-brand" href="#">Défi-Lecture</a>
            <!-- Apparait lorque la fenêtre devient de la taille d'un téléphone mobile -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>                       
            </button>
          </div>          
             
          <!-- Options contenues par le boutons à son activation -->   
          <div class="collapse navbar-collapse" id="optionsNavigation">
            <ul class="nav navbar-nav">
              <li class="active"><a href="#">Acceuil</a></li>
              <li><a href="#">Tableau des scores</a></li>
              <li><a href="#">Page de profil</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <!--  <li><a href="#"><span class="glyphicon glyphicon-user"></span> S'incrire</a></li> -->
              <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>
            </ul>   
          </div>
             
         </div>
        </nav>
        
        <div class='container' style='margin-top: 100px'>
            <div class='row'>
                
                <a href='profilEquipe.jsp'>Page de profil</a>
            </div>
        </div>
        
    </body>
</html>

