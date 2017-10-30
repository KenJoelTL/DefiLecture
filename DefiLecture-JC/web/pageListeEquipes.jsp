<%-- 
    Document   : pageListeEquipes
    Created on : 2017-10-24, 20:46:22
    Author     : Joel
--%>

<%@page import="modele.DemandeEquipeDAO"%>
<%@page import="modele.CompteDAO"%>
<%@page import="java.sql.Connection"%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%  Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    EquipeDAO dao = new EquipeDAO(cnx);
    CompteDAO daoCompte = new CompteDAO(cnx);
    DemandeEquipeDAO daoDemEq = new DemandeEquipeDAO(cnx);
    pageContext.setAttribute("compteConnecte", daoCompte.read((int)session.getAttribute("connecte")));
    pageContext.setAttribute("daoDemEq", daoDemEq);
    pageContext.setAttribute("listeEquipes", dao.findAll());   %>
    
<h2>Liste des équipes</h2>  

    <table class="table">
      <thead>
        <tr>
          <th>Nom</th>
          <th>Points</th>
          <th>État</th>
        </tr>
      </thead>
      
      <c:set var="i" value="0"/>          
      <tbody>
      <c:forEach items="${listeEquipes}" var="equipe">          
        <tr>
          <c:if test="${(compteConnecte.idEquipe eq -1) and (equipe.nbMembres lt 3)}">
          <td><a href="pageEquipe.do?afficherPageEquipe&idEquipe=${equipe.idEquipe}">${equipe.nom}</a></td>
          <td>${equipe.point} points</td>
           <td>
            <c:set var="demande" value="${daoDemEq.findByIdCompteEquipe(compteConnecte.idCompte,equipe.idEquipe)}"/>          
            <c:choose>
                
            <c:when test="${empty demande or demande.statutDemande eq 0}">
               <a href="demande.do?tache=effectuerDemandeAdhesionEquipe&idCompte=${compteConnecte.idCompte}&idEquipe=${equipe.idEquipe}">
                   Envoyer une demande d'adhésion
               </a>
            </c:when>
            <c:otherwise>
                <span>Envoyée</span>
            </c:otherwise>
            </c:choose>

           </td>    
          </c:if>
        </tr>
      </c:forEach>  
      </tbody>
      
    </table>
