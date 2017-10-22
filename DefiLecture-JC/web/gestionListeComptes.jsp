<%-- 
    Document   : gestionListeComptes
    Created on : 2017-10-22
    Author     : Joel
--%>
<%@page import="jdbc.Config"%>
<%@page import="modele.Compte"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Connection"%>
<%@page import="modele.CompteDAO"%>
<%@page import="jdbc.Connexion"%>
<%@ page pageEncoding="UTF-8" %>
<h2>Gestionnaire de comptes</h2>

<%  Class.forName(Config.DRIVER);
    Connexion.setUrl(Config.URL);
    Connexion.setUser(Config.DB_USER);
    Connexion.setPassword(Config.DB_PWD);
    
    Connection cnx = Connexion.getInstance();
    CompteDAO dao = new CompteDAO(cnx);
    
    List<Compte> listeComptes = dao.findAll();
    //Compte c;
    String role;
  %>
    <table class="table">
        
        <thead>
        <tr>
          <th>Prenom</th>
          <th>Nom</th>
          <th>Courriel</th>
          <th>Rôle</th>
        </tr>
      </thead>
      <tbody>
<%  
    for(Compte c : listeComptes){
    //(listeComptes.iterator().hasNext()){
      //  c = listeComptes.iterator().next();
        switch (c.getRole()) {
                case 0: role = "Utilisteur"; //considéré comme un simple visiteur
                    break;
                case 1: role = "Participant";       
                    break;
                case 2: role = "Capitaine";       
                    break;
                case 3: role = "Moderateur";       
                    break;
                case 4: role = "Administrateur";       
                    break;
                default:
                    role = "Utilisateur";
            }
%>
        <tr>
          <td><%=c.getPrenom()%></td>
          <td><%=c.getNom() %></td>
          <td><%=c.getCourriel()%></td>
          <td><%=role%></td>
        </tr>
<% } %>
      </tbody>
        
    </table>
    
    
    
    
