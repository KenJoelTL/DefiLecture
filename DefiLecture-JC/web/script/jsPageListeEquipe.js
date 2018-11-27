/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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