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
    Document   : adresseCourriel
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
       
        <h2>Liste d'adresses</h2>  
        
                <p>
                  <c:set var="listeComptes" value="${dao.findAll()}"/>
                <c:forEach items="${listeComptes}" var="compte">
         
                        ${compte.courriel} <br>
          
                </c:forEach>
                </p>
    </div>
</div>
