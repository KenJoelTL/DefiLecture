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
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


 <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
 <jsp:useBean id="dao" class="com.defiLecture.modele.EquipeDAO">
     <jsp:setProperty  name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
 </jsp:useBean>
 <c:set var="listeEquipes" value="${dao.findAll()}"></c:set>

        <div class="row scores-row">
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col">
                <h1>La trésorerie</h1>  

                    <table class="table">
                      <thead>
                        <tr>
                          <th>Rang</th>
                          <th>Nom de l'équipe</th>
                          <th style="text-align:center">Doublons</th>
                        </tr>
                      </thead>

                      <tbody>
                      <c:forEach items="${listeEquipes}" var="equipe">          
                      <c:set var="i" value="${i+1}"/>          
                        <tr>
                          <td>${i}</td>
                          <td><a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
                          <td style="text-align:center">${equipe.point}</td>
                        </tr>
                      </c:forEach>  
                      </tbody>

                    </table>
            </div>
        </div> 