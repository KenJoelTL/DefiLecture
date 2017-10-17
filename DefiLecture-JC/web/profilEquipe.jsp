<%-- 
    Document   : profilEquipe
    Created on : 2017-10-14
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Profil d'équipe</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <style>
            #toutPageEquipe { background-image: url("ocean.jpg");
                              background-repeat: no-repeat;
                              background-position: right top;
                              background-attachment: fixed;
                              z-index: -1;
            }
        </style>
    
    
    
    </head>
    <body style="background-color: whitesmoke;">
        
        <div id='toutPageEquipe' style='background-color: rgba(51, 122, 183, 0.5);'>    
            
                
        <nav class="navbar navbar-inverse">
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
              <li><a href="index.jsp">Acceuil</a></li>
              <li><a href="#">Tableau des scores</a></li>
            <%if(session.getAttribute("connecte") != null){%>
              <li><a href="#">Page de profil</a></li>
              <li class="active"><a href="*.Frontal?action=AfficherPageEquipe">Page d'équipe</a></li>
            <%}%>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            <!--  <li><a href="#"><span class="glyphicon glyphicon-user"></span> S'incrire</a></li> -->
            <%if(session.getAttribute("connecte") == null){%>
              <li><a href='*.Frontal?action=AfficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>        
            <%}else{%>
              <li><a href='*.Frontal?action=EffectuerDeconnexion'><span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter</a></li>
            <%}%>
            </ul>   
          </div>
             
         </div>
      
            </nav>
        
            <div class='container-fluid'>
            
                <div class='jumbotron' style=" background-color: rgba(34, 34, 34, 0.78); color: whitesmoke;"> 
                    <div class='row'>
                        
                    <div class='col-md-1' ></div>
                    <div class='col-md-6' >
                        <p>Page de l'équipe<p>
                    </div>
                    <div class='col-md-5' >
                        <div class='col-sm-6' ><p>Pointage Courant</p><p>58 pts</p></div>

                        <div class='col-sm-6' ><p>Rang</p><p>#8</p></div>
                    </div>
                    </div>
                </div>
            
            </div>
        </div>
        
        <div class='container'>
            <div class='col-md-1'></div>
            
            <div class='col-lg-10 offset-md-3' style='margin-top: 15px'>
                <div class="panel panel-default">
                    <div class="panel-heading" style="background-color: #253849; color: #e9e9e9;">Performance</div>
                    <div class="panel-body">Panel Content</div>
                </div>
                
                <table class='table table-hover' style="background-color: rgb(255, 255, 255); border:1px lightgray solid">
                  <thead>
                    <tr>
                      <th>Prénom</th>
                      <th>Nom</th>
                      <th>Contributions</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>John</td>
                      <td>Doe</td>
                      <td>
                          <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="50"
                            aria-valuemin="0" aria-valuemax="100" style="width:50%">
                          </div>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <td>Mary</td>
                      <td>Moe</td>
                      <td>
                          <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="10"
                            aria-valuemin="0" aria-valuemax="100" style="width:10%">
                            </div>
                          </div>
                      </td>
                    </tr>
                    <tr>
                      <td>July</td>
                      <td>Dooley</td>
                      <td>
                          <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="40"
                            aria-valuemin="0" aria-valuemax="100" style="width:40%">
                            </div>
                          </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
                
            </div>
        </div>
            
   
    </body>
</html>
