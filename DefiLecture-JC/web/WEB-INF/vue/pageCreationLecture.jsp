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
                $('.duree15').click(function() {
                    $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    $('.duree30').css('border', 'transparent');
                    $('.duree45').css('border', 'transparent');
                    $('.duree60').css('border', 'transparent');
                    
                });
                $('.duree30').click(function() {
                     $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    $('.duree15').css('border', 'transparent');
                    $('.duree45').css('border', 'transparent');
                    $('.duree60').css('border', 'transparent');


                });
                $('.duree45').click(function() {
                    $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    $('.duree30').css('border', 'transparent');
                    $('.duree15').css('border', 'transparent');
                    $('.duree60').css('border', 'transparent');


                });
                $('.duree60').click(function() {
                     $(this).css('border', '3px dotted goldenrod');
                    $(this).css('border-radius', '37px');
                    $('.duree30').css('border', 'transparent');
                    $('.duree45').css('border', 'transparent');
                    $('.duree15').css('border', 'transparent');


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
                                <div class="dureeLecture">
                                <div class="radio">
                                    <label class="duree15" id="duree15"><input  type="radio" name="dureeMinutes" value="15" required ></label>
                                </div>
                                   <div class="radio">
                                    <label class="duree30"><input type="radio" name="dureeMinutes" value="30" required ></label>
                                </div>
                                   <div class="radio">
                                    <label class="duree45"><input type="radio" name="dureeMinutes" value="45" required ></label>
                                </div>
                                   <div class="radio">
                                    <label class="duree60"><input type="radio" name="dureeMinutes" value="60" required ></label>
                                </div>
                             
                            </div>
                        </div>
                        
                        <div class="lectureObligatoire">
                            <div class="form-group">
                                <label for="obligatoire">La lecture était-elle obligatoire? : </label>

                                <div class="radio">
                                    <label><input type="radio" name="obligatoire" value="1" required >oui</label>
                                </div>
                                <div class="radio">
                                    <label><input type="radio" name="obligatoire" value="0" required >non</label>
                                </div>
                            </div>
                        </div>

                        <input type="hidden" name="tache" value="effectuerCreationLecture">
                        <button type="submit" class="btn btn-success" value="Ajouter" >Ajouter</button>
                        
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>