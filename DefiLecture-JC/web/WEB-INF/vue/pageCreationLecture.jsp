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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="java.sql.Connection"%>
<%@page import="com.defilecture.modele.LectureDAO"%>
<%@page import="jdbc.Connexion"%>

<jsp:useBean id="connexion" scope="page" class="jdbc.Connexion"></jsp:useBean>  
<jsp:useBean id="dao" scope="page" class="com.defilecture.modele.LectureDAO">
  <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
</jsp:useBean>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Création d'une lecture</title>
    <script type="text/javascript">

     var ajoutMin=0;
     
     function ajouterMin(bouton, min){
	 minAjd=${dao.getMinutesAjd(sessionScope.connecte)};
	 bouton.css('border', '3px dotted goldenrod');
	 bouton.css('border-radius', '37px');

	 //limite le nombre de minutes quotidiennes
	 minutes = Math.min(parseInt($('.dureeMinutes').text())+min,${applicationScope['com.defilecture.limiteHard']}-minAjd);
	 var txt = " Vous allez cumuler " + (minutes+minAjd) + " minutes de lecture pour aujourd'hui. Confirmez qu'il s'agit bien de lectures effectuées aujourd'hui seulement en entrant «Je confirme» ci-dessous :";
	 $('#texteConfirmation').html(txt);
	 $('#dureeMinutes').val(minutes);
	 $('.dureeMinutes').html(minutes + " minutes");

	 if(minutes==0){
	     $('#confirmer').hide();
	     $('#confirmerAvertissement').hide();
	 }
	 else{
	     if(minutes+minAjd>${applicationScope['com.defilecture.limiteSoft']}){
		 $('#confirmer').hide();
		 $('#confirmerAvertissement').show();
	     }
	     else{
		 $('#confirmer').show();
		 $('#confirmerAvertissement').hide();
	     }
	 }
     }
     
     $(document).ready(function(){
	 
         var mobile   = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent); 
         var start = mobile ? "touchstart" : "mousedown";
         var end =  mobile ? "touchend" : "mouseup";


         $('.duree15').bind(start, function(){
	     ajouterMin($(this),15);
	 });
         $('.duree15').on(end+" mouseleave mouseout", function() {
             $(this).css('border', 'transparent');
         });
         
         $('.duree30').bind(start, function(){
	     ajouterMin($(this),30);
	 });
         $('.duree30').on(end+" mouseleave mouseout", function() {
             $(this).css('border', 'transparent');
         });

         $('.duree45').bind(start, function(){
	     ajouterMin($(this),45);
	 });
         $('.duree45').on(end+" mouseleave mouseout", function() {
             $(this).css('border', 'transparent');
         });

         $('.duree60').bind(start, function(){
	     ajouterMin($(this),60);
	 });
         $('.duree60').on(end+" mouseleave mouseout", function() {
             $(this).css('border', 'transparent');

             
         });
         
         
         $('.remiseAzero').on("mousedown", function() {

             minutesTotal = 0;
             $('#dureeMinutes').val(minutesTotal);
             $('.dureeMinutes').html(minutesTotal+ " minute");
	     ajouterMin($(this),0);

         });
         
         $('.lecture-submit').bind(start, function() {
             $(this).css('border', '3px dotted goldenrod');
             $(this).css('border-radius', '37px');


         });
         $('.lecture-submit').on(end+" mouseleave mouseout", function() {
             $(this).css('border', 'transparent');

             
         });
         
         $('.obligatoire-oui').click(function() {
             $(this).css('background-color', 'rgba(255,255,255,0.8)');
             $('.obligatoire-non').css('background-color', 'rgba(255,255,255,0.5)');
         });
         $('.obligatoire-non').click(function() {
             $(this).css('background-color', 'rgba(255,255,255,0.8)');
             $('.obligatoire-oui').css('background-color', 'rgba(255,255,255,0.5)');
         });
     });
    </script>
  </head>
  <body>
    
    <div class='row creation-lecture-row'> 
      
      <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
        <div class="creation-lecture-form">
          <h1>Ajoutez une lecture!</h1>
          <form action="*.do" method="post" id="formCreationLecture">
            
            <div class="form-group">
	      <label for="titre">Titre : </label><input list="browsers" name="titre" id="titre" class="form-control" required/>
              <datalist id="browsers">
                <c:set var="listeLectures" value="${dao.findByIdCompteOrderByTitre(sessionScope.connecte)}"/>
                <c:forEach items="${listeLectures}" var="l">
                  <option value="${l.titre}">   
                </c:forEach>
              </datalist>
            </div>
            
            <div class="form-group">
              <label id="lblDureeLecture" >Durée de la lecture : <br> (cliquez sur les <% out.println(application.getAttribute("vocPoints"));%> pour afficher les minutes de lecture) </label>
              
              
              <label class="dureeMinutes">0 minute</label>
              <input type="hidden" name="dureeMinutes" id="dureeMinutes" required value=0>
              
              <div class="dureeLecture">
                
                <label class="duree15" id="duree15"></label>
                
                
                <label class="duree30"></label>
                
                <label class="duree45"></label>
                
                
                <label class="duree60"></label>

              </div>
            </div>
            
            <a class="retour remiseAzero"><span class="glyphicon glyphicon-repeat"></span>Remise à zéro </a>
            
            <div class="lectureObligatoire">
              <div class="form-group">
                <label for="obligatoire">La lecture était-elle obligatoire? <br>  (Lecture imposée dans le cadre d’un cours.) </label>

                <div class="radio obligatoire-oui" style="background-color: rgba(255,255,255,0.8);">
                  <label ><input type="radio" name="obligatoire" value="1" required checked>oui</label>
                </div>
                <div class="radio obligatoire-non">
                  <label ><input type="radio" name="obligatoire" value="0" required >non</label>
                </div>
              </div>
            </div>

            <input type="hidden" name="tache" value="effectuerCreationLecture">
	    <div id="confirmer" class="form-group" style="display: none; text-align: center;">
              <label class="lecture-submit"><input type="submit" class="btn btn-success" value="Ajouter" id="btnSubmit"></input></label>
	    </div>
	    <div id="confirmerAvertissement" class="form-group" style="display: none; text-align: center;">
	      <div class="alert alert-danger" role="alert" id="texteConfirmation">
	      </div>
	      <input type="text" id="confirmation">
              <label class="lecture-submit"><input class="btn btn-success" value="Ajouter" id="btnSubmit" onclick="if($('#confirmation').val().toLowerCase()=='je confirme'){$('#btnSubmit').click();}"></input></label>
	    </div>
            <a href="*.do?tache=afficherPageGestionLectures" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des lectures</a>
            
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
