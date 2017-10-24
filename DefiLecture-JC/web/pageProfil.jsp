<%-- 
    Document   : pageProfil
    Created on : 2017-10-22, 08:26:14
    Author     : Charles
--%>

<%@page import="jdbc.Config"%>
<%@page import="modele.Compte"%>
<%@page import="modele.Lecture"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="modele.CompteDAO"%>
<%@page import="modele.LectureDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>
<h2>Liste de mes lectures</h2>

<%  Class.forName(Config.DRIVER);
    Connexion.setUrl(Config.URL);
    Connexion.setUser(Config.DB_USER);
    Connexion.setPassword(Config.DB_PWD);
    Connexion.reinit();
    Connection cnx = Connexion.getInstance();
    LectureDAO dao = new LectureDAO(cnx);
    
    List<Lecture> listeLectures = dao.findByIdCompte((int)(session.getAttribute("connecte")));
    //Compte c;
    String obligatoire;
  %>
    <table class="table">
        
        <thead>
        <tr>
          <th>Titre</th>
          <th>Durée</th>
          <th>Date d'inscription</th>
          <th>Obligatoire</th>
        </tr>
      </thead>
      <tbody>
<%  
    for(Lecture l : listeLectures){
    //while (listeComptes.iterator().hasNext()){
      //  c = listeComptes.iterator().next();
      pageContext.setAttribute("l", l);
        switch (l.getEstObligatoire()) {
                case 0: obligatoire = "NON";
                    break;
                case 1: obligatoire = "OUI";       
                    break;
                default:
                    obligatoire = "NON";
            }
%>
        <tr>
          <td>${l.titre}</td>
          <td>${l.dureeMinutes} minutes</td>
          <td>${l.dateInscription} </td>
          <td><%=obligatoire%></td>
          <td><a href="*.do?tache=afficherPageModificationLecture&id=${l.idLecture}">Modifier</a></td>
          <td><a href="#">Supprimer</a></td>
        </tr>
    <% } %>
      </tbody>
        
    </table>
    
    
    
    
