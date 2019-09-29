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
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
<script language="javascript" src="./script/jsPageCreationDefi.js"></script>


   <div class='row'> 
        
        <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-defi-col">
            <div class="creation-defi-form">
        <h1>Création d'un defi</h1>
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
                <input class="form-control" type="date" name="dateDebut" required />
                <label for="dateDebut">Heure de début* : </label>
                <input class="form-control" type="time" name="heureDebut" required />
                <label for="dateDebut">Date de fin* : </label>
                <input class="form-control" type="date" name="dateFin" required />
                <label for="dateDebut">Heure de fin* : </label>
                <input class="form-control" type="time" name="heureFin" required />
            </div>

            
            
            <div class="form-group">
                <label for="question">Question à laquelle les participants devront répondre* : </label>
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
                <label for="point">Nombre de <% out.println(application.getAttribute("vocPoints"));%> accordé pour la réussite de ce défi* : </label>
                <input class="form-control" type="number" name="valeurMinute" required />
            </div>
            
            <input id="choixReponseJSON" type="hidden" name="choixReponseJSON" value="">
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <button type="submit" class="btn btn-success">Créer un défi! </button>
            <a href="*.do?tache=afficherPageParticipationDefi" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des défis</a>
     
        </form>
           </div>
    </div>
</div>
