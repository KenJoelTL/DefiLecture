<%-- 
    Document   : pageInscriptionDefi
    Created on : 2017-10-24, 17:34:03
    Author     : Charles
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defiLecture.modele.Defi"%>
<%@page import="com.defiLecture.modele.DefiDAO"%>
<%@page import="jdbc.Connexion"%>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defiLecture.modele.DefiDAO">
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
        <h2>${d.nom}</h2>
  
        <div >      
                <label for="description">Défi à relever : </label>
                <textarea id="description" disabled class="form-control" name="description" rows="15" style="max-width:80%; resize:none">${d.description}</textarea>
        </div>
        
        <div>
                <label for="question">Question à répondre : </label>
                <p id="question"> ${d.question} </p>
        </div>
          
       <div>
            <form action="*.do" method="post">
                <label for="listeChoixReponse">Choix de réponse : </label>
                <div id="listeChoixReponse"></div>
                    <div>
                        <input type="hidden" name="tache" value="effectuerInscriptionDefi">
                        <input type="hidden" name="idDefi" value="${d.idDefi}">

                        <input type="submit" name="valider" value=" Valider ma réponse" />
                        <input type="submit" name="annule" value=" Annuler" />
                    </div>
                    
            </form>

        </div>
  
    </body>