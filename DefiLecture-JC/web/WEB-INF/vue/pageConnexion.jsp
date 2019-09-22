<!--
     This file is part of DefiLecture.

     DefiLecture is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     DefiLecture is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with DefiLecture.  If not, see <http://www.gnu.org/licenses/>.
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="./script/jquery-1.4.2.min.js"></script>
    <script src="./script/connexion.js"></script>
    <title>Page de connexion</title>
  </head>
  <body class="connexion-body">
    <div class='row connexion-row'>

      <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 connexion-col">
	<h1>Se connecter</h1>
	<c:if test="${!empty requestScope.data['succesInscription']}">
	  <div class="alert alert-success">
	    <strong>${requestScope.data['succesInscription']}</strong>
	  </div>
	</c:if>
	<c:if test="${!empty requestScope.data['echecConnexion']}">
	  <div class="alert alert-danger">
	    <strong>${requestScope.data['echecConnexion']}</strong>
	  </div>
	</c:if>
	<form class="connexion-form" action="connexion.do" method="post">
	  <div class="form-group">
	    <label for="identifiant">Courriel ou pseudonyme :</label>
	    <div class="input-group">
	      <input id="identifiant" type="text" class="form-control"
		     name="identifiant" value="${requestScope.data['identifiant']}">
	    </div>
	  </div>

	  <div class="form-group">
	    <label for="motPasse">Mot de passe :</label>
	    <div class="input-group">

	      <input id="motPasse" type="password" class="form-control"
		     name="motPasse">
	    </div>
	  </div>
	  <a id="lienMDPoublie" href="#"> Mot de passe oublié? <span
								   id="spanMDP" class="glyphicon glyphicon-menu-down"></span>
	  </a>
	  <p id="msgMDPoublie">Veuillez contacter l'administrateur de
	    l'application pour qu'il puisse réinitialiser votre mot de passe.</p>
	  <input type="hidden" name="tache" value="effectuerConnexion">
	  <button type="submit" class="btn btn-danger">
	    <% out.println(application.getAttribute("vocConnexion"));%>
	  </button>

	</form>
      </div>

    </div>
  </body>
</html>
