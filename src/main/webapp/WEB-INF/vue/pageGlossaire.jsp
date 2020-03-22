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
    Document   : pageGlossaire
    Created on : 2018-03-18, 15:26:36
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Glossaire</title>
    </head>
    <body>

        <div class='row creation-lecture-row'> 

            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
                <div class="creation-lecture-form glossaire">
                    <% out.println(application.getAttribute("txtGlossaire"));%>
                </div>
            </div>
        </div>
    </body>
</html>
