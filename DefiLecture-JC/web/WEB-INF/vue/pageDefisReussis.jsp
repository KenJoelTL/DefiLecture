<%-- 
    Document   : pageDefisReussis
    Created on : 2018-04-15, 09:23:25
    Author     : Charles
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  

<jsp:useBean id="daoDefi" scope="page" class="com.defiLecture.modele.DefiDAO">
    <jsp:setProperty name="daoDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoInscriptionDefi" scope="page" class="com.defiLecture.modele.InscriptionDefiDAO">
    <jsp:setProperty name="daoInscriptionDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>
<jsp:useBean id="daoCompte" scope="page" class="com.defiLecture.modele.CompteDAO">
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