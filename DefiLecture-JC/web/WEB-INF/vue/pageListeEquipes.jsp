<%-- 
    Document   : pageListeEquipes
    Created on : 2017-10-24, 20:46:22
    Author     : Joel
--%>

<%@page import="com.defiLecture.modele.DemandeEquipeDAO"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
    
<jsp:useBean id="daoEquipe" scope="page" class="com.defiLecture.modele.EquipeDAO">
    <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" scope="page" class="com.defiLecture.modele.CompteDAO">
    <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoDemEq" scope="page" class="com.defiLecture.modele.DemandeEquipeDAO">
    <jsp:setProperty name="daoDemEq" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<c:set var="compteConnecte" value="${daoCompte.read(sessionScope.connecte)}"/>
<c:set var="listeEquipes" value="${daoEquipe.findAll()}"/>
    
<div class="row liste-equipes-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 liste-equipes-col">
            <h2>Liste des équipes</h2>  

            <table class="table">
              <thead>
                <tr>
                  <th>Nom</th>
                  <th>Doublons</th>
                  <th>État</th>
                </tr>
              </thead>

              <c:set var="i" value="0"/>          
              <tbody>
              <c:forEach items="${listeEquipes}" var="equipe">          
                <tr>
                  <c:if test="${(compteConnecte.idEquipe eq -1) and (equipe.nbMembres lt 3)}">
                  <td><a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
                  <td>${equipe.point} points</td>
                   <td>
                    <c:set var="demande" value="${daoDemEq.findByIdCompteEquipe(compteConnecte.idCompte,equipe.idEquipe)}"/>          
                    <c:choose>

                    <c:when test="${empty demande or demande.statutDemande eq 0}">
                       <a href="demande.do?tache=effectuerDemandeAdhesionEquipe&idCompte=${compteConnecte.idCompte}&idEquipe=${equipe.idEquipe}">
                           Envoyer une demande d'adhésion
                       </a>
                    </c:when>
                    <c:otherwise>
                        <span>Envoyée</span>
                    </c:otherwise>
                    </c:choose>

                   </td>    
                  </c:if>
                </tr>
              </c:forEach>  
              </tbody>

            </table>
    </div>
</div>
