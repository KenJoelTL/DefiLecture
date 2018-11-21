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
    Document   : pageGestionConfigurationEquipe
    Created on : 2017-11-01, 20:52:35
    Author     : Joel
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.defiLecture.modele.DemandeEquipeDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Equipe"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Connexion"%>
<%@page import="jdbc.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- Faire la connexion -->
<jsp:useBean id="connexion" class="jdbc.Connexion"/>

<!-- Cree les DAOs -->
<jsp:useBean id="compteDao" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty name="compteDao" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>
<jsp:useBean id="equipeDao" class="com.defiLecture.modele.EquipeDAO">
    <jsp:setProperty name="equipeDao" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>
<jsp:useBean id="demEquipeDao" class="com.defiLecture.modele.DemandeEquipeDAO">
    <jsp:setProperty name="demEquipeDao" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>

<!--Initier les variables -->
<jsp:useBean id="equipe" class="com.defiLecture.modele.Equipe" scope="page"/>
<jsp:useBean id="compteConnecte" class="com.defiLecture.modele.Compte" scope="page"/>

<!-- Assigner la valeur aux variables -->
<c:set var="compteConnecte" value="${compteDao.read(sessionScope.connecte)}"/>

<!-- Verifier que son equipe ne soit pas null-->
<c:choose>
    <c:when test="${!empty param.idEquipe}">
        <c:set var="equipe" value="${equipeDao.read(param.idEquipe)}"/>
    </c:when>
    <c:otherwise>
        <c:set var="equipe" value="${equipeDao.read(compteConnecte.getIdEquipe())}"/>
    </c:otherwise>
</c:choose>

<!-- Ajouter l'equipe dans les variables -->
<c:set var="listeMembres" value="${compteDao.findByIdEquipe(equipe.getIdEquipe())}"/>
<c:set var="nbMembres" value="${listeMembres.size()}"/>

<!-- Reste du code -->
<c:set var="permissionAccordee" value="${((sessionScope.role eq Compte.CAPITAINE) 
                                    and (compteConnecte.idEquipe eq equipe.idEquipe)) or (sessionScope.role eq Compte.ADMINISTRATEUR)}"></c:set>
        <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 page-equipe-col configuration-equipe">
                <h1>Configuration de l'&eacute;quipage</h1>
                <c:if test="${!empty requestScope.data['succesNom']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['succesNom']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurNom']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurNom']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurModification']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurModification']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['succesSuspension']}">
                    <div class="alert alert-success"><strong>${requestScope.data['succesSuspension']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurSuspension']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurSuspension']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['succesReafectation']}">
                    <div class="alert alert-success"><strong>${requestScope.data['succesReafectation']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurSuspension']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurSuspension']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['succesRetrait']}">
                    <div class="alert alert-success"><strong>${requestScope.data['succesRetrait']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurRetrait']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurRetrait']}</strong></div>
                </c:if>
                <form action="modificationEquipe.do" method="post">
                    <div class="form-group">
                        <label for="nom">Nom de l'&eacute;quipage* :</label>
                        <input type="text" class="form-control" id="nom" value="${equipe.nom}" 
                               placeholder="Entrez le nouveau nom de votre &eacute;quipage" name="nom" ${permissionAccordee ? 'required': 'disabled'}/>
                    </div>
                    <c:if test="${permissionAccordee}">
                        <input type="hidden" name="tache" value="effectuerModificationEquipe">
                        <input type="hidden" name="idEquipe" value="${equipe.idEquipe}">
                        <button type="submit" class="btn btn-success" name='modifier' value="Enregistrer">Enregistrer</button>
                    </c:if> 
                </form>
        
                <table class='table table-hover cacherSurMobile' >
                    <thead>
                        <tr>
                            <th>Prénom</th>
                            <th>Nom</th>
                            <th>Contributions</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listeMembres}" var="membre">      
                        <tr>
                            <td>${membre.prenom}</td>
                            <td>${membre.nom}</td>
                            <td>
                                <c:set var="contribution" value="${demEquipeDao.findByIdCompteEquipe(membre.idCompte,equipe.idEquipe)}"></c:set>
                                <div class="progress">
                                  <div class="progress-bar" role="progressbar" aria-valuenow="${contribution.point}"
                                       aria-valuemin="0" aria-valuemax="100" style="width:${(contribution.point/equipe.point)*100}%">
				    ${contribution.point}
                                  </div>
                                </div>
                            </td> 
                            <c:if test="${permissionAccordee and (sessionScope.connecte ne membre.idCompte)}"> 
                            <td>
                                <td>
                                    <c:choose>
                                        <c:when test="${contribution.statutDemande eq 0}">                             
                                            <a href="*.do?tache=effectuerReaffectationMembreEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">R&eacute;afecter &agrave; l'&eacute;quipage</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="*.do?tache=effectuerSuspensionMembreEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">Suspendre de l'&eacute;quipage</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                </c:if>
                                <c:if test="${permissionAccordee and (sessionScope.connecte ne membre.idCompte)}">
                                <td>
                                    <a href="depart.do?tache=effectuerDepartEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">
                                        Retirer de l'équipage
                                    </a>
                                </td>
                            </td>
                            </c:if>
                        </tr>
                        </c:forEach>
                    </tbody>
                </table>
        
        <a href="*.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la page d'équipe</a>
        
            </div>
        </div>

