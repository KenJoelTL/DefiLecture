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
    Document   : pageTableauScores
    Created on : 2017-10-27, 21:22:09
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


 <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
 <jsp:useBean id="daoEquipe" class="com.defilecture.modele.EquipeDAO">
    <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
 </jsp:useBean>
 <jsp:useBean id="daoCompte" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
 </jsp:useBean>
 <jsp:useBean id="daoDemEqp" class="com.defilecture.modele.DemandeEquipeDAO">
    <jsp:setProperty name="daoDemEqp" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
 <c:set var="listeEquipes" value="${daoEquipe.findAll()}"></c:set>
 <c:if test="${not empty param.recherche}">
     <c:set var="listeEquipes" value="${daoEquipe.findAllByNom(param.recherche)}"></c:set>
     <c:set var="listeComptes" value="${daoCompte.findAllByNom(param.recherche)}"></c:set>
 </c:if>

        <div class="row scores-row">
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col">
                <h1>La trésorerie</h1>
                    <form method="post" style="text-align:center;margin-top:30px;margin-bottom:40px;">
                        <input type="text" name="recherche" style="width:45%;height:30px;font-size:125%;position:relative;left:15px;" maxlength="60" value="${param.recherche}">
                        <button title="Explorer!" style="padding:0px;margin-top:15px;height:33px;background:none;border:none;position:relative;top:-3px;left:20px"><img src="./images/victorian_search_icon2.png" height="100%"></button>
                    </form>
                    
                    <h3>Équipages</h3>
                    <table class="table">
                      <thead>
                        <tr>
                          <th>Rang</th>
                          <th>Nom <% out.println(application.getAttribute("vocEquipe1"));%> </th>
                          <th style="text-align:center">Doublons</th>
                        </tr>
                      </thead>

                      <tbody>
                      <c:forEach items="${listeEquipes}" var="equipe">          
                      <c:set var="i" value="${i+1}"/>          
                        <tr>
                          <td>${daoEquipe.findAll().indexOf(equipe)+1}</td>
                          <td><a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
                          <td style="text-align:center">${equipe.score}</td>
                        </tr>
                      </c:forEach>
                      </tbody>
                    </table>
                    <c:if test="${empty listeEquipes}">
                        <div style="text-align:center;font-family:'Goudy Bookletter 1911',serif;">
                            (Aucun résultat à afficher)
                        </div>
                    </c:if>
                    
                    <c:if test="${not empty param.recherche}">
                        <h3>Pirates</h3>
                        <table class="table">
                            <thead>
                                <tr>
                                    <th width=50%>Pirate</th>
                                    <th>Contribution</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach items="${listeComptes}" var="compte">
                                <tr>
                                    <td><a href="pageEquipe.do?tache=afficherPageProfil&id=${compte.idCompte}">${compte.prenom} «${compte.pseudonyme}» ${compte.nom}</a></td>
                                    <td>
                                        <c:set var="equipe" value="${daoEquipe.read(compte.idEquipe)}"/>
                                        <c:set var="contribution" value="${daoDemEqp.findByIdCompteEquipe(compte.idCompte,equipe.idEquipe)}"></c:set>
                                        <div class="progress">
                                            <div class="progress-bar" role="progressbar" aria-valuenow="${contribution.point}"
                                                aria-valuemin="0" aria-valuemax="100" style="width:${(contribution.point/equipe.score)*100}%">
                                                ${contribution.point}
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${empty listeComptes}">
                            <div style="text-align:center;font-family:'Goudy Bookletter 1911',serif;">
                                (Aucun résultat à afficher)
                            </div>
                        </c:if>
                    </c:if>
            </div>
        </div> 
