<%-- 
    Document   : profilEquipe
    Created on : 2017-10-14
    Author     : Joel
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty requestScope}">${requestScope.idEquipe=1}</c:if>
<%@page import="modele.Equipe"%>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.EquipeDAO"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<style>
    #toutPageEquipe { background-image: url("ocean.jpg");
                      background-repeat: no-repeat;
                      background-position: right top;
                      background-attachment: fixed;
                      z-index: -1;
    }
</style>

<%  EquipeDAO daoEquipe = new EquipeDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
    Equipe equipe = daoEquipe.read(request.getParameter("idEquipe"));
    CompteDAO daoCompte = new CompteDAO(Connexion.startConnection(Config.DB_USER, Config.DB_PWD, Config.URL, Config.DRIVER));
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

                        <div class='col-sm-6' ><p>Rang</p><p>#8</p></div>
                    </div>
                    </div>
                </div>
            
            </div>
        </div>
        
        <div class='container'>
            <div class='col-md-1'></div>
            
            <div class='col-lg-10 offset-md-3' style='margin-top: 15px'>
                <div class="panel panel-default">
                    <div class="panel-heading" style="background-color: #253849; color: #e9e9e9;">Performance</div>
                    <div class="panel-body">Panel Content</div>
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
                        <div class="progress">
                          <div class="progress-bar" role="progressbar" aria-valuenow="${membre.pointage}"
                            aria-valuemin="0" aria-valuemax="100" style="width:${(membre.pointage/equipe.point)*100}%">
                          </div>
                        </div>
                      </td>
                    </tr>
                    </c:forEach>
                    <tr>
                      <td>Mary</td>
                      <td>Moe</td>
                      <td>
                          <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="10"
                            aria-valuemin="0" aria-valuemax="100" style="width:10%">
                            </div>
                          </div>
                      </td>
                    </tr>
                    <tr>
                      <td>July</td>
                      <td>Dooley</td>
                      <td>
                          <div class="progress">
                            <div class="progress-bar" role="progressbar" aria-valuenow="40"
                            aria-valuemin="0" aria-valuemax="100" style="width:40%">
                            </div>
                          </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
                
            </div>
        </div>
            
   

