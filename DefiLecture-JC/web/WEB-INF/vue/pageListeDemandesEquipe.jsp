<%--
    Document   : pageListeDemandesEquipe
    Created on : 2017-10-28, 08:15:58
    Author     : Joel
--%>
<%@page import="com.defiLecture.modele.DemandeEquipe"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty sessionScope.connecte or (!(sessionScope.role eq 2) and (requestScope.ordre eq 'recu'))}">
    <jsp:forward page="*.do?tache=afficherPageAccueil"></jsp:forward>
</c:if>


<%  Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    DemandeEquipeDAO demandeEqDao = new DemandeEquipeDAO(cnx);

    if("recu".equals(request.getParameter("ordre"))){
        CompteDAO cptDao = new CompteDAO(cnx);
        Compte compte = new Compte();
        if(request.getParameter("idEquipe")==null){
            compte = cptDao.read((int)session.getAttribute("connecte"));
        }
        pageContext.setAttribute("cptDao", cptDao);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdEquipeStatutDemande(compte.getIdEquipe(),DemandeEquipe.EN_ATTENTE));
    }
    else{
        EquipeDAO eqpDao = new EquipeDAO(cnx);
        pageContext.setAttribute("equipeDao", eqpDao);
        pageContext.setAttribute("listeDemandes", demandeEqDao.findByIdCompte((int)session.getAttribute("connecte")));
    }
%>


 <div class="row demandes-row"> 
    <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 demandes-col">
        <h2>Liste des demandes</h2>

            <table class="table">

              <thead>
                <tr>
                  <th>Demandes d'adh&eacute;sion</th>
                  <th>&Eacute;tat de la demande</th>
                  <th></th>
                </tr>
              </thead>

              <tbody>
              <c:choose>

              <c:when test="${(param.ordre eq 'recu') and (sessionScope.role eq Compte.CAPITAINE) }">
               <c:forEach items="${listeDemandes}" var="demande">

                 <c:set var="auteur" value="${cptDao.read(demande.idCompte)}"></c:set>
                 <tr>
                    <td>Demande envoy&eacute;e par ${auteur.prenom} ${auteur.nom}</td>
                    <td>
                        <a href="accepter.do?tache=effectuerAcceptationDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Accepter</a>
                        <a href="refuser.do?tache=effectuerSuppressionDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Refuser</a>
                    </td>
                 </tr>
               </c:forEach>
              </c:when>


              <c:otherwise>
               <c:forEach items="${listeDemandes}" var="demande">
                <c:set var="equipe" value="${equipeDao.read(demande.idEquipe)}"></c:set>
                <tr>
                <c:choose>
                    <c:when test="${demande.statutDemande eq DemandeEquipe.ACCEPTEE}">
                    <td>
                        <span>Demande Acceptée !</span>
                    </td>
                    </c:when>
                    <c:otherwise>
                    <td>
                        <span>Demande envoy&eacute;e &agrave; l'&eacute;quipe 
                            <a href="equipe.do?tache=afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a>
                        </span>
                    </td>
                    <td>
                        <a href="refuser.do?tache=effectuerSuppressionDemandeAdhesion&idDemandeEquipe=${demande.idDemandeEquipe}">Annuler</a>
                    </td>
                    </c:otherwise>
                </c:choose>
                </tr>
               </c:forEach>
              </c:otherwise>

              </c:choose>
              </tbody>

            </table>
      </div>
 </div>

