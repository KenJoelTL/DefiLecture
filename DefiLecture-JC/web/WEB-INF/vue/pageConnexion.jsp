<%-- 
    Document   : pageConnexion
    Created on : 2017-10-15
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="javascript" src="./script/jquery-1.4.2.min.js"></script>
        <script language="javascript" src="./script/connexion.js"></script>
        <title>Page de connexion</title>
    </head>
    <body class="connexion-body">
        <div class='row'> 

        <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 connexion-col"> 
           <h1>Connexion</h1>
            <c:if test="${!empty requestScope.data['succesInscription']}">
                <div class="alert alert-success"><strong>${requestScope.data['succesInscription']}</strong></div>
            </c:if>
            <c:if test="${!empty requestScope.data['echecConnexion']}">
                <div class="alert alert-danger"><strong>${requestScope.data['echecConnexion']}</strong></div>
            </c:if>
        <form class="connexion-form" action="connexion.do" method="post">
            <div class="form-group">
                <label for="identifiant">Courriel ou pseudonyme:</label>
                <div class="input-group">
          
                    <input id="identifiant" type="text" class="form-control" name="identifiant" value="${requestScope.data['identifiant']}">
                </div>
            </div>
            
            <div class="form-group">
                <label for="motPasse">Mot de passe :</label>                
                <div class="input-group">
                    
                    <input id="motPasse" type="password" class="form-control" name="motPasse"  >
                </div>
            </div>
            <a id="lienMDPoublie" href="#">
                Mot de passe oublié? <span id="spanMDP" class="glyphicon glyphicon-menu-down"></span>
            </a>
            <p id="msgMDPoublie">Si vous avez oublié votre mot de passe, veuillez contacter l'administrateur de l'application.</p>
            <input type="hidden" name="tache" value="effectuerConnexion">
            <button type="submit" class="btn btn-danger">À L'ABORDAGE!</button>
            
        </form>
      </div>
       
            </div>
    </body>
</html>
