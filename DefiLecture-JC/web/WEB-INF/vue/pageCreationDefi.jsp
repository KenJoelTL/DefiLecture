<%-- 
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script language="javascript" src="./script/jquery-1.4.2.min.js"></script>
<script language="javascript" src="./script/jsPageCreationDefi.js"></script>


   <div class='row'> 
        
        <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-defi-col">
            <div class="creation-defi-form">
        <h1>Creation d'un defi</h1>
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
            
            <div class="ajouterChoix">
            <div class="form-group">
                <label>Choix de réponse :</label>
                <label id="ajouterChoix">Ajouter un choix : <button id="btnPLUS" type="button">+</button> <button id="btnMOINS" type="button">-</button></label>
                
                <div id="choixReponse">
                    <div><label for="choix1">choix#1 :</label> 
                    <input  id="choix1"class="choix form-control" type="text" name="choix1" required ></input</div>
                    </div>
                </div>
            </div>
            </div>
       
       <div class="bonneReponse">
            <div class="form-group">
                
                <label id="titreBonneReponse">La bonne réponse est :</label>
                <div id="bonneReponse">
                    <div class="radio">
                        <label><input type="radio" name="reponse" value="0" required >choix#1: <span id="radiochoix1"></span></label>
                    </div>
                  
                    
                </div>

            </div>
           </div>
            
            
            
            <div class="form-group">
                <label for="point">Quantité de doublons pour ce défi* : </label>
                <input class="form-control" type="text" name="valeurMinute" required />
            </div>
            
            <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <button type="submit" class="btn btn-success">Créer un défi! </button>
     
        </form>
           </div>
    </div>
</div>