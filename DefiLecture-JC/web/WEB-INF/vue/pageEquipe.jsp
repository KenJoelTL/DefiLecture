<%-- 
    Document   : profilEquipe
    Created on : 2017-10-14
    Author     : Joel
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.defiLecture.modele.DemandeEquipeDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.EquipeDAO"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="com.defiLecture.modele.Equipe"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>


<c:if test="${empty param.idEquipe}">${param.idEquipe=1}</c:if>
<%  Connection cnx = Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER);
    EquipeDAO daoEquipe = new EquipeDAO(cnx);
    Equipe equipe = daoEquipe.read(request.getParameter("idEquipe"));
    int rang = daoEquipe.findAll().indexOf(equipe)+1;
    DemandeEquipeDAO daoDemEqp = new DemandeEquipeDAO(cnx);
    CompteDAO daoCompte = new CompteDAO(cnx);
    pageContext.setAttribute("rang", rang);
    if(session.getAttribute("connecte") != null)
        pageContext.setAttribute("compteConnecte", daoCompte.read((int)session.getAttribute("connecte")));
    pageContext.setAttribute("daoDemEqp", daoDemEqp);
    pageContext.setAttribute("equipe", equipe);
    pageContext.setAttribute("listeMembres", daoCompte.findByIdEquipe(equipe.getIdEquipe())); %>
    <div class='row'> 
        
            <div class="col-sm-12 col-lg-12 col-xs-12 col-md-12 page-equipe-col">
 
            
                <div class='jumbotron'> 
                    
                    <h1>${equipe.nom}</h1>
                    <p>${equipe.point} pts</p>
                    <p>Rang #${rang}</p>
             
                </div>
                    
              
                        <c:if test="${(!empty sessionScope.connecte) and (compteConnecte.idEquipe eq equipe.idEquipe)}">
                            <a id="parametreEquipe" href="depart.do?tache=afficherPageModificationEquipe&idEquipe=${equipe.idEquipe}">
                                Paramètres <span class="glyphicon glyphicon-cog"></span>
                            </a>
                        </c:if>
                    <h2>Membres de l'équipe</h2>
                    <table class='table table-hover'>
                  <thead>
                    <tr>
                      <th>Prénom</th>
                      <th>Nom</th>
                      <th>Contributions</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${listeMembres}" var="membre">      
                    <tr>
                      <td>${membre.prenom}</td>
                      <td>${membre.nom}</td>
                      <td>
                          <c:set var="contribution" value="${daoDemEqp.findByIdCompteEquipe(membre.idCompte,equipe.idEquipe)}"></c:set>
                        <div class="progress">
                          <div class="progress-bar" role="progressbar" aria-valuenow="${contribution.point}"
                            aria-valuemin="0" aria-valuemax="100" style="width:${(contribution.point/equipe.point)*100}%">
                          </div>
                        </div>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>

               </div>
        </div>
            
   

