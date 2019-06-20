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
    Document   : pageInscriptionDefi
    Created on : 2017-10-24, 17:34:03
    Author     : Charles
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defilecture.modele.Defi"%>
<%@page import="com.defilecture.modele.DefiDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defilecture.modele.DefiDAO">
    <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<c:set var="d" scope="page" value="${dao.read(param.id)}"/>
<c:set var="choixReponse" scope="page" value="${d.choixReponse}"/>
  <script>
       $(document).ready(function(){
          var value = ${choixReponse};
          var s="";
          //Boucle pour afficher les choix de réponses en bouton radio
          for(i=0; i<value.length; i++ ){
             s += "<div class=\"radio\">" +
                        "<label><input type=\"radio\" name=\"reponseParticipant\" value=\""+i+"\" required >"+value[i]+"</label>"+
                    "</div>";
            }
            $("#listeChoixReponse").append(s);

       });
  </script>

    <body>
        <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 inscription-defi-col">
                <div class="inscription-defi-form">
                    <h2>${d.nom}</h2>

                    <div >      
                            <label for="description">Défi à relever : </label>
                            <textarea id="description" disabled class="form-control" name="description" rows="15" style="max-width:80%; resize:none">${d.description}</textarea>
                    </div>

                    <div>
                            <label for="question">Question à répondre : </label>
                            <p id="question" class="form-control"> ${d.question} </p>
                    </div>

                   <div>
                        <form action="*.do" method="post">
                            <div class="choixReponse">
                            <label for="listeChoixReponse">Choix de réponse : </label>
                            <div id="listeChoixReponse"></div>
                            </div>
                                <div>
                                    <input type="hidden" name="tache" value="effectuerInscriptionDefi">
                                    <input type="hidden" name="idDefi" value="${d.idDefi}">
                                    
                                    <button type="submit" class="btn btn-success" name="valider" >Valider ma réponse</button>
                                    <a href="*.do?tache=afficherPageParticipationDefi" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des défis</a>
                                     
                                </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </body>
