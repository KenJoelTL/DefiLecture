<%-- 
    Document   : gestionConfigurationCompte
    Created on : 2017-10-22, 18:19:46
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>


 <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
    <jsp:useBean id="dao" class="com.defiLecture.modele.CompteDAO">
        <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>
    <c:set var="compte" scope="page" value="${dao.read(param.id)}"/>
    
        
<body>
    <div class='row'> 
        <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 modification-compte-col">
           <div class="modification-compte-form">
                <h1>Page de configuration</h1>
                <c:if test="${!empty requestScope.data['succesModification']}">
                    <div class="alert alert-success"><strong>${requestScope.data['succesModification']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurCourriel']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurCourriel']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurPseudonyme']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurPseudonyme']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurModification']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurModification']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['succesAvatar']}">
                    <div class="alert alert-success"><strong>${requestScope.data['succesAvatar']}</strong></div>
                </c:if>
                <c:if test="${!empty requestScope.data['erreurAvatar']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurAvatar']}</strong></div>
                </c:if>
                    

                <div style="background-image: url('<c:url value='${compte.avatar}'/>')"></div>

                <img class="img-responsive avatar" src="<c:url value='${compte.avatar}'/>" alt="Avatar">
                <form method="POST" action=".do" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="nomFichier">Avatar : </label>
                        <input type="file" name="nomFichier" id="file"  accept="image/*"/> <br/>
                        <input type="hidden" name="tache" value="effectuerAjoutAvatarCompte" />
                        <button id="upload" name="upload" class="btn btn-primary">T&eacute;l&eacute;verser</button>
                    </div>
                </form>


                <form action="modification.do" method="post" >
                    <div class="form-group">
                        <label for="prenom">Pr&eacute;nom* : </label>
                        <input type="text" class="form-control" id=prenom name="prenom" value="${compte.prenom}" required ${ compte.idCompte eq sessionScope.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                        <label for="nom">Nom* : </label>
                        <input type="text" class="form-control" name="nom" value="${compte.nom}" required ${ compte.idCompte eq sessionScope.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                        <label for="programmeEtude">Programme d'&eacute;tude ou poste occup&eacute; au coll&egrave;ge: </label>
                         <input type="text" class="form-control" name="programmeEtude" value="${compte.programmeEtude}" ${ compte.idCompte eq sessionScope.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                         <label for="courriel">Courriel* : </label>
                        <input type="email" class="form-control" name="courriel" value="${compte.courriel}" required ${ compte.idCompte eq sessionScope.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                        <label for="pseudonyme">Pseudonyme : </label>
                        <input type="text" class="form-control"  name="pseudonyme" value="${compte.pseudonyme}" ${ compte.idCompte eq sessionScope.connecte ? '':'readonly' }/>
                    </div>

                    <c:if test="${sessionScope.role gt 3}">
                    <div class="form-group">
                        <label for="role">R&ocirc;le du compte : </label>
                        <c:set var="selected" value=" selected=\"selected\"" />
                        <select name="role" class="form-control">
                            <option value="1" ${ compte.role eq 1 ? selected:'' }>Participant</option>
                            <option value="2" ${ compte.role eq 2 ? selected:'' }>Capitaine</option>
                            <c:if test="${sessionScope.role eq 4}">
                            <option value="3" ${ compte.role eq 3 ? selected:'' }>Modérateur</option>
                            <option value="4" ${ compte.role eq 4 ? selected:'' }>Administrateur</option>
                            </c:if>
                        </select>
                    </div>
                    </c:if>
                    <div>
                    <input type="hidden" name="idCompte" value="${compte.idCompte}">
                    <input type="hidden" name="tache" value="effectuerModificationCompte">
                    <button type="submit" class="btn btn-success" name="modifie" >Enregistrer</button>
                    
                    </div>
                </form>

                <div class="form" >
                    <form action="suppression.do">
                        <input type="hidden" name="idCompte" value="${compte.idCompte}"/>
                        <input type="hidden" name="tache" value="effectuerSuppressionCompte"/>
                        <button class="btn btn-danger" type="submit">Supprimer</button>
         
                    </form>
                </div>
                        <a href="*.do?tache=afficherPageGestionListeCompte" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des comptes</a>
           </div>
        </div>
    </div>
</body>
