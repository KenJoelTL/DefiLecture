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
 */


$(document).ready(function(){
  $( ".alert" ).hide();
  });

function Demande(idCompte,idEquipe,i){

  $.ajax({
        url: "demande.do?tache=effectuerDemandeAdhesionEquipe&idCompte="+idCompte+"&idEquipe="+idEquipe, 
        type: 'GET',
        
        success: function (response) {
            $( ".alert" ).show();
            var obj = JSON.parse(response);
            $( "#msg" ).text(obj.msg);
            $( "#demandeLien" ).remove();
            msg="<a id='annulationLien-"+i+"' onclick='Annulation("+obj.idDemandeEquipe+','+idCompte+','+idEquipe+','+i+")'>Annuler la demande d'adh&eacute;sion </a>";
            document.getElementById("Lien-"+i).innerHTML = msg;


        }
    });
    
    

  }

function Annulation(idDemandeEquipe,idCompte,idEquipe,i){

  $.ajax({
        url: "refuser.do?tache=effectuerSuppressionDemandeAdhesion&idDemandeEquipe="+idDemandeEquipe,
        type:'GET', 
        success: function (response) {
            $( ".alert" ).show();
            var obj = JSON.parse(response);
            $( "#msg" ).text(obj.msg);
            $( "#annulationLien" ).remove();
            var msg= "<a id='demandeLien-"+i+"' onclick='Demande("+idCompte+','+idEquipe+','+i+")'>Envoyer une demande d'adésion </a>"
            document.getElementById("Lien-"+i).innerHTML = msg;
            
            //$( "#Lien" ).html(msg);
            
        }
    });
    
    

  }
