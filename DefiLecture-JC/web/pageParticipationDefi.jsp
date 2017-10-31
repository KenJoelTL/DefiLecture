<%-- 
    Document   : pageGestionDefi
    Created on : 2017-10-24, 17:34:47
    Author     : Charles
--%>

<%@page import="modele.Compte"%>
<%@page import="modele.CompteDAO"%>
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
    
    CompteDAO daoCompte = new CompteDAO(cnx);
    Compte compte = new Compte();
    compte = daoCompte.read((int)session.getAttribute("connecte"));
    int role = compte.getRole();
    pageContext.setAttribute("role", role);
    List<Defi> listeDefi;
    if(compte.getRole()<3){
        listeDefi = daoDefi.findAllDefiEnCours();
        
    }
    else{
        listeDefi = daoDefi.findAllByIdCompte((int)session.getAttribute("connecte"));
    }
    
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
          <th>Valeur</th>
          <th>Date debut</th>
          <th>Date fin</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach items="${listeDefi}" var="d">
            <tr>
              <td>${d.nom}</td>
              <td>+ ${d.valeurMinute} minutes</td>
              <td>${d.dateDebut} </td>
              <td>${d.dateFin}</td>
              
              <%-- Si le compte est un compte admin ou moderateur, il ne peut pas relever de défi, mais il peut les modifier--%>
              <c:if test="${pageScope.role ge 3}">
                  <td><a href="##">modifier</a></td>
              </c:if>
              
               
              <c:if test="${pageScope.role lt 3}">
              
                      <c:choose>
                          <c:when test="${listeReussi.contains(d.idDefi.toString())}">
                          <td class="bg-success">REUSSI</td>
                              
                          </c:when>
                          <c:when test="${listeEchoue.contains(d.idDefi.toString())}">
                             <td class="bg-danger">ECHOUE</td>
                          </c:when>
                             <c:otherwise>
                                 <td> <a class="btn btn-info" role="button" href="*.do?tache=afficherPageInscriptionDefi&id=${d.idDefi}">Relever le défi</a></td>

                             </c:otherwise>
                      </c:choose>
              </c:if>
              </td
            </tr>
        </c:forEach>

      </tbody>
        
    </table>