<%-- 
    Document   : adresseCourriel
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defiLecture.modele.CompteDAO">
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
