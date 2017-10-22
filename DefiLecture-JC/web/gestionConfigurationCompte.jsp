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
        
        <form action="modif.do" class="form">
                        <div>
                Prenom* : <input type="text" name="prenom" required/>
            
                Nom* : <input type="text" name="nom" required/>
            </div>
            <div>
                Programme d'étude : <input type="text" name="programmeEtude" />

                Courriel* : <input type="email" name="courriel" required/>
            </div>
            <div>    
                Pseudonyme : <input type="text" name="pseudonyme" />
            </div>
            <div>
                Mot de passe* : <input type="password" name="motPasse" required/>
            
                Confirmation du mot de passe* : <input type="password" name="confirmationMotPasse" required/>
            </div>
            <input type="hidden" name="tache" value="effectuerInscription">
            <input type="submit" value=" Enregistrer" />
        </form>
        
        
    </body>
