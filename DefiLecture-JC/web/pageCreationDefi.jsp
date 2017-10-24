<%-- 
    Document   : defi
    Created on : 2017-10-22, 14:22:35
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <h1>Creation d'un defi</h1>
        <form action="*.do" method="post">
            <div>
                Nom du défi* : <input type="text" name="nom" required />
            </div>
            <div>
                Date de début* : <input type="text" name="dateDebut" required />
                Date de fin* : <input type="text" name="dateFin" required /> 
            </div>
            <div>
               
                Question : <input type="text" name="question" required /> 
             </div>
             <div>
                 Choix de réponse :
                 Choix #1 : <input type="text" name="choix1" />
                 Choix #2 : <input type="text" name="choix2" />
                 Choix #3 : <input type="text" name="choix3" />
                 Choix #4 : <input type="text" name="choix4" />
                 
                 <br>
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
            <div>
               
                Nombre de point pour ce défi : <input type="text" name="point" required /> 
             </div>
           
            <input type="hidden" name="tache" value="effectuerCreationDefi">
            <input type="submit" value="Créer un défi!" />
        </form>
