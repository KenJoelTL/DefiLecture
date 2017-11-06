<%-- 
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script language="javascript" src="./script/jquery-1.4.2.min.js"></script>
<script language="javascript" src="./script/jsPageCreationDefi.js"></script>



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
                    <input  id="choix1"class="choix form-control" type="text" name="choix1" required ></input</div>
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
                <label for="point">Nombre de minutes pour ce défi* : </label>
                <input class="form-control" type="text" name="valeurMinute" required />
            </div>
            
            <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <input type="submit" value="Créer un défi!" />
        </form>
            <br>
</div>