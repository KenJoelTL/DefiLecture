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
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Equipe"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<script language="javascript" src="./script/jsPageGestionEquipe.js"></script>

<div class="row gestion-lecture-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 gestion-lecture-col">
        <h1>&Eacute;quipes</h1>

  <jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
  <jsp:useBean id="daoEquipe" scope="page" class="com.defilecture.modele.EquipeDAO">
      <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
  </jsp:useBean>
  <jsp:useBean id="daoCompte" scope="page" class="com.defilecture.modele.CompteDAO">
      <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
  </jsp:useBean>
  
  <c:set var="equipes" value="${daoEquipe.findAll()}"/>
            <table class="table cacherSurMobile">
                <thead>
                <tr>
                  <th>Nom</th>
                  <th>Nombre de points</th>
                  <th>Nombre de membres</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${equipes}" var="equipe">
                    <tr id="lecture-${equipe.getIdEquipe()}">
                      <td>${equipe.getNom()}</td>
                      <td>${equipe.getPoint()}</td>
                      <td>${equipe.getNbMembres()} </td>
                      <td><a id="afficherMembres" href-"*.do?tache=afficherGestionEquipe?idEquipe=${equipe.getIdEquipe()}">Modifier</a></td>
                      <td><a id="supprimer" onclick="supprimer(${equipe.getIdEquipe()})">Supprimer</a></td>
                    </tr>
                </c:forEach>
              </tbody>
            </table>
    
    </div>
</div>

      


