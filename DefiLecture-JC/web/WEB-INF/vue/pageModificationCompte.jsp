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
                <span>${requestScope.message}</span>


                <div style="background-image: url('<c:url value='${compte.avatar}'/>')"></div>

                <img class="img-responsive avatar" src="<c:url value='${compte.avatar}'/>" alt="Avatar">
                <form method="POST" action=".do" enctype="multipart/form-data">
                    <div class="form-group">
                    <label for="nomFichier">Avatar : </label>
                    <input type="file" name="nomFichier" id="file"  accept="image/*"/> <br/>
                    <input type="hidden" name="tache" value="effectuerAjoutAvatarCompte" />
                    <input type="submit" value="T&eacute;l&eacute;verser" name="upload" id="upload" />
                      </div>


                </form>


                <form action="modification.do" method="post" >
                    <div class="form-group">
                        <label for="prenom">Pr&eacute;nom* : </label>
                        <input type="text" class="form-control" id=prenom name="prenom" value="${compte.prenom}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                        <label for="nom">Nom* : </label>
                        <input type="text" class="form-control" name="nom" value="${compte.nom}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                        <label for="programmeEtude">Programme d'&eacute;tude : </label>
                         <input type="text" class="form-control" name="programmeEtude" value="${compte.programmeEtude}" ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                    </div>
                    <div class="form-group">
                         <label for="courriel">Courriel* : </label>
                        <input type="email" class="form-control" name="courriel" value="${compte.courriel}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                    </div>
                   <div class="form-group">
                        <label for="pseudonyme">Pseudonyme : </label>
                        <input type="text" class="form-control"  name="pseudonyme" value="${compte.pseudonyme}" ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                    </div>

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
                    <div>
                    <input type="hidden" name="idCompte" value="${compte.idCompte}">
                    <input type="hidden" name="tache" value="effectuerModificationCompte">
                    <button type="submit" class="btn btn-success" name="modifie" >Enregistrer</button>
                    
                    </div>
                </form>

                <div class="form" action="suppression.do">
                    <form>
                        <input type="hidden" name="idCompte" value="${compte.idCompte}">
                        <button class="btn btn-danger" type="submit" name="supprime"  >Supprimer</button>
         
                    </form>
                </div>
                        <a href="*.do?tache=afficherPageGestionListeCompte" class="retour"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des comptes</a>
           </div>
        </div>
    </div>
</body>
