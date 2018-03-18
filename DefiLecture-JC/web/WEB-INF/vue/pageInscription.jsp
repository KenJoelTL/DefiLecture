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
        <title>Page D'inscription</title>
    </head>
    <body>
        <div class='row'> 
                <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 inscription-col"> 
                    <h1>Inscription</h1>
                    <form class="inscription-form" action="inscription.do" method="post">
                        <div class="form-group">
                            <label for="prenom">Prénom* :</label>
                            <c:if test="${!empty requestScope.data['erreurPrenom']}">
                              <div class="alert alert-danger"><strong>${requestScope.data['erreurPrenom']}</strong></div>
                            </c:if>
                            <div class="input-group">
                                <input id="prenom" type="text" class="form-control" name="prenom" value="${requestScope.data['prenom']}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="nom">Nom* :</label>
                            <c:if test="${!empty requestScope.data['erreurNom']}">
                            <div class="alert alert-danger"><strong>${requestScope.data['erreurNom']}</strong></div>
                            </c:if>
                            <div class="input-group">
                                <input id="nom" type="text" class="form-control" name="nom" value="${requestScope.data['nom']}" required>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="programmeEtude">Programme d'étude :</label>
                            <div class="input-group">
                                <input id="programmeEtude" type="text" class="form-control" name="programmeEtude" value="${requestScope.data['programmeEtude']}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="courriel">Courriel* :</label>
                            <c:if test="${!empty requestScope.data['erreurCourriel']}">
                            <div class="alert alert-danger"><strong>${requestScope.data['erreurCourriel']}</strong></div>
                            </c:if>
                            <div class="input-group">
                                <input id="courriel" type="email" class="form-control" name="courriel" value="${requestScope.data['courriel']}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pseudonyme">Pseudonyme :</label>
                            <c:if test="${!empty requestScope.data['erreurPseudonyme']}">
                            <div class="alert alert-danger"><strong>${requestScope.data['erreurPseudonyme']}</strong></div>
                            </c:if>
                            <div class="input-group">
                                <input id="pseudonyme" type="text" class="form-control" name="pseudonyme" value="${requestScope.data['pseudonyme']}">
                            </div>
                        </div>
                            <c:if test="${!empty requestScope.data['erreurMotPasseIdentique']}">
                            <div class="alert alert-danger"><strong>${requestScope.data['erreurMotPasseIdentique']}</strong></div>
                            </c:if>
                        <div class="form-group">
                            <label for="motPasse">Mot de passe (jusqu'à 12 caratères)* :</label>
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
