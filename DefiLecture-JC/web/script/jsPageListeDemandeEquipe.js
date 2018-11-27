/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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