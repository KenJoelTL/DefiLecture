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
    Document   : pageDefisReussis
    Created on : 2018-04-15, 09:23:25
    Author     : Charles
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  

<jsp:useBean id="daoDefi" scope="page" class="com.defilecture.modele.DefiDAO">
    <jsp:setProperty name="daoDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoInscriptionDefi" scope="page" class="com.defilecture.modele.InscriptionDefiDAO">
    <jsp:setProperty name="daoInscriptionDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" scope="page" class="com.defilecture.modele.CompteDAO">
    <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<div class="row listeCompte-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 listeCompte-col">
       
        <h2>Liste des défis réussis</h2>  
        
                <p>
                <c:set var="listeInscriptionDefi" value="${daoInscriptionDefi.findByDefiReussi()}"/>
                <c:forEach items="${listeInscriptionDefi}" var="inscription">
                    <c:set var="defi" value="${daoDefi.read(inscription.idDefi)}"/>
                    <c:if test="${ defi.idDefi != defiTemp }">
                        <h3>${defi.nom}</h3>
                    </c:if>
                    <c:set var="defiTemp" value="${inscription.idDefi}"/>
                    
                     <c:set var="compte" value="${daoCompte.read(inscription.idCompte)}"/>

                         ${compte.courriel}
                         <br>
          
                </c:forEach>
                </p>
    </div>
</div>
