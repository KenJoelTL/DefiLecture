/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.defiLecture.controleur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Joel
 */
@MultipartConfig
public class AjouterImageAction implements Action, RequestAware, SessionAware, RequirePRGAction{
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    
    @Override
    public String execute() {
        OutputStream out = null;
        InputStream filecontent = null;
        String absolutePath = "";

        try {
            final Part filePart = request.getPart("nomFichier");
            final String path = "/image/avatars";
            final String fileName = "avatarCompte_"+request.getParameter("idCompte");

            String relativePath = path+"/"+fileName;
            absolutePath = session.getServletContext().getRealPath(relativePath);
            
            
            out = new FileOutputStream(new File(absolutePath));
            filecontent = filePart.getInputStream();

            int nbOctetsLus = 0;
            final byte[] bytes = new byte[1024];

            while ((nbOctetsLus = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, nbOctetsLus);
            }

        } 
        catch (FileNotFoundException fne) { 
            System.out.println("\nImpossible d'atteindre la destination : "+ absolutePath); 
        } 
        catch (IOException | ServletException ex) { 
            Logger.getLogger(AjouterImageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if (out != null){
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(AjouterImageAction.class.getName()).log(Level.SEVERE, null, ex);
                }     
            }
            if (filecontent != null){
                try {
                    filecontent.close();
                } catch (IOException ex) {
                    Logger.getLogger(AjouterImageAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return "*.do?tache=afficherPageAccueil";
        
    }

    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }
    
}
