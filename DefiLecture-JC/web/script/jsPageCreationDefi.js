/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    $(document).ready(function(){
        var i=2; // compteur pour les choix de reponse
        
        //Quand on appuie sur le bouton +, un nouveau choix de reponse est affiché
        $("#btnPLUS").on("click", function(){
            //Création textbox pour les choix de réponse
            var s = "<div><label for=\"choix"+ i +"\">choix#"+ i +" : </label>" +
                "<input  id=\"choix"+ i +"\" class=\"choix form-control\" type=\"text\" name=\"choix"+ i +"\" required /></div>"; 
            //Création des boutons radio pour choisir la bonne réponse
            var b = "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >choix#"+ i +": <span id=\"radiochoix"+i+"\"></span></label>"+
                    "<\/div>";
            i++;
            $("#choixReponse").append(s);
            $("#bonneReponse").append(b);

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


