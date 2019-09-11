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
    Document   : gestionConfigurationCompte
    Created on : 2017-10-22, 18:19:46
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defilecture.modele.Compte"%>
<%@page import="com.defilecture.modele.CompteDAO"%>
<%@page import="com.defilecture.modele.Equipe"%>
<%@page import="com.defilecture.modele.EquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>


 <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
    <jsp:useBean id="dao" class="com.defilecture.modele.CompteDAO">
        <jsp:setProperty name="dao" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>
    <c:set var="compte" scope="page" value="${dao.read(param.id)}"/>
    <jsp:useBean id="daoEquipe" class="com.defilecture.modele.EquipeDAO">
        <jsp:setProperty name="daoEquipe" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>    
    <c:set var="equipe" scope="page" value="${daoEquipe.read(compte.idEquipe)}"/>

    <script>
     function genererMotDePasse(){
	 mdp=Math.random().toString(36).substring(2, 15);
	 c_mdp=document.getElementById('motPasseNouveau')
	 c_mdp.type='text';
	 c_mdp.value=mdp;
	 c_mdpconf=document.getElementById('motPasseNouveauConfirmation')
	 c_mdpconf.type='text';
	 c_mdpconf.value=mdp;
     }
    </script>

<body>
    <div class='row connexion-row'> 
        <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 connexion-col modification-compte-col">
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
                <c:if test="${!empty requestScope.data['erreurMotPasse']}">
                    <div class="alert alert-danger"><strong>${requestScope.data['erreurMotPasse']}</strong></div>
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
                <h2 style="text-align:center">${compte.prenom} ${compte.nom}</h2>
                <c:if test="${ !empty equipe }">
                    <h2 style="text-align:center">De  <% out.println(application.getAttribute("vocEquipe4"));%> <a href="pageEquipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></h2>                
                </c:if>

                <form method="POST" action=".do" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="nomFichier">Avatar : </label>
                        <input type="file" name="nomFichier" id="file"  accept="image/*"/> <br/>
                        <input type="hidden" name="tache" value="effectuerAjoutAvatarCompte" />
                        <button id="upload" name="upload" class="btn btn-primary">T&eacute;l&eacute;verser</button>
                    </div>
                </form>

                <form action="modification.do" method="post" >
                    <div class="form-group col-lg-6 col-md-6">
                        <label for="prenom">Pr&eacute;nom*</label>
                        <input type="text" class="form-control" id=prenom name="prenom" value="${compte.prenom}" required  }/>
                    </div>
                    <div class="form-group col-lg-6 col-md-6">
                        <label for="nom">Nom*</label>
                        <input type="text" class="form-control" name="nom" value="${compte.nom}" required  }/>
                    </div>
                    <div class="form-group col-lg-12">
                        <label for="programmeEtude">Programme d'&eacute;tude ou poste occup&eacute; au coll&egrave;ge</label>
                         <input type="text" class="form-control" name="programmeEtude" value="${compte.programmeEtude}"  }/>
                    </div>
                    <div class="form-group col-lg-6 col-md-6">
                         <label for="courriel">Courriel*</label>
                        <input type="email" class="form-control" name="courriel" value="${compte.courriel}" required  }/>
                    </div>
                    <div class="form-group col-lg-6 col-md-6">
                        <label for="pseudonyme">Pseudonyme</label>
                        <input type="text" class="form-control"  name="pseudonyme" value="${compte.pseudonyme}"  }/>
                    </div>
                    
                    
                    <c:if test="${sessionScope.role gt 3}">
                    <div class="form-group col-lg-12"> 
                        <label for="role">R&ocirc;le du compte : </label>
                        <c:set var="selected" value=" selected=\"selected\"" />
                        <select name="role" class="form-control">
                            <option value="1" ${ compte.role eq 1 ? selected:'' }>Participant</option>
                            <option value="2" ${ compte.role eq 2 ? selected:'' }><% out.println(application.getAttribute("vocChef"));%></option>
                            <c:if test="${sessionScope.role eq 4}">
                            <option value="3" ${ compte.role eq 3 ? selected:'' }>Mod&eacute;rateur</option>
                            <option value="4" ${ compte.role eq 4 ? selected:'' }>Administrateur</option>
                            </c:if>
                        </select>
                    </div>
                    </c:if>
                    <div class="form-group col-lg-12">
                        <input type="hidden" name="idCompte" value="${compte.idCompte}">
                        <input type="hidden" name="tache" value="effectuerModificationCompte">
                        <button type="submit" class="btn btn-success" name="modifie" >Enregistrer</button>
                    </div>
                </form>

                <form action="modification.do" method="post" >                     
                    <div class="form-group col-lg-12">
			<c:if test="${sessionScope.role ne Compte.ADMINISTRATEUR}">
                            <label for="motPasseActuel">Mot de passe actuel*</label>
                            <input type="password" class="form-control" name="motPasseActuel" required/>
			</c:if>
                    </div>
                    <div class="form-group col-lg-12">
                        <label for="motPasseNouveau">Nouveau mot de passe*</label>
			<input type="password" class="form-control" name="motPasseNouveau" id="motPasseNouveau"/>
                    </div>
                    <div class="form-group col-lg-12">
                        <label for="motPasseNouveauConfirmation">Confirmation du nouveau mot de passe*</label>
			<input type="password" class="form-control" name="motPasseNouveauConfirmation" id="motPasseNouveauConfirmation"/>
                    </div>                        
                    <div class="form-group col-lg-12">
                        <input type="hidden" name="idCompte" value="${compte.idCompte}"/>
                        <input type="hidden" name="tache" value="effectuerModificationCompte"/>
                        <button class="btn btn-primary" type="submit" name="modifie">Modifier le mot de passe</button>
                    </div>
                </form>
                        
                <c:if test="${compte.idCompte ne sessionScope.connecte and sessionScope.role eq Compte.ADMINISTRATEUR}">
                        <div class="form-group col-lg-12">
                            <button class="btn btn-info" type="button" name="genere" onclick="genererMotDePasse();">Générer un mot de passe</button>
                        </div>
                </c:if>
                                

                <c:if test="${sessionScope.role eq Compte.ADMINISTRATEUR}">
                    <form action="suppression.do" method="post">
                        <input type="hidden" name="idCompte" value="${compte.idCompte}"/>
                        <input type="hidden" name="tache" value="effectuerSuppressionCompte"/>
                        <div class="form-group">
                            <button class="btn btn-danger" type="submit">Supprimer</button>
                        </div>
                    </form>

                    <a href="*.do?tache=afficherPageGestionListeCompte" class="retour modifCompte"><span class="glyphicon glyphicon-circle-arrow-left"></span>retour à la liste des comptes</a>
                </c:if>
           </div>
        </div>
    </div>
</body>
