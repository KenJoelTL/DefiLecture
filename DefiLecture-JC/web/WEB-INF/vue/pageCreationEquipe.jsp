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
    Document   : pageCreationEquipe
    Created on : 2017-10-26, 00:10:33
    Author     : Joel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div class='row creation-lecture-row'> 
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
                <h1>Cr&eacuteation d'une equipe</h1>
                <c:if test="${!empty requestScope.data['erreurNom']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurNom']}</strong></div>
                </c:if>
                <form action="creationEquipe.do" method="post">
                    <div class="form-group">
                        <label for="nom">Nom de l'&eacute;quipe* :</label>
                        <input type="text" class="form-control" id="nom" placeholder="Entrez le nom de votre nouvelle  &eacute;quipe" name="nom" required/>
                    </div>
                    <input type="hidden" name="tache" value="effectuerCreationEquipe">
                    <button type="submit" class="btn btn-success">CRÉER</button>
                </form>
            </div>
        </div>
    </body>
</html>
