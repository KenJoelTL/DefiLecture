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
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<% 
    Connexion.reinit();
    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DefiDAO dao = new DefiDAO(cnx);
    Defi d = dao.read(request.getParameter("id"));
    String choixReponse = d.getChoixReponse();
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
                                    <button type="submit" class="btn btn-info" name="annule"  >Annuler</button>
                                    <button type="submit" class="btn btn-success" name="valider" >Valider ma réponse</button>
                                    
                                     
                                </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>
    </body>