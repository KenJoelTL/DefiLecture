<%-- 
    Document   : pageGestionDefi
    Created on : 2017-10-24, 17:34:47
    Author     : Charles
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="modele.InscriptionDefi"%>
<%@page import="modele.InscriptionDefiDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="jdbc.Config"%>
<%@page import="modele.Defi"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="modele.DefiDAO"%>
<%@page import="jdbc.Connexion"%>
<h2>Liste des défis</h2>

<% 
    Connexion.reinit();
    Connection cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
    DefiDAO daoDefi = new DefiDAO(cnx);
    List<Defi> listeDefi = daoDefi.findAll();
    pageContext.setAttribute("listeDefi", listeDefi);
    
    cnx = Connexion.startConnection(Config.DB_USER,Config.DB_PWD,Config.URL,Config.DRIVER);
    InscriptionDefiDAO daoInscriptionDefi = new InscriptionDefiDAO(cnx);
    List<InscriptionDefi> listeInscriptionDefi = daoInscriptionDefi.findAllByIdCompte((int)session.getAttribute("connecte"));
    
    //Création des liste réussi et echoue, déterminer si le partcipant a déjà participé au défis
    ArrayList<String> listeReussi = new ArrayList<String>();
    ArrayList<String> listeEchoue = new ArrayList<String>();
    for(InscriptionDefi i : listeInscriptionDefi){
        if(i.getEstReussi() == 1)
            listeReussi.add(""+i.getIdDefi());
        else
            listeEchoue.add(""+i.getIdDefi());
    }
    pageContext.setAttribute("listeReussi", listeReussi);
    pageContext.setAttribute("listeEchoue", listeEchoue);
  %>
  
  <table class="table">
        <thead>
        <tr>
          <th>Titre</th>
          <th>Points</th>
          <th>Date debut</th>
          <th>Date fin</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${listeDefi}" var="d">
            <tr>
              <td>${d.nom}</td>
              <td>+ ${d.point} minutes</td>
              <td>${d.dateDebut} </td>
              <td>${d.dateFin}</td>
              <td>
                      <c:choose>
                          <c:when test="${listeReussi.contains(d.idDefi.toString())}">
                              REUSSI
                          </c:when>
                          <c:when test="${listeEchoue.contains(d.idDefi.toString())}">
                             ECHOUE
                          </c:when>
                             <c:otherwise>
                                 <a href="*.do?tache=afficherPageInscriptionDefi&id=${d.idDefi}">Relever le défi</a>
                             </c:otherwise>
                      </c:choose>
              </td>
            </tr>
        </c:forEach>

      </tbody>
        
    </table>