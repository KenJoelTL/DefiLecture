<%-- 
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script language="javascript" src="./script/jquery-1.4.2.min.js"></script>

<script>
    $(document).ready(function(){
        var i=2; // compteur pour les choix de reponse
        
        //Quand on appuie sur le bouton +, un nouveau choix de reponse est affiché
        $("#btnPLUS").on("click", function(){
            //Création textbox pour les choix de réponse
            var s = "<div><label for=\"choix"+ i +"\">Choix#"+ i +" : </label>" +
                "<input  id=\"choix"+ i +"\" class=\"choix form-control\" type=\"text\" name=\"choix"+ i +"\" required /></div>"; 
            //Création des boutons radio pour choisir la bonne réponse
            var b = "<div class=\"radio\">"+
                        "<label><input type=\"radio\" name=\"reponse\" value=\""+ (i-1) +"\" required >Choix #"+ i +": <span id=\"radiochoix"+i+"\"></span></label>"+
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
</script>
    
        <h1>Creation d'un defi</h1>
        <div class="col-lg-4 col-sm-10">
        <form action="*.do" method="post">
            <div class="form-group">
                <label for="nom">Nom du défi* : </label>
                <input class="form-control" type="text" name="nom" required />
            </div>
            
            <div class="form-group">
                <label for="description">Description du défi* : </label>
                <textarea class="form-control" name="description" rows="5" required></textarea>
            </div>
            
            <div class="form-group">
                <label for="dateDebut">Date de début* : </label>
                <input class="form-control" type="datetime-local" name="dateDebut" required />
                <label for="dateDebut">Date de fin* : </label>
                <input class="form-control" type="datetime-local" name="dateFin" required />
            </div>
            <div class="form-group">
                <label for="question">Question à répondre pour réussir ce défi* : </label>
                <input class="form-control" type="text" name="question" required />
            </div>
            
            <div class="form-group">
                <h4>Choix de réponse :</h4>
                Ajouter un choix : <button id="btnPLUS" type="button">+</button> <button id="btnMOINS" type="button">-</button>
                
                <div id="choixReponse">
                    <div><label for="choix1">Choix#1 :</label> 
                    <input  id="choix1"class="choix form-control" type="text" name="choix1" required /></div>
                </div>
                
 
            
            </div>
            
            <div class="form-group">
                
                <h4 id="titreBonneReponse">La bonne réponse est :</h4>
                <div id="bonneReponse">
                    <div class="radio">
                        <label><input type="radio" name="reponse" value="0" required >Choix #1: <span id="radiochoix1"></span></label>
                    </div>
                    
                    
                </div>

            </div>
            
            
            
            <div class="form-group">
                <label for="point">Nombre de point pour ce défi* : </label>
                <input class="form-control" type="text" name="valeurMinute" required />
            </div>
            
            <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <input type="submit" value="Créer un défi!" />
        </form>
            <br>
</div>