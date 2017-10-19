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
            <a class="navbar-brand" href='*.do?tache=""'>Défi-Lecture</a>
            <!-- Apparait lorsque la fenêtre devient de la taille d'un téléphone mobile -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>                       
            </button>
          </div>          
             
          <!-- Options contenues dans le bouton à son activation -->   
          <div class="collapse navbar-collapse" id="optionsNavigation">
            <ul class="nav navbar-nav">
                <li class="active"><a href='*.do?tache=""'>Acceuil</a></li>
                <li class="active"><a href="*.do?tache=afficherCreationLecture">Créer Lecture</a></li>
              <li><a href="#"><span class="glyphicon glyphicon-stats"></span> Tableau des scores</a></li>
             <%if(session.getAttribute("connecte") != null){%>
              <li><a href="#">Page de profil</a></li>
              <li><a href="*.do?tache=afficherPageEquipe">Page d'équipe</a></li>
            <%}else{%>
              <li style="background-color: #349737;"><a href='*.do?tache=afficherPageInscription' style="color: #fff;" ><span class="glyphicon glyphicon-education"></span> S'incrire</a></li>        
            <%}%>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <!--  <li><a href="#"><span class="glyphicon glyphicon-user"></span> S'incrire</a></li> -->
            <%if(session.getAttribute("connecte") == null){%>
              <li><a href='*.do?tache=afficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>        
            <%}else{%>
              <li><a href='*.Frontal?action=EffectuerDeconnexion'><span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter</a></li>
            <%}%>
            
            </ul>   
          </div>
             
         </div>
        </nav>
        
        <div class='container' style='margin-top: 100px'>
            
                
                <a href='*.do?tache=afficherPageEquipe'>Page d'équipe</a>
                
               
                
                <%if (request.getAttribute("vue") == null) 
                {
                %>
                
                <%@include file="accueil.jsp" %>      
                 
                <%}
                else{
                    String vue = request.getAttribute("vue").toString();
                %>
                <jsp:include page="<%=vue%>" ></jsp:include>
                <%}%>
                    


                
            
        </div>
        
    </body>
</html>

