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
    Document   : pageModificationDefi
    Created on : 2017-10-31, 18:48:11
    Author     : Charles
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.defilecture.modele.Defi"%>
<%@page import="jdbc.Config"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Connexion"%>
<%@page import="com.defilecture.modele.DefiDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<!-- Faire la connexion -->
<jsp:useBean id="connexion" class="jdbc.Connexion"/>

<!-- Cree les DAOs -->
<jsp:useBean id="daoDefi" class="com.defilecture.modele.DefiDAO" scope="page">
    <jsp:setProperty name="daoDefi" property="cnx" value="${connexion.connection}"/>
</jsp:useBean>

<!-- Declarer les classes-->
<jsp:useBean id="defi" class="com.defilecture.modele.Defi" scope="page"/>

<!-- Assigner les variables-->
<c:set var="defi" value="${daoDefi.read(param.id)}" scope="page"/>
<c:set var="choixReponse" value="${defi.getChoixReponse()}" scope="page"/>
<c:set var="bonneReponse" value="${defi.getReponse()}" scope="page"/>


<script>
    $(document).ready(function(){
          var value = ${choixReponse}; //chaine string venant de la BD qui contient les choix de réponse
          var reponse = ${bonneReponse}; //valeur de la bonne réponse
          var sBonneReponse=""; //chaine string qui contiendra le code HTML pour recréer les radio boutons de la bonne réponse
          var sChoixReponse=""; //chaine string qui contiendra le code HTML pour recréer les inputs text pour les choix de réponse
 
          //Boucle pour recréer la bonne réponse en bouton radio
          for(var i=1; i<=value.length; i++ ){
            //Si l'index est égal à la bonne réponse, le radio bouton sera checked 
            if((i-1)== reponse){
                 sBonneReponse += "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required checked >choix#"+ i +": <span id=\"radiochoix"+i+"\">"+value[i-1]+"</span></label>"+
                    "<\/div>";
              }
             else{
             sBonneReponse += "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >choix#"+ i +": <span id=\"radiochoix"+i+"\">"+value[i-1]+"</span></label>"+
                    "<\/div>";
                }
            }
            $("#bonneReponse").append(sBonneReponse);
            

          //Boucle pour recréer les choix de réponses en input text
          for(var i=1; i<=value.length; i++){
              sChoixReponse += "<div><label for=\"choix"+ i +"\">choix#"+ i +" : </label>" +
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
            sChoixReponse = "<div><label for=\"choix"+ i +"\">choix#"+ i +" : </label>" +
                "<input  id=\"choix"+ i +"\" class=\"choix form-control\" type=\"text\" name=\"choix"+ i +"\" required /></div>"; 
            //Création des boutons radio pour choisir la bonne réponse
            sBonneReponse = "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >choix#"+ i +": <span id=\"radiochoix"+i+"\"></span></label>"+
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
<div class='row'> 
        
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 modification-defi-col">
        <div class="modification-defi-form">
            <h1>Modification d'un defi</h1>
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
	            <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss" value="${defi.dateDebut}" var="dateDebutPARSE" />
                    <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="yyyy-MM-dd" />
                    <label for="dateDebut">Date de début* : </label>
                    <input class="form-control" type="date" name="dateDebut" value="${dateDebut}" required />
                    <fmt:formatDate var="heureDebut" value="${dateDebutPARSE}" pattern="HH:mm:ss" />
                    <label for="heureDebut">Heure de début* : </label>
                    <input class="form-control" type="time" name="heureDebut" value="${heureDebut}" required />
                    <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss" value="${defi.dateFin}" var="dateFinPARSE" />
                    <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="yyyy-MM-dd" />
                    <label for="dateDebut">Date de fin* : </label>
                    <input class="form-control" type="date" name="dateFin" value="${dateFin}" required />
                    <fmt:formatDate var="heureFin" value="${dateFinPARSE}" pattern="HH:mm:ss" />
                    <label for="heureFin">Heure de fin* : </label>
                    <input class="form-control" type="time" name="heureFin" value="${heureFin}" required />
                </div>
                <div class="form-group">
                    <label for="question">Question à répondre pour réussir ce défi* : </label>
                    <input class="form-control" type="text" name="question" value="${defi.question}" required />
                </div>
                <div class="ajouterChoix">
                <div class="form-group">
                    <label>Choix de réponse :</label>
                    <label id="ajouterChoix">Ajouter un choix :</label> <button id="btnPLUS" type="button">+</button> <button id="btnMOINS" type="button">-</button>

                    <div id="choixReponse"></div>



                </div>
                </div>
                <div class="bonneReponse">
                    <div class="form-group">

                         <label id="titreBonneReponse">La bonne réponse est :</label>
                        <div id="bonneReponse"></div>

                    </div>
                </div>



                <div class="form-group">
                    <label for="point">Quantité de <% out.println(application.getAttribute("vocPoints"));%> pour ce défi* : </label>
                    <input class="form-control" type="text" name="valeurMinute" value="${defi.valeurMinute}" required />
                </div>

                <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
                <input type="hidden" name="idDefi" value="${defi.idDefi}">
                <input type="hidden" name="tache" value="effectuerModificationDefi">
                <button type="submit" class="btn btn-success" name="modifie" >Enregistrer </button> 
            </form>
            <br />
            
            <form>
              <input type="hidden" name="idDefiSup" value="${defi.idDefi}">
              <input type="hidden" name="tache" value="supprimerDefi">
              
              <!-- Bouton de suppression -->
              <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#supprimerModal">
                Supprimer
              </button>

              <!-- Boîte de dialogue de confirmation -->
              <div class="modal fade" id="supprimerModal" tabindex="-1" role="dialog" aria-labelledby="supprimerModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="supprimerModalLabel">Confirmer la suppression</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      Voulez-vous vraiment supprimer le défi?
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
                      <button type="submit" class="btn btn-secondary">Oui</button>
                    </div>
                  </div>
                </div>
              </div>              
            </form>
            
            <a href="*.do?tache=afficherPageParticipationDefi" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des défis</a>
            <br />
        </div>
    </div>
</div>
