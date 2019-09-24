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
    Document   : profilEquipe
    Created on : 2017-10-14
    Author     : Joel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defilecture.modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="com.defilecture.modele.Equipe"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>


    <c:if test="${empty param.idEquipe}">${param.idEquipe=1}</c:if>

    <jsp:useBean id="connexion" scope="page"  class="jdbc.Connexion"></jsp:useBean>
    <c:set var="cnx" value="${connexion.connection}"/>
    
    <c:if test="${empty cnx}">
        <c:redirect url="redirection.do?tache=afficherPageAccueil"></c:redirect>
    </c:if>
    
    <jsp:useBean id="daoEquipe" scope="page"  class="com.defilecture.modele.EquipeDAO">
        <jsp:setProperty name="daoEquipe" property="cnx" value="${cnx}"></jsp:setProperty>
    </jsp:useBean>

    <jsp:useBean id="daoDemEqp" scope="page"  class="com.defilecture.modele.DemandeEquipeDAO">
        <jsp:setProperty name="daoDemEqp" property="cnx" value="${cnx}"></jsp:setProperty>
    </jsp:useBean>
    
    <jsp:useBean id="daoCompte" scope="page"  class="com.defilecture.modele.CompteDAO">
        <jsp:setProperty name="daoCompte" property="cnx" value="${cnx}"></jsp:setProperty>
    </jsp:useBean>
    
    <c:if test="${!empty sessionScope.connecte}">
        <c:set var="compteConnecte" value="${daoCompte.read(sessionScope.connecte)}"></c:set>
    </c:if>
    
    <c:set var="equipe" value="${daoEquipe.read(param.idEquipe)}"/>
    <c:set var="rang" value="${daoEquipe.findAll().indexOf(equipe)+1}"/>
    <c:set var="listeMembres" value="${daoCompte.findByIdEquipe(equipe.idEquipe)}"/>

    
    <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 page-equipe-col">
 
                <div class='jumbotron'> 
                    
                    <h1>${equipe.nom}</h1>
                    <c:if test="${equipe.point eq 0}">
                        <p>${equipe.point} <% out.println(application.getAttribute("vocPoint"));%></p>
                    </c:if>
                    <c:if test="${equipe.point ge 1}">
                        <p>${equipe.point} <% out.println(application.getAttribute("vocPoints"));%></p>
                    </c:if> 
                    <p>Rang ${rang}</p>
             
                </div>
                 
                <c:if test="${(!empty sessionScope.connecte) and (compteConnecte.idEquipe eq equipe.idEquipe) and ((sessionScope.role eq 2) or (sessionScope.role eq 4))}">
                    <a id="parametreEquipe" href="depart.do?tache=afficherPageModificationEquipe&idEquipe=${equipe.idEquipe}">
                        Paramètres <span class="glyphicon glyphicon-cog"></span>
                    </a>
                </c:if>
                    
                    <h2>Membres de  <% out.println(application.getAttribute("vocEquipe1"));%> </h2>

                    <table class='table table-hover'>
                  <thead>
                    <tr>
	              <th witdh=50/>
                      <th width=50%><% out.println(application.getAttribute("vocParticipant"));%></th>
                      <th>Contribution</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${listeMembres}" var="membre">      
                    <tr>
			    <td><img class="imgProfil Petite" src="<c:url value='${membre.avatar}'/>"></td>
                    <td><a href="?tache=afficherPageProfil&id=${membre.idCompte}">${membre.prenom} «${membre.pseudonyme}» ${membre.nom}</a></td>
                      <td>
                          <c:set var="contribution" value="${daoDemEqp.findByIdCompteEquipe(membre.idCompte,equipe.idEquipe)}"></c:set>
                        <div class="progress">
                          <div class="progress-bar" role="progressbar" aria-valuenow="${contribution.point}"
                               aria-valuemin="0" aria-valuemax="100" style="width:${(contribution.point/equipe.point)*100}%">
			    ${contribution.point}
                          </div>
                        </div>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>

               </div>
        </div>
            
   

