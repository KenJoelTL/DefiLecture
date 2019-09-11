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
    Document   : pageMarcheASuivre
    Created on : 2018-03-21, 07:34:53
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">   
        <title>Marche à suivre</title>
    </head>
    <body>

        <div class="row creation-lecture-row">

            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col marcheASuivre">
               <% out.println(application.getAttribute("txtMarcheASuivre"));%>
                    <div class='divPetiteIcone'>
                    <label class='petiteIconeMarcheASuivre'></label>
                    </div>
         
                </div>
            </div>
        </div>
    </body>
</html>
