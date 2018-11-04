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
    Document   : pageMarcheASuivre
    Created on : 2018-03-21, 07:34:53
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">   
        <title>Marche à suivre</title>
    </head>
    <body>

        <div class="row creation-lecture-row">
            <form action="?tache=afficherPageProfil&id=<%=session.getAttribute("connecte")%>" method="post"> <!-- faire que sa soit l'id des autres users -->
                <input type="submit">
            </form>

            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 scores-col marcheASuivre">
  
                    <h1>Un nouveau défi pour tous!</h1>
                    <p>

                        Bienvenue à cette nouvelle édition du Défi lecture! Organisé par le Centre d’aide en français dans le cadre de la Politique de valorisation de la langue, le Défi lecture s’adresse tant aux élèves qu’au personnel du Collège de Rosemont.

                    </p>

                    <p>
                        Empreinte de l’imaginaire de la piraterie, cette activité vise à promouvoir la lecture en encourageant les membres de la communauté collégiale à former des équipes de corsaires, composées d’un capitaine et de trois matelots, qui consigneront leurs lectures sur un site Internet. Les différents équipages, à bord de leur fier vaisseau, se livreront un combat féroce pour obtenir le plus de doublons et ainsi damer le pion à leurs adversaires.

                    </p>

                    <p>
                        Les employés et les tuteurs du CAF pourront devenir capitaines et former leur équipage en recrutant trois élèves. Des prix de participation (dictionnaires, livres, bons d’achat, etc.) seront remis aux braves qui sauront relever des défis tout au long du périple.



                    </p>

                    <h2>Inscrivez-vous</h2>
                    
                    
                    <div>

                        <h3>  <strong>Pour être capitaine&nbsp;:</strong></h3>
                    <ol>
                        <li>Inscrivez-vous </li>
                        <li>Cochez : «&nbsp;Je désire être capitaine d’un équipage (employé ou tuteur)&nbsp;»</li>
                        <li>Attendez que nous approuvions votre inscription en tant que capitaine </li>
                        <li>Créez votre équipage en donnant un nom au navire</li>
                        <li>Invitez des élèves à joindre votre équipage.</li>
                    </ol>

                    </div>
                    <div>

                        <h3>  <strong>Pour vous joindre à un équipage&nbsp;:</strong></h3>
                    <ol>
                        <li>Inscrivez-vous </li>
                        <li>Sélectionnez l’onglet «&nbsp;Joindre un équipage&nbsp;», choisissez l’équipage de votre choix et cliquez sur «&nbsp;Envoyer une demande d’adhésion&nbsp;» </li>
                        <li>Attendez que le capitaine approuve votre demande</li>
                    </ol>
                    </div>

                    <h2>

                        Du <b>17 au 25&nbsp;septembre 2018</b> : inscrivez-vous.<br>
                        Du <b>25&nbsp;septembre au 15&nbsp;octobre</b> : consignez vos lectures et relevez les défis proposés. 


                    </h2>

                    <p>
                        <strong>Consultez la page <i class="fa fa-facebook"></i> Facebook <a  target="_blank" href='https://www.facebook.com/DefiLectureCollegeRosemont/'><i>Défi lecture – Collège de Rosemont</i></a></strong> et suivez-nous sur <a href="https://www.instagram.com/defilecture"><img src="images/instagram.png" height=32> Instagram</a> pour ne rien manquer de l’aventure et pour nous formuler vos commentaires au sujet de Défi lecture!

                    </p>
                    <p style="text-align: right">
                        Personnes ressources : Alexandra Jarque et Mélanie Bergeron, CAF, local A-494

                    </p>
                    <div class='divIconePirate'>
                    <label class='pirateIconeMarcheASuivre'></label>
                    </div>
         
                </div>
            </div>
        </div>
    </body>
</html>
