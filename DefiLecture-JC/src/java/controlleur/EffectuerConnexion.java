/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlleur;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joel
 */
public class EffectuerConnexion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String  courriel = request.getParameter("courriel"),
                motPasse = request.getParameter("motPasse"); 
        
        ServletContext contexte = this.getServletContext();
        
        String pilote = contexte.getInitParameter("piloteJDBC");
        String url = contexte.getInitParameter("urlBd");
        Connection cnx =null;
        ResultSet rs = null;
        PreparedStatement requetePreparee = null; 
        String requete = "SELECT courriel FROM participant WHERE courriel = ? and motPasse = ?";

        try{
            //Étape 1 : chargement du pilote JDBC
            Class.forName(pilote);
            //Étape 2 : ouverture de la connection vers la base de données
            cnx = DriverManager.getConnection(url);
            //Étape 3 : execution de la requete
            requetePreparee = cnx.prepareStatement(requete);
            //Étape 3.1 : liaison des paramètres avec les valeurs
            requetePreparee.setString(1, courriel);   //met la 1er ? à la valeur du courriel dans la requete
            requetePreparee.setString(2, motPasse);   //met la 2e  ? à la valeur de motPasse dans la requete

            ResultSet resultat = requetePreparee.executeQuery();
            // On vérifie s'il y a un résultat    
            if(resultat.next()){
                HttpSession session = request.getSession(true);
                session.setAttribute("connecte", resultat.getString(1));
                response.sendRedirect("./profilEquipe.jsp");
            }
            else{/*
                HttpSession session = request.getSession(true);
                session.setAttribute("connecte", "0");*/
                RequestDispatcher r = this.getServletContext().getRequestDispatcher("/pageConnexion.jsp");
                r.forward(request, response);
            }
        }
        catch(SQLException e){
            System.out.println("problème dans la connexion vers la base de données : " + e);
            response.sendRedirect("./pageConnexion.jsp");
        }
        catch(ClassNotFoundException e){
            System.out.println("Erreur dans le chargement du pilote :"+ e);
            response.sendRedirect("./pageConnexion.jsp");
        }
        finally{
            try {
                if(rs != null)
                    rs.close();
                if(requetePreparee !=null)
                    requetePreparee.close();
                if(cnx != null)
                    cnx.close();
            } catch (SQLException ex) {
                System.out.println("problème dans la fermeture de la connexion");
            } 
        }


    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
