<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />

<div class='container-fluid'  style="margin-bottom: 90px" >
  <nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand logo-navigation" href='*.do?tache=afficherPageMarcheASuivre'></a>
        <!-- Apparait lorsque la fenêtre devient de la taille d'un téléphone mobile -->
        <button id="btn-burger" type="button" class="navbar-toggle" data-toggle="collapse" data-target="#optionsNavigation">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
      </div>

      <!-- Options contenues dans le bouton à son activation -->
      <div class="collapse navbar-collapse" id="optionsNavigation">
        <ul class="nav navbar-nav">
          <c:if test="${ !empty sessionScope.connecte && sessionScope.role le 2 }">
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dLecture']}" var="datedebut" type="both"/>
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>
            <div id="menuLectures" style="display: ${now ge datedebut && now lt datefin ? 'block' : 'none'}">
              <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Lectures
                <span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li><a href="*.do?tache=afficherPageCreationLecture">Ajouter une lecture</a></li>
                  <li><a href="*.do?tache=afficherPageGestionLecture">Voir mes lectures</a></li>
                </ul>
              </li>
            </div>
          </c:if>
          <li>
            <a href="scoreboard.do?tache=afficherPageTableauScores">
              <% out.println(application.getAttribute("vocBanque"));%>
            </a>
          </li>
          <c:choose>
            <c:when test="${ !empty sessionScope.connecte }">
              <c:choose>
                <c:when test="${ (sessionScope.role eq 2) }">
                  <c:choose>
                    <c:when test="${compteConnecte.idEquipe gt -1}">
                      <li>
                        <a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                          Page <% out.println(application.getAttribute("vocEquipe1"));%>
                        </a>
                      </li>
                      <li>
                        <a href="joindreEquipe.do?tache=afficherPageListeDemandesEquipe&ordre=recu">
                          Acc&eacute;der aux demandes</a>
                      </li>
                    </c:when>
                    <c:otherwise>
                      <li>
                        <a href="creationEquipe.do?tache=afficherPageCreationEquipe">
                          Cr&eacute;er <% out.println(application.getAttribute("vocEquipe3"));%>
                        </a>
                      </li>
                    </c:otherwise>
                  </c:choose>
                </c:when>
                <c:otherwise>
                  <c:choose>
                    <c:when test="${sessionScope.role < 3}">
                      <c:choose>
                        <c:when test="${compteConnecte.idEquipe > -1}">
                          <li>
                            <a href="affichagePageEquipe.do?tache=afficherPageEquipe&idEquipe=${compteConnecte.idEquipe}">
                              Page <% out.println(application.getAttribute("vocEquipe1"));%>
                            </a>
                          </li>
                        </c:when>
                        <c:otherwise>
                          <li>
                            <a href="joindreEquipe.do?tache=afficherPageListeEquipes">
                              Joindre <% out.println(application.getAttribute("vocEquipe3"));%>
                            </a>
                          </li>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                  </c:choose>
                </c:otherwise>
              </c:choose>
            </c:when>
            <c:otherwise>
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dInscription']}" var="datedebut" type="both"/>
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>
              <div id="menuInscription" style="display: ${now ge datedebut && now lt datefin ? 'block' : 'none'}">
                <li>
                  <a href='*.do?tache=afficherPageInscription'>
                    S'inscrire
                  </a>
                </li>
              </div>
            </c:otherwise>
          </c:choose>

          <c:if test="${ sessionScope.role eq 4 }">
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                Comptes<span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li ><a href="*.do?tache=afficherPageGestionListeCompte">G&eacute;rer les comptes</a></li>
                <li ><a href="*.do?tache=afficherPageAdresseCourriel">Liste d'adresses</a></li>
                <li ><a href="*.do?tache=afficherPageListeLectures">Liste des lectures</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                &Eacute;quipes<span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li >
                  <a href="*.do?tache=afficherPageGestionListeEquipes">
                    G&eacute;rer les &eacute;quipes</a>
                </li>
              </ul>
            </li>
          </c:if>

          <c:if test="${ sessionScope.role ge 1 }">
            <c:if test="${!empty sessionScope.connecte}">
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dLecture']}" var="datedebut" type="both"/>
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>
              <div id="menuDefis" style="display: ${now ge datedebut && now lt datefin ? 'block' : 'none'}">
                <li class="dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    Épreuves<span class="caret"></span>
                  </a>
                  <ul class="dropdown-menu">
                    <c:if test="${ sessionScope.role ge 3 }">
                      <li><a href="*.do?tache=afficherPageCreationDefi">Cr&eacute;er une épreuve</a></li>
                      <li><a href="*.do?tache=afficherPageDefisReussis">Liste des épreuves réussies</a></li>
                    </c:if>
                    <li><a href="*.do?tache=afficherPageParticipationDefi">Voir les épreuves</a></li>
                  </ul>
                </li>
              </div>
            </c:if>
            <c:if test="${ sessionScope.role ge 3 }">
              <li><a href="*.do?tache=afficherPageConfiguration">Configuration du site</a></li>
            </c:if>
          </c:if>
        </ul>

        <ul class="nav navbar-nav navbar-right">
          <li id='li-facebook'>
            <a id='facebook'  target="_blank" href='https://www.facebook.com/DefiLectureCollegeRosemont/'></a>
          </li>
          <li id='li-facebook'>
            <a id='instagram'  target="_blank" href='https://www.instagram.com/defilecture/'></a>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"><% out.println(application.getAttribute("vocCode"));%>
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href='*.do?tache=afficherPageMarcheASuivre'>Marche à suivre</a></li>
              <li><a href='*.do?tache=afficherPageCodeConduite'>Code de conduite</a></li>
              <li><a href='*.do?tache=afficherPageGlossaire'>Glossaire</a></li>
              <li><a href='*.do?tache=afficherPageContributeurs'>Contributeurs</a></li>
            </ul>
          </li>

          <c:choose>
            <c:when test="${ empty sessionScope.connecte }">
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.dInscription']}" var="datedebut" type="both"/>
              <fmt:parseDate pattern="yyyy-MM-dd HH:mm" value="${applicationScope['com.defilecture.fLecture']}" var="datefin" type="both"/>
              <div id="menuConnexion" style="display: ${now ge datedebut && now lt datefin ? 'block' : 'none'}">
                <li><a href='*.do?tache=afficherPageConnexion'><span class="glyphicon glyphicon-log-in"></span> Se connecter</a></li>
              </div>
            </c:when>
            <c:otherwise>
              <li class="dropdown">
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <span class="glyphicon glyphicon-cog"></span>
                <span class="caret"></span></a>
                <ul class="dropdown-menu">
                  <li>
                    <a href='details.do?tache=afficherPageProfil&id=${sessionScope.connecte}'>
                      <span class="glyphicon glyphicon-user"></span> Mon Compte
                    </a>
                  </li>
                  <li>
                    <a href='*.do?tache=effectuerDeconnexion'>
                      <span class="glyphicon glyphicon-log-in"></span> Se d&eacute;connecter
                    </a>
                  </li>
                </ul>
              </li>
            </c:otherwise>
          </c:choose>
        </ul>
      </div>
    </div>
  </nav>
</div>
