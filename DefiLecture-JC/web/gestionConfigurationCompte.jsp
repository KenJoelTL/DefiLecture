<%-- 
    Document   : gestionConfigurationCompte
    Created on : 2017-10-22, 18:19:46
    Author     : Joel
--%>
<%@page import="modele.Compte"%>
<%@page import="modele.CompteDAO"%>
<%@page import="java.sql.Connection"%>
<%@page import="jdbc.Config"%>
<%@page import="jdbc.Connexion"%>
<%  Class.forName(Config.DRIVER);
    Connexion.setUrl(Config.URL);
    Connexion.setUser(Config.DB_USER);
    Connexion.setPassword(Config.DB_PWD);
    Connexion.reinit();
    Connection cnx = Connexion.getInstance();
    CompteDAO dao = new CompteDAO(cnx);
    
    Compte c = dao.read(request.getParameter("id"));
    String role;
  %>

    <body>
        <h1>Page de configuration</h1>
        
        <form action="*.do" method="post" >
            <div>
                Prenom* : <input type="text" name="prenom" value="<%=c.getPrenom()%>" required/>
            
                Nom* : <input type="text" name="nom" value="<%=c.getNom()%>" required/>
            </div>
            <div>
                Programme d'&eacute;tude : <input type="text" name="programmeEtude" value="<%=c.getProgrammeEtude()==null? "": c.getProgrammeEtude()%>" />
                Courriel* : <input type="email" name="courriel" value="<%=c.getCourriel()%>" required/>
            </div>
            <div>    
                Pseudonyme : <input type="text" name="pseudonyme" value="<%=c.getPseudonyme()==null? "":c.getPseudonyme()%>" />
            </div>
            <div>Role du compte : 
                <select name="role">
                    <option value="1">Participant</option>
                    <option value="2">Capitaine</option>
                    <%if((int)session.getAttribute("role")==4) {%>
                    <option value="3">Modérateur</option>
                    <option value="4">Administrateur</option>
                    <%}%>
                </select>
            </div>
            <div>
            <input type="hidden" name="idCompte" value="<%=c.getIdCompte()%>">
            <input type="hidden" name="tache" value="effectuerModificationCompte">
                <input type="submit" name="modifie" value=" Enregistrer" />
                <input type="submit" name="annule" value=" Annuler" />
            </div>
        </form>
        
        
    </body>
