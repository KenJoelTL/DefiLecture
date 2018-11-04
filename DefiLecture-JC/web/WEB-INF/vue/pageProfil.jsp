<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
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
    Document   : pageProfil
    Created on : 2017-10-22, 08:26:14
    Author     : Charles
--%>
<%@ page pageEncoding="UTF-8" %>
<% 
    CompteDAO dao = new CompteDAO();
    //dao.read("3");
    String id = request.getParameter("id");
    Compte comp = dao.read(id);
%>
<html>
    <head>
        <title>Profil </title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body style='background-color:grey;'>
        <div class='row' style='background-color:white; margin-top: 3em;'>
            <h2>Profil :</h2> <!-- ecrire Mon profile si cest le profile du user -->
            <div class='col-lg-6'>
                <p class='bold'>Prénom :</p>
                <p><% out.println(comp.getPrenom()); %></p>
                <p class='bold'>Nom :</p>
                <p></p>
                <p class='bold'>Courriel : </p> <!-- tcheker que cest son profil -->
                <p></p>
            </div>
            <div class='col-lg-6'>
                <p class='bold'>Département :</p>
                <p></p>
                <p class='bold'>Liste des lectures :</p>
                <p></p>
            </div>
        </div>
    </body>
</html>

