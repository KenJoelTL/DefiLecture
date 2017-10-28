<%-- 
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
                <input class="form-control" type="text" name="dateDebut" required />
                <label for="dateDebut">Date de fin* : </label>
                <input class="form-control" type="text" name="dateFin" required />
            </div>
            <div class="form-group">
                <label for="question">Question* : </label>
                <input class="form-control" type="text" name="question" required />
            </div>
            
            <div class="form-group">
                <h4>Choix de réponse :</h4>
                <label for="choix1">Choix#1 : </label>
                <input class="form-control" type="text" name="choix1" required />
                <label for="choix1">Choix#2 : </label>
                <input class="form-control" type="text" name="choix2" required />
                <label for="choix3">Choix#3 : </label>
                <input class="form-control" type="text" name="choix3" required />
                <label for="choix4">Choix#4 : </label>
                <input class="form-control" type="text" name="choix4" required />
                
                
                
                    La bonne réponse est :
                    <div class="radio">
                        <label><input type="radio" name="reponse" value="0" required >Choix #1</label>
                    </div>
                     <div class="radio">
                        <label><input type="radio" name="reponse" value="1" required >Choix #2</label>
                    </div>
                     <div class="radio">
                        <label><input type="radio" name="reponse" value="2" required >Choix #3</label>
                    </div>
                     <div class="radio">
                        <label><input type="radio" name="reponse" value="3" required >Choix #4</label>
                    </div>
                
               
            
            </div>
            <div class="form-group">
                <label for="point">Nombre de point pour ce défi* : </label>
                <input class="form-control" type="text" name="valeurMinute" required />
            </div>
            
           
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <input type="submit" value="Créer un défi!" />
        </form>
            <br>
</div>