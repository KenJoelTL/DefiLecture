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
<%-- 
    Document   : pageInscription
    Created on : 2017-10-15, 17:49:43
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Page d'inscription</title>
    </head>
    <body>
        <div class='row inscription-row'> 
                <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 inscription-col"> 
                    <c:if test="${!empty requestScope.data['erreurInscription']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurInscription']}</strong></div>
                    </c:if>
                    <h1>S'inscrire</h1>
                   
                    <c:if test="${!empty requestScope.data['erreurDates']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurDates']}</strong></div>
                    </c:if>
                    <c:if test="${!empty requestScope.data['erreurPrenom']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurPrenom']}</strong></div>
                    </c:if>
                    <c:if test="${!empty requestScope.data['erreurNom']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurNom']}</strong></div>
                    </c:if>
                    <c:if test="${!empty requestScope.data['erreurCourriel']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurCourriel']}</strong></div>
                    </c:if>
                    <c:if test="${!empty requestScope.data['erreurPseudonyme']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurPseudonyme']}</strong></div>
                    </c:if>
                    <c:if test="${!empty requestScope.data['erreurMotPasseIdentique']}">
                        <div class="alert alert-danger"><strong>${requestScope.data['erreurMotPasseIdentique']}</strong></div>
                    </c:if>
                    <form class="inscription-form" action="inscription.do" method="post">
                        <strong><p>* champs obligatoires</p></strong>
                        <div class="form-group">
                            <label for="prenom">Prénom* :</label>
                           
                            <div class="input-group">
                                <input id="prenom" type="text" class="form-control" name="prenom" value="${requestScope.data['prenom']}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nom">Nom* :</label>
                           
                            <div class="input-group">
                                <input id="nom" type="text" class="form-control" name="nom" value="${requestScope.data['nom']}" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="programmeEtude">Programme d'étude ou poste occupé au collège :</label>
                            <div class="input-group">
                                <input id="programmeEtude" type="text" class="form-control" name="programmeEtude" value="${requestScope.data['programmeEtude']}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="courriel">Courriel* :</label>
                            
                            <div class="input-group">
                                <input id="courriel" type="email" class="form-control" name="courriel" value="${requestScope.data['courriel']}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pseudonyme">Pseudonyme :</label>
                            
                            <div class="input-group">
                                <input id="pseudonyme" type="text" class="form-control" name="pseudonyme" value="${requestScope.data['pseudonyme']}">
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
                        
                        <div class="form-group">
                             <label for="devenirCapitaine"><input id="chkDevenirCapitaine" type="checkbox" name="devenirCapitaine" value="1">Je désire être <% out.println(application.getAttribute("vocChef"));%> <% out.println(application.getAttribute("vocEquipe2"));%> (employé ou tuteur)</label>
                        </div> 
                        
                        <input type="hidden" name="tache" value="effectuerInscription">
               
                       <button type="submit" class="btn btn-danger btn-inscription">S'INSCRIRE</button>
                    </form>
    
                </div>
       </div>
    </body>
</html>
