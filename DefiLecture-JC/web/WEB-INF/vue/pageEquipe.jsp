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
<style>
    #toutPageEquipe { background-image: url("<c:url value='/images/arriere-plans/ocean.jpg'/>");
                      background-repeat: no-repeat;
                      background-position: right top;
                      background-attachment: fixed;
                      z-index: -1;
    }
</style>

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
    
        <div id='toutPageEquipe' style='background-color: rgba(51, 122, 183, 0.5);'>    
        
            <div class='container-fluid'>
            
                <div class='jumbotron' style=" background-color: rgba(34, 34, 34, 0.78); color: whitesmoke;"> 
                    <div class='row'>
                        
                    <div class='col-md-1' ></div>
                    <div class='col-md-6' >
                        <p>Page de l'équipe<p>
                        <p>${equipe.nom}<p>
                    </div>
                    <div class='col-md-5' >
                        <div class='col-sm-6' ><p>Pointage Courant</p><p>${equipe.point} pts</p></div>

                        <div class='col-sm-6' ><p>Rang</p><p>#${rang}</p></div>
                    </div>
                    </div>
                </div>
            
            </div>
        </div>
        
        <div class='container'>
            <div class='col-md-1'></div>
            
            <div class='col-lg-10 offset-md-3' style='margin-top: 15px'>
                <div class="panel panel-default">
                    <div class="panel-heading" style="background-color: #253849; color: #e9e9e9;">Performances
                        <c:if test="${(!empty sessionScope.connecte) and (compteConnecte.idEquipe eq equipe.idEquipe)}">
                            <a href="depart.do?tache=afficherPageModificationEquipe&idEquipe=${equipe.idEquipe}">
                                Paramètres <span class="glyphicon glyphicon-cog"></span>
                            </a>
                        </c:if>
                    
                    </div>
                    <div class="panel-body">Dernière nouvelle !</div>
                </div>
                
                <table class='table table-hover' style="background-color: rgb(255, 255, 255); border:1px lightgray solid">
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
            
   

