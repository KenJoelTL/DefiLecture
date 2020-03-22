/* 
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
    Document   : jsPageListedemandeEquipe
    Created on : 2018-12-16, 20:32:35
    Author     : Roodney Aladin
--%>
 */
$(document).ready(function(){
    $( ".alert" ).hide();
    });

function Accepter(idDemandeEquipe,numeroRanger){

  $.ajax({
        url: "accepter.do?tache=effectuerAcceptationDemandeAdhesion&idDemandeEquipe="+idDemandeEquipe,
        
        type: 'POST',
        
        success: function (response) {
            $( ".alert" ).show();
            
            var obj = JSON.parse(response);
            switch(obj.typeAlert){
                case "succes":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-success"><strong id="msg"></strong></div>';
                    $("tr#utilisateur-"+ numeroRanger).remove();
                    break;
                case "avertissement":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-warning"><strong id="msg"></strong></div>';
                    $("tr#utilisateur-"+ numeroRanger).remove();
                    break;
                case "danger":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-danger"><strong id="msg"></strong></div>';
                    break;
            }
            
            $( "#msg" ).text(obj.msg);
            
            
            
            
        }
    });
    
    

  }
  
  
  function Refuser(idDemandeEquipe,numeroRanger){

  $.ajax({
        url: "refuser.do?tache=effectuerSuppressionDemandeAdhesion&idDemandeEquipe="+idDemandeEquipe+"&ordre=recu",
        
        type: 'POST',
        
        success: function (response) {
            $( ".alert" ).show();
            
            var obj = JSON.parse(response);
            switch(obj.typeAlert){
                case "succes":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-success"><strong id="msg"></strong></div>';
                    $("tr#utilisateur-"+ numeroRanger).remove();
                    break;
                case "avertissement":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-warning"><strong id="msg"></strong></div>';
                    $("tr#utilisateur-"+ numeroRanger).remove();
                    break;
                case "danger":
                    document.getElementById("notification").innerHTML = '<div class="alert alert-danger"><strong id="msg"></strong></div>';
                    break;
            }
            
            $( "#msg" ).text(obj.msg);
        }
    });
    
    

  }