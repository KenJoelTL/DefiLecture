<%-- 
    Document   : pageModificationDefi
    Created on : 2017-10-31, 18:48:11
    Author     : Charles
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="modele.Defi"%>
<%@page import="jdbc.Config"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Connexion"%>
<%@page import="modele.DefiDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script language="javascript" src="./script/jquery-1.4.2.min.js"></script>

<% 
    Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DefiDAO daoDefi = new DefiDAO(cnx);
    Defi defi = daoDefi.read(request.getParameter("id"));
    String choixReponse = defi.getChoixReponse();
    String bonneReponse = defi.getReponse();
    pageContext.setAttribute("defi", defi);

%>


<script>
    $(document).ready(function(){
          var value = <%=choixReponse%>; //chaine string venant de la BD qui contient les choix de réponse
          var reponse = <%=bonneReponse%>; //valeur de la bonne réponse
          var sBonneReponse=""; //chaine string qui contiendra le code HTML pour recréer les radio boutons de la bonne réponse
          var sChoixReponse=""; //chaine string qui contiendra le code HTML pour recréer les inputs text pour les choix de réponse
 
          //Boucle pour recréer la bonne réponse en bouton radio
          for(var i=1; i<=value.length; i++ ){
            //Si l'index est égal à la bonne réponse, le radio bouton sera checked 
            if((i-1)== reponse){
                 sBonneReponse += "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required checked >Choix #"+ i +": <span id=\"radiochoix"+i+"\">"+value[i-1]+"</span></label>"+
                    "<\/div>";
              }
             else{
             sBonneReponse += "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >Choix #"+ i +": <span id=\"radiochoix"+i+"\">"+value[i-1]+"</span></label>"+
                    "<\/div>";
                }
            }
            $("#bonneReponse").append(sBonneReponse);
            

          //Boucle pour recréer les choix de réponses en input text
          for(var i=1; i<=value.length; i++){
              sChoixReponse += "<div><label for=\"choix"+ i +"\">Choix#"+ i +" : </label>" +
                "<input  id=\"choix"+ i +"\" class=\"choix form-control\" type=\"text\" name=\"choix"+ i +"\" required value=\""+value[i-1]+"\" /></div>"; 
          
            }
            $("#choixReponse").append(sChoixReponse);
            
            //Boucle pour recréer la chaine JSON des choix de réponses qui sera stocké dans la BD
            var tab = [];
            for(var j = 1; j< i; j++){
                tab.push($("#choix"+j).val());
            }
            var chaineJSON = JSON.stringify(tab);
            $("#choixReponseJSON").attr("value", chaineJSON);


        //Quand on appuie sur le bouton +, un nouveau choix de reponse est affiché
        $("#btnPLUS").on("click", function(){
            //Création textbox pour les choix de réponse
            sChoixReponse = "<div><label for=\"choix"+ i +"\">Choix#"+ i +" : </label>" +
                "<input  id=\"choix"+ i +"\" class=\"choix form-control\" type=\"text\" name=\"choix"+ i +"\" required /></div>"; 
            //Création des boutons radio pour choisir la bonne réponse
            sBonneReponse = "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >Choix #"+ i +": <span id=\"radiochoix"+i+"\"></span></label>"+
                    "<\/div>";
            i++;
            $("#choixReponse").append(sChoixReponse);
            $("#bonneReponse").append(sBonneReponse);

        });
        
        //Quand on appuie sur le bouton -, le dernier choix de réponse ajouté est supprimé
        $("#btnMOINS").on("click", function(){
            if(i>2)
            {
            //On supprime le text box du dernier choix de reponse
            var select = document.getElementById('choixReponse');
            select.removeChild(select.lastChild);
            //On supprime le bouton radio du dernier choix de réponse
            var select = document.getElementById('bonneReponse');
            select.removeChild(select.lastChild);
            i--;
            //On recrée la chaine string qui sera passé en parametre pour les choix de réponse
            //===>Pour créer une chaine JSON
            var tab = [];
            for(var j = 1; j< i; j++){
                tab.push($("#choix"+j).val());
            }
            var chaineJSON = JSON.stringify(tab);
            $("#choixReponseJSON").attr("value", chaineJSON);
            }
        });
        
        
        $("#choixReponse").on('keyup', '.choix', function(){
            //===>Pour créer une chaine JSON
            var tab = [];
            for(var j = 1; j< i; j++){
                tab.push($("#choix"+j).val());
            }
            var chaineJSON = JSON.stringify(tab);
            $("#choixReponseJSON").attr("value", chaineJSON);
            value = $("#"+$(this).attr("id")).val();
            $("#radio"+$(this).attr("id")).html(value);
        });
    });
</script>
    
        <h1>Creation d'un defi</h1>
        <div class="col-lg-4 col-sm-10">
        <form action="*.do" method="post">
            <div class="form-group">
                <label for="nom">Nom du défi* : </label>
                <input class="form-control" type="text" name="nom" value="${defi.nom}" required />
            </div>
            
            <div class="form-group">
                <label for="description">Description du défi* : </label>
                <textarea class="form-control" name="description" rows="5" required>${defi.description}</textarea>
            </div>
            
            <div class="form-group">
                <c:catch>
                    <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${defi.dateDebut}" var="dateDebutPARSE" />
                </c:catch>
                <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="yyyy-MM-dd'T'HH:mm" />
                <label for="dateDebut">Date de début* : </label>
                <input class="form-control" type="datetime-local" name="dateDebut" value="${dateDebut}" required />
                <c:catch>
                    <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${defi.dateFin}" var="dateFinPARSE" />
                </c:catch>
                <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="yyyy-MM-dd'T'HH:mm" />
                <label for="dateDebut">Date de fin* : </label>
                <input class="form-control" type="datetime-local" name="dateFin" value="${dateFin}" required />
            </div>
            <div class="form-group">
                <label for="question">Question à répondre pour réussir ce défi* : </label>
                <input class="form-control" type="text" name="question" value="${defi.question}" required />
            </div>
            
            <div class="form-group">
                <h4>Choix de réponse :</h4>
                Ajouter un choix : <button id="btnPLUS" type="button">+</button> <button id="btnMOINS" type="button">-</button>
                
                <div id="choixReponse"></div>
                
 
            
            </div>
            
            <div class="form-group">
                
                <h4 id="titreBonneReponse">La bonne réponse est :</h4>
                <div id="bonneReponse"></div>

            </div>
            
            
            
            <div class="form-group">
                <label for="point">Nombre de point pour ce défi* : </label>
                <input class="form-control" type="text" name="valeurMinute" value="${defi.valeurMinute}" required />
            </div>
            
            <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
            <input type="hidden" name="idDefi" value="${defi.idDefi}">
            <input type="hidden" name="tache" value="effectuerModificationDefi">
            <input type="submit" name="modifie" value=" Enregistrer" />
                <input type="submit" name="annule" value=" Annuler" />
        </form>
            <br>
</div>

