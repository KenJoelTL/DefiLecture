<%-- 
    Document   : pageGestionConfigurationEquipe
    Created on : 2017-11-01, 20:52:35
    Author     : Joel
--%>
<%@page import="modele.DemandeEquipeDAO"%>
<%@page import="modele.Compte"%>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.Equipe"%>
<%@page import="modele.EquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Connexion"%>
<%@page import="jdbc.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
   Equipe equipe;
   CompteDAO compteDao = new CompteDAO(cnx); 
    Compte compteConnecte = compteDao.read((int)(session.getAttribute("connecte"))); //vérifier si son équipe n'Est pas null
   if(request.getParameter("idEquipe") != null)
       equipe = new EquipeDAO(cnx).read(request.getParameter("idEquipe"));
   else{
       
       equipe =new EquipeDAO(cnx).read(compteConnecte.getIdEquipe());
   }
    DemandeEquipeDAO demEquipeDao = new DemandeEquipeDAO(cnx); 
    pageContext.setAttribute("equipe", equipe);
    pageContext.setAttribute("listeMembres", new CompteDAO(cnx).findByIdEquipe(equipe.getIdEquipe()));
    pageContext.setAttribute("demEquipeDao", demEquipeDao);      
    pageContext.setAttribute("compteConnecte", compteConnecte);      
%>

<c:set var="permissionAccordee" value="${((sessionScope.role eq Compte.CAPITAINE) 
                                    and (compteConnecte.idEquipe eq equipe.idEquipe)) or (sessionScope.role eq Compte.ADMINISTRATEUR)}"></c:set>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    </head>
    <body>
        <h1>Configuration d'une &eacute;quipe</h1>
        <form action="modificationEquipe.do" method="post">
        <div class="form-group">
        <label for="nom">Nom de l'&eacute;quipe* :</label>
        <input type="text" class="form-control" id="nom" value="${equipe.nom}" 
               placeholder="Entrez le nouveau nom de votre &eacute;quipe" name="nom" ${permissionAccordee ? 'required': 'disabled'}/>
        </div>
        <c:if test="${permissionAccordee}">
            <input type="hidden" name="tache" value="effectuerModificationEquipe">
            <input type="hidden" name="idEquipe" value="${equipe.idEquipe}">
            <input type="submit"name='modifier' value="Enregistrer" />
            <input type="submit" value="annuler" />
        </c:if> 
        </form>
        
        <table class='table table-hover' style="background-color: rgb(255, 255, 255); border:1px lightgray solid">
            <thead>
                <tr>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Contributions</th>
                    <th></th>
                    <th></th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${listeMembres}" var="membre">      
                <tr>
                    <td>${membre.prenom}</td>
                    <td>${membre.nom}</td>
                    <td>
                        <c:set var="contribution" value="${demEquipeDao.findByIdCompteEquipe(membre.idCompte,equipe.idEquipe)}"></c:set>
                        <div class="progress">
                          <div class="progress-bar" role="progressbar" aria-valuenow="${contribution.point}"
                            aria-valuemin="0" aria-valuemax="100" style="width:${(contribution.point/equipe.point)*100}%">
                          </div>
                        </div>
                    </td> 
                    <td>
                        <c:if test="${permissionAccordee}"> 
                        <td>
                            <c:choose>
                                <c:when test="${contribution.statutDemande eq 0}">                             
                                    <a href="*.do?tache=effectuerReaffectationMembreEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">R&eacute;afecter &agrave; l'&eacute;quipe</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="*.do?tache=effectuerSuspensionMembreEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">Suspendre de l'&eacute;quipe</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        </c:if>
                        <c:if test="${permissionAccordee or (sessionScope.connecte eq membre.idCompte)}">
                        <td>
                            <a href="depart.do?tache=effectuerDepartEquipe&idCompte=${membre.idCompte}&idEquipe=${equipe.idEquipe}">
                                Retirer de l'équipe
                            </a>
                        </td>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        
        
        
        
    </body>
</html>
