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
    Document   : pageListeDemandesEquipe
    Created on : 2017-10-28, 08:15:58
    Author     : Joel
--%>
<%@page import="com.defiLecture.modele.Equipe"%>
<%@page import="com.defiLecture.modele.DemandeEquipe"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.connecte or (!(sessionScope.role eq 2) and (requestScope.ordre eq 'recu'))}">
    <jsp:forward page="*.do?tache=afficherPageAccueil"></jsp:forward>
</c:if>


<%  Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DemandeEquipeDAO demandeEqDao = new DemandeEquipeDAO(cnx);
    EquipeDAO eqpDao = new EquipeDAO(cnx);

    if("recu".equals(request.getParameter("ordre"))){
        CompteDAO cptDao = new CompteDAO(cnx);
        Compte compte = new Compte();
        Equipe equipe = new Equipe();
        if(request.getParameter("idEquipe")==null){
            compte = cptDao.read((int)session.getAttribute("connecte"));
        }
        equipe = eqpDao.read(compte.getIdEquipe());
        pageContext.setAttribute("cptDao", cptDao);
        pageContext.setAttribute("equipe", equipe);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdEquipeStatutDemande(compte.getIdEquipe(),DemandeEquipe.EN_ATTENTE));
    }
    else{
        pageContext.setAttribute("equipeDao", eqpDao);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdCompte((int)session.getAttribute("connecte")));
    }
%>


 <div class="row demandes-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 demandes-col">
        <h2>Liste des demandes</h2>
        <c:if test="${!empty requestScope.data['erreurDemande']}">
            <div class="alert alert-danger"><strong>${requestScope.data['erreurDemande']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['succesDemande']}">
            <div class="alert alert-success"><strong>${requestScope.data['succesDemande']}</strong></div>
        </c:if>
        <c:if test="${!empty requestScope.data['avertissementDemande']}">
            <div class="alert alert-warning"><strong>${requestScope.data['avertissementDemande']}</strong></div>
        </c:if>
          <c:if test="${(param.ordre eq 'recu') and (sessionScope.role eq Compte.CAPITAINE) }">
              <c:choose>
                  <c:when test="${ equipe.nbMembres lt Equipe.NB_MAX_MEMBRES }">
              <table class="table">

              <thead>
                <tr>
                  <th>Demandes d'adh&eacute;sion</th>
                  <th>&Eacute;tat de la demande</th>
                  <th></th>
                </tr>
              </thead>

              <tbody>
               <c:forEach items="${listeDemandes}" var="demande">
                 <c:set var="auteur" value="${cptDao.read(demande.idCompte)}"/>
                 <tr>
                    <td>Demande envoy&eacute;e par ${auteur.prenom} ${auteur.nom}</td>
                    <td>
                        <a href="accepter.do?tache=effectuerAcceptationDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Accepter</a>
                        <a href="refuser.do?tache=effectuerSuppressionDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}&ordre=recu">Refuser</a>
                    </td>
                 </tr>
               </c:forEach>
              </tbody>

            </table>
              </c:when>
                  <c:otherwise>
                      <div class="alert alert-warning" style="text-align:center">Votre &eacute;quipage est plein<div>
                  </c:otherwise>
              </c:choose>
          </c:if>

        
      </div>
 </div>

