<%-- 
    Document   : pageGestionDefi
    Created on : 2017-10-24, 17:34:47
    Author     : Charles
--%>

<%@page import="java.util.logging.Level"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.defiLecture.modele.Compte"%>
<%@page import="com.defiLecture.modele.CompteDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.defiLecture.modele.InscriptionDefi"%>
<%@page import="com.defiLecture.modele.InscriptionDefiDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="com.defiLecture.modele.Defi"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.defiLecture.modele.DefiDAO"%>
<%@page import="jdbc.Connexion"%>
<h2>Liste des défis</h2>

<% /*
    Connexion.reinit();
    Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
    DefiDAO daoDefi = new DefiDAO(cnx);
    
    CompteDAO daoCompte = new CompteDAO(cnx);    
    Compte compte = new Compte();
    compte = daoCompte.read((int)session.getAttribute("connecte"));
    int role = compte.getRole();
    pageContext.setAttribute("role", role);
    
List<Defi> listeDefi;
    //List<Defi> listeHistoriqueParticipant = new ArrayList<>();
    if(compte.getRole()<3){
        //listeDefi = daoDefi.findAllDefiEnCours();
        listeDefi = daoDefi.findHistorique();
        //listeHistorique = daoDefi.findHistoriqueByIdCompte((int)session.getAttribute("connecte"));
    }
    else{
        listeDefi = daoDefi.findAllByIdCompte((int)session.getAttribute("connecte"));
    }
    
    pageContext.setAttribute("listeDefi", listeDefi);
    //pageContext.setAttribute("listeHistorique", listeHistorique);
    cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);

    InscriptionDefiDAO daoInscriptionDefi = new InscriptionDefiDAO(cnx);
    List<InscriptionDefi> listeInscriptionDefi = daoInscriptionDefi.findAllByIdCompte((int)session.getAttribute("connecte"));
       //Création des liste réussi et echoue, déterminer si le partcipant a déjà participé au défis
    ArrayList<String> listeReussi = new ArrayList<String>();
    ArrayList<String> listeEchoue = new ArrayList<String>();
    %>


    <jsp:useBean id="connexion" class="jdbc.Connexion"></jsp:useBean>
    <jsp:useBean id="daoDefi" class="com.defiLecture.modele.DefiDAO">
        <jsp:setProperty name="daoDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>
    
    <jsp:useBean id="daoCompte" class="com.defiLecture.modele.CompteDAO">
        <jsp:setProperty name="daoCompte" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>
    
    <c:set var="compte" scope="page" value="${dao.read(sessionScope.connecte)}"/>
    <c:set var="role" scope="page" value="${compte.role}"/>
    
    <c:choose>
        <c:when test="${ role gt 3 }">
            <c:set var="listeDefi" scope="page" value="${daoDefi.findHistorique()}"/>
        </c:when>
        <c:otherwise>
            <c:set var="listeDefi" scope="page" value="${daoDefi.findAllByIdCompte(sessionScope.connecte)}"/>
        </c:otherwise>
    </c:choose>
    
    <jsp:useBean id="daoInscriptionDefi" class="com.defiLecture.modele.InscriptionDefiDAO">
        <jsp:setProperty name="daoInscriptionDefi" property="cnx" value="${connexion.connection}"></jsp:setProperty>
    </jsp:useBean>
    
    <c:set var="listeInscriptionDefi" scope="page" value="${daoInscriptionDefi.findAllByIdCompte(sessionScope.connecte)}"/>

    <jsp:useBean id="listeReussi" class="java.util.ArrayList"></jsp:useBean>
    <jsp:useBean id="listeEchoue" class="java.util.ArrayList"></jsp:useBean>

    <c:forEach items="${listeInscriptionDefi}" var="i">
        <c:choose>
            <c:when test="${i.estReussi eq 1}">
                ${listeReussi.add(""+i.idDefi)}
            </c:when>
            <c:otherwise>
                ${listeEchoue.add(""+i.idDefi)}
            </c:otherwise>
        </c:choose>
    </c:forEach>
<%/*
     for(InscriptionDefi i : listeInscriptionDefi){
        if(i.getEstReussi() == 1)
            listeReussi.add(""+i.getIdDefi());
        else
            listeEchoue.add(""+i.getIdDefi());
    }
    pageContext.setAttribute("listeReussi", listeReussi);
    pageContext.setAttribute("listeEchoue", listeEchoue);
    
    //Création de la liste d'historique des défis
    Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);*/
%> 
  <table class="table">
        <thead>
        <tr>
          <th>Titre</th>
          <th>Valeur</th>
          <th>Date debut</th>
          <th>Date fin</th>
        </tr>
      </thead>
      <tbody>
       
          <%-- variable qui indique la date d'aujourd'hui, pour faire des comparaisons--%>
              <jsp:useBean id="now" class="java.util.Date" />
              <fmt:formatDate var="dateMaintenant" value="${now}" pattern="yyyy-MM-dd' 'HH:mm:ss.S" />
          
        <c:forEach items="${listeDefi}" var="d">
            
            <%-- Condition qui permet au participant de voir tous les défis qu'il a réussi ou échoué, et de voir les nouveaux défis à relever--%>
            <c:if test="${(pageScope.role lt 3) and (listeReussi.contains(d.idDefi.toString()) or listeEchoue.contains(d.idDefi.toString()) or d.dateFin gt dateMaintenant) }">
              <tr>
              <td>${d.nom}</td>
              <td>+ ${d.valeurMinute} minutes</td>
              <c:catch>
                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateDebut}" var="dateDebutPARSE" />
              </c:catch>
              <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
              <td>${dateDebut} </td>
              <c:catch>
                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateFin}" var="dateFinPARSE" />
              </c:catch>
              <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
              <td>${dateFin} </td>

              <c:choose>
                          <c:when test="${listeReussi.contains(d.idDefi.toString())}">
                          <td class="bg-success">REUSSI</td>
                              
                          </c:when>
                          <c:when test="${listeEchoue.contains(d.idDefi.toString())}">
                             <td class="bg-danger">ECHOUE</td>
                          </c:when>
                             <c:otherwise>
                                 <c:choose>
                                     <c:when test="${d.dateFin lt dateMaintenant}">
                                        <td> TERMINÉ </td>
                                    </c:when>
                                        <c:otherwise>
                                            <td> <a class="btn btn-info" role="button" href="*.do?tache=afficherPageInscriptionDefi&id=${d.idDefi}">Relever le défi</a></td>
                                        </c:otherwise>
                                 </c:choose>
                             </c:otherwise>
              </c:choose>
     
              </c:if>
                
                                            
              <%-- Condition qui permet au modérateur ou à l'administrateur de voir tous les défis qu'il a créé--%>                              
              <c:if test="${pageScope.role ge 3}">
                  
             <tr>
              <td>${d.nom}</td>
              <td>+ ${d.valeurMinute} minutes</td>
              <c:catch>
                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateDebut}" var="dateDebutPARSE" />
              </c:catch>
              <fmt:formatDate var="dateDebut" value="${dateDebutPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
              <td>${dateDebut} </td>
              <c:catch>
                <fmt:parseDate pattern="yyyy-MM-dd' 'HH:mm:ss.SS" value="${d.dateFin}" var="dateFinPARSE" />
              </c:catch>
              <fmt:formatDate var="dateFin" value="${dateFinPARSE}" pattern="d MMMM yyyy 'à' HH'h'mm" />
              <td>${dateFin} </td>

              <%-- Si le compte est un compte admin ou moderateur, il ne peut pas relever de défi, mais il peut les modifier--%>
              <td><a href="*.do?tache=afficherPageModificationDefi&id=${d.idDefi}">modifier</a></td>
              
                  <%-- Sert à identifier si les défi sont en cours, en attente, ou terminé--%>
                 <c:choose>
                     <c:when test="${(d.dateDebut lt dateMaintenant) and (d.dateFin gt dateMaintenant)}">
                         <td class="bg-success"> EN COURS </td>
                     </c:when>
                     <c:when test="${d.dateFin lt dateMaintenant}">
                         <td class="bg-danger"> TERMINÉ </td>
                     </c:when>
                     <c:otherwise>
                         <td> EN ATTENTE </td>
                     </c:otherwise>
                 </c:choose>
              
          
              </td
            </tr>
            </c:if>
        </c:forEach>

      </tbody>
        
    </table>