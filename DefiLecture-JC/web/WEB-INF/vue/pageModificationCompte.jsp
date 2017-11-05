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

<script>  
$(document).ready(
        function () {
                //alert('Le DOM est pret!');
                $("#prenom").keyup(function(event) {
                        $('#testAjax5').load('*.do?tache=testAjax&prenom='+event.target.value);
                    }
                );
        }
);  
</script>

<%  CompteDAO dao = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    pageContext.setAttribute("compte", dao.read(request.getParameter("id"))); %>

    <body>
        <h1>Page de configuration</h1>
        <span>${requestScope.message}</span>


        <div style="background-image: url('<c:url value='${compte.avatar}'/>')"></div>

        <img class="img-responsive avatar" src="<c:url value='${compte.avatar}'/>" alt="Avatar">
        <form method="POST" action=".do" enctype="multipart/form-data">
            Avatar :
            <input type="file" name="nomFichier" id="file"  accept="image/*"/> <br/>
            <input type="hidden" name="tache" value="effectuerAjoutAvatarCompte" />
            <input type="submit" value="T&eacute;l&eacute;verser" name="upload" id="upload" />
        </form>

        <form action="modification.do" method="post" >
            <div>
                Prenom* : <input type="text" id=prenom name="prenom" value="${compte.prenom}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>

                Nom* : <input type="text" name="nom" value="${compte.nom}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
            </div>
            <div>
                Programme d'&eacute;tude : <input type="text" name="programmeEtude" value="${compte.programmeEtude}" ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
                Courriel* : <input type="email" name="courriel" value="${compte.courriel}" required ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
            </div>
            <div>
                Pseudonyme : <input type="text" name="pseudonyme" value="${compte.pseudonyme}" ${ compte.idCompte eq session.connecte ? '':'readonly' }/>
            </div>

            <div>R&ocirc;le du compte :
                <c:set var="selected" value=" selected=\"selected\"" />
                <select name="role">
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
                <input type="submit" name="modifie" value=" Enregistrer" />
                <input type="submit" name="annule" value=" Annuler" />
            </div>
        </form>

        <div class="form" action="suppression.do">
            <form>
                <input type="hidden" name="idCompte" value="${compte.idCompte}">
                <input type="submit" name="supprime" value=" Supprimer" />
            </form>
        </div>

        <div id="testAjax5"></div>

    </body>
