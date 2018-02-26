<%-- 
    Document   : pageCreationLecture
    Created on : 2017-10-24, 13:07:39
    Author     : Charles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Création d'une lecture</title>
        <script type="text/javascript">
            $(document).ready(function(){
                
                $('.duree15').mousedown(function() {
                    $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    minutesTotal = parseInt($('.dureeMinutes').text())+15;
                    $('#dureeMinutes').val(minutesTotal);
                    $('.dureeMinutes').html(minutesTotal + " minutes");
  
                });
                
                 $('.duree15').mouseup(function() {
                    $(this).css('border', 'transparent');

                    
                });
                $('.duree30').mousedown(function() {
                     $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    minutesTotal = parseInt($('.dureeMinutes').text())+30;
                    $('#dureeMinutes').val(minutesTotal);
                    $('.dureeMinutes').html(minutesTotal+ " minutes");
               



                });
                
                $('.duree30').mouseup(function() {
                    $(this).css('border', 'transparent');

                    
                });
                $('.duree45').mousedown(function() {
                    $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    minutesTotal = parseInt($('.dureeMinutes').text())+45;
                    $('#dureeMinutes').val(minutesTotal);
                    $('.dureeMinutes').html(minutesTotal+ " minutes");
                 



                });
                $('.duree45').mouseup(function() {
                    $(this).css('border', 'transparent');

                    
                });
                $('.duree60').mousedown(function() {
                     $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    minutesTotal = parseInt($('.dureeMinutes').text())+60;
                    $('#dureeMinutes').val(minutesTotal);
                    $('.dureeMinutes').html(minutesTotal+ " minutes");
            



                });
                $('.duree60').mouseup(function() {
                    $(this).css('border', 'transparent');

                    
                });
                
                 $('.lecture-submit').mousedown(function() {
                     $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');


                });
                $('.lecture-submit').mouseup(function() {
                    $(this).css('border', 'transparent');

                    
                });
                
                 $('.obligatoire-oui').click(function() {
                     $(this).css('background-color', 'rgba(255,255,255,0.8)');
                      $('.obligatoire-non').css('background-color', 'rgba(255,255,255,0.5)');
                });
                 $('.obligatoire-non').click(function() {
                     $(this).css('background-color', 'rgba(255,255,255,0.8)');
                      $('.obligatoire-oui').css('background-color', 'rgba(255,255,255,0.5)');
                });
            });
            </script>
    </head>
    <body>
        
        <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 creation-lecture-col">
                <div class="creation-lecture-form">
                    <h1>Inscription d'une lecture</h1>
                    <form action="*.do" method="post">
                         
                        <div class="form-group">
                            <label for="titre">Titre* : </label>
                            <input class="form-control" type="text" name="titre" required />
                        </div>
                
                        
           
                            <div class="form-group">
                                <label id="lblDureeLecture" >Durée de la lecture : </label>
                                
                                <label class="dureeMinutes">0 minute</label>
                                <input type="hidden" name="dureeMinutes" id="dureeMinutes" required value=0>
                                
                                <div class="dureeLecture">
                         
                                    <label class="duree15" id="duree15"></label>
                          
                              
                                    <label class="duree30"></label>
                         
                                    <label class="duree45"></label>
                         
                              
                                    <label class="duree60"></label>
                         
                             
                            </div>
                        </div>
                        
                        
                        
                        <div class="lectureObligatoire">
                            <div class="form-group">
                                <label for="obligatoire">La lecture était-elle obligatoire? : </label>

                                <div class="radio obligatoire-oui">
                                    <label ><input type="radio" name="obligatoire" value="1" required >oui</label>
                                </div>
                                <div class="radio obligatoire-non">
                                    <label ><input type="radio" name="obligatoire" value="0" required >non</label>
                                </div>
                            </div>
                        </div>

                        <input type="hidden" name="tache" value="effectuerCreationLecture">
                        <label class="lecture-submit"><input type="submit" class="btn btn-success" value="Ajouter" ></input></label>
                        <a href="*.do?tache=afficherPageGestionLecture" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des lectures</a>
                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>