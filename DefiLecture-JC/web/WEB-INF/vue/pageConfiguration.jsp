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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>

<jsp:useBean id="connexion" scope="page"  class="jdbc.Connexion"></jsp:useBean>
    <c:set var="cnx" value="${connexion.connection}"/>
    
    <c:if test="${empty cnx || empty sessionScope.connecte || sessionScope.role <=2}">
        <c:redirect url="redirection.do?tache=afficherPageAccueil"></c:redirect>
    </c:if>
    
    <jsp:useBean id="daoConfig" scope="page"  class="com.defilecture.modele.ConfigSiteDAO">
        <jsp:setProperty name="daoConfig" property="cnx" value="${cnx}"></jsp:setProperty>
    </jsp:useBean>
    
<html>   
<head>
    <title>Configuration du défi-lecture</title>
</head>
    <body>
        <div class ="container" id="configSite"> 
            <div class="row">
                <div class="col-sm-10 col-md-10 col-lg-10">
                    <div class="panel panel-default" id="panelConfig">
                        <div class="panel-heading"><h2>Configuration :</h2></div>
                        <div class="panel-body">
                            <form action="configSite.do" method="post">
                                <h4>Accès :</h4>
                                <div class="input-daterange input-group dateConfig">
                                    <span class="input-group-addon">De:</span>
                                    <input type="datetime-local" id="accesDe" name="accesDe" class="input-sm form-control" value="<c:out value="${daoConfig.getString('accesDe')}" />"/>
                                    <span class="input-group-addon">à</span>
                                    <input type="datetime-local" id="accesA" name="accesA" class="input-sm form-control" value="<c:out value="${daoConfig.getString('accesA')}" />"/>
                                </div>
                                <h4>Inscription à partir:</h4>
                                <div class="input-daterange input-group dateConfig">
                                    <span class="input-group-addon">De :</span>
                                    <input type="datetime-local" id="dInscription" name="dInscription" class="input-sm form-control" value="<c:out value="${daoConfig.getString('dInscription')}" />"/>
                                </div>
                                <h4>Lectures :</h4>
                                <div class="input-daterange input-group dateConfig">
                                    <span class="input-group-addon">De :</span>
                                    <input type="datetime-local" id="dLecture" name="dLecture" class="input-sm form-control" value="<c:out value="${daoConfig.getString('dLecture')}" />"/>
                                    <span class="input-group-addon">à</span>
                                    <input type="datetime-local" id="fLecture" name="fLecture" class="input-sm form-control" value="<c:out value="${daoConfig.getString('fLecture')}" />"/>
                                </div>
                                <h4>Nombre de matelots :</h4>
                                <input id="nbMatelot" name="nbMatelot" class="form-control" type="text" value="<c:out value="${daoConfig.getString('nbmatelot')}" />" pattern="[-+]?[0-9]*[.,]?[0-9]+"/>
                                <input type="hidden" name="tache" value="effectuerModificationConfig"/>
                                <input type="submit" id="enregistrerConfig" class="btn btn-primary" value="Enregistrer"/>
                            </form>
                        </div>
                    </div>
                </div>
                 <div class="col-sm-10 col-md-10 col-lg-10">
                    <div class="panel panel-default" id="panelReset">
                        <div class="panel-heading"><h2>Réinitialisation de la base de données :</h2></div>
                        <div class="panel-body">
                            <c:choose> 
                                <c:when test="${sessionScope.role ==4}" > 
                                    <div>
                                        <input type="button" class="btn btn-danger" value="Réinitialiser" id="reset">
                                    </div>
                                    <form action="deleteBD.do" method="post">
                                        <div id='resetConfirm'>
                                            <div class='input-group'>
                                                <div class='row form-group'>
                                                    <h4 for="passwordConf">Mot de passe requis :</h4>
                                                    <input id="passwordConf" name="passwordConf" type="password" class='form-control' required>
                                                </div>
                                                <div class='row'>
                                                    <input type="hidden" name="tache" value="effectuerSupressionBD"/>
                                                    <input type="submit" class="btn btn-danger" value="Accepter">        
                                                    <input type="button" class="btn btn-danger" value="Annuler" id="resetCancel">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:when> 
                                <c:otherwise> 
                                    <div>
                                        <input type="button" class="btn btn-danger" value="Réinitialiser" id="reset" disabled>
                                    </div>
                                </c:otherwise> 
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div> 
        </div>
        <script>
            $(document).ready(function() {
                $("#resetConfirm").hide();
            });

            $("#reset").click(function(){
                $("#reset").hide();
                $("#resetConfirm").show();
            });
            $("#resetCancel").click(function(){
                $("#resetConfirm").hide();
                $("#reset").show();
            });
        </script>
    </body>
</html>
