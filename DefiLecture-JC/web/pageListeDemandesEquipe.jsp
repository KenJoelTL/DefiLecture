<%-- 
    Document   : pageListeDemandesEquipe
    Created on : 2017-10-28, 08:15:58
    Author     : Joel
--%>
<%@page import="modele.Compte"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.connecte or !(sessionScope.role eq 2)}">
    <jsp:forward page="*.do&tache=afficherPageAccueil"></jsp:forward>  
</c:if>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page pageEncoding="UTF-8"%>


  
<% Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DemandeEquipeDAO eqDao = new DemandeEquipeDAO(cnx);
    CompteDAO cptDao = new CompteDAO(cnx);
    Compte compte = new Compte();
    //String idEquipe;
    if(request.getParameter("idEquipe")==null){
        compte = cptDao.read((int)session.getAttribute("connecte"));
        //idEquipe = compte.getIdEquipe()+"";
    }
//    else
  //      idEquipe = request.getParameter("idEquipe");
    pageContext.setAttribute("cptDao", cptDao);
    pageContext.setAttribute("listeDemandes", eqDao.findByIdEquipe(compte.getIdEquipe()));
%>


<h2>Liste des demandes</h2>  

    <table class="table">
        
      <thead>
        <tr>
          <th>Demande d'adh&eacute;sion</th>
          <th>&Eacute;tat de la demande</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
    <c:set var="etat" value="----"></c:set>        

      <c:forEach items="${listeDemandes}" var="demande">
        <c:set var="auteur" value="${cptDao.read(demande.idCompte)}"></c:set> 


        <tr>
          <td>Demande envoy&eacute;e par ${auteur.prenom} ${auteur.nom}</td>
            <td>
                <c:if test="${sessionScope.role eq 2 and demande.statutDemande eq -1}">
                  <a href="accepter.do?tache=accepterDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Accepter</a>
                  <a href="refuser.do?tache=refuserDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Refuser</a>
                </c:if>
            </td>
        </tr>
      
      </c:forEach>   
      
      </tbody>
        
    </table>

