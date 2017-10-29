<%-- 
    Document   : pageInscriptionDefi
    Created on : 2017-10-24, 17:34:03
    Author     : Charles
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modele.Defi"%>
<%@page import="modele.DefiDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<% 
    Connexion.reinit();
    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DefiDAO dao = new DefiDAO(cnx);
    Defi d = dao.read(request.getParameter("id"));
    String choixReponse = d.getChoixReponse();
    System.out.println(choixReponse);
    pageContext.setAttribute("d", d);
  %>

  <script>
       $(document).ready(function(){
          var value = <%=choixReponse%>;
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
        <h1>Inscription à un défi</h1>
        <div>
                Nom du défi: ${d.nom}
        </div>
        <div>      
                Description : ${d.description}
        </div>
        
        <div>
                Question à répondre : ${d.question}
        </div>
          
       <div>
            <form action="*.do" method="post">
                <h1>Choix de réponse</h1>
                <div id="listeChoixReponse"></div>
                    <div>
                        <input type="hidden" name="tache" value="effectuerInscriptionDefi">
                        <input type="hidden" name="idDefi" value="${d.idDefi}">
                        <input type="hidden" name="idCompte" value="${d.idCompte}">
                        <input type="submit" name="valider" value=" Valider ma réponse" />
                        <input type="submit" name="annule" value=" Annuler" />
                    </div>
                    
            </form>

        </div>
  
    </body>