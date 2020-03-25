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
Document   : index
Created on : 2017-10-14, 12:23:05
Author     : Joel & Charles
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defilecture.modele.Theme"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${ !empty sessionScope.connecte}">
    <% CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("compteConnecte", dao.read(session.getAttribute("connecte").toString())); %>
</c:if> 

<%
Theme t= new Theme();
Iterator<Map.Entry<String, String>> it = t.getTheme().entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<String, String> pair = it.next();
    application.setAttribute(pair.getKey(), pair.getValue());
}
%>  
<!DOCTYPE html>
<!-- Layout -->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Défi Lecture</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/styles.css">
        <link rel="stylesheet" href="css/defiLectureStyles.css" type="text/css"> </head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-fr_FR.min.js"></script>
    </head>
    <body >
	<%@include file="menu.jsp" %>
        <div class="container">
            <c:choose>
                <c:when test="${ !empty requestScope.vue }">
                    <c:set var="vue" value="/WEB-INF/vue/${requestScope.vue}"/>
                    <jsp:include page="${vue}" ></jsp:include>
                </c:when>
                <c:otherwise>
                    <jsp:include page="/WEB-INF/vue/pageMarcheASuivre.jsp" ></jsp:include>
                </c:otherwise>
            </c:choose>
        </div> 
    </body>
</html>
