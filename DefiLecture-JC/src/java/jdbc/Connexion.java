/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Charles
 */
public class Connexion {
	private static Connection cnx;
	private static String url;
	private static String user;		
        private static String password;
        //private static String encoding;
        private Connexion()
        {
        }
	public static Connection getInstance()
	{
		if (cnx == null)
			try {
				if (user.equals(""))
                                    cnx = DriverManager.getConnection(url);
				else
                                    cnx = DriverManager.getConnection(url,user,password);
                                          
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return cnx;
	}
	public static void reinit()
	{
                close();
		cnx = null;
	}
	public static void close()
	{
		try {
			if (cnx!=null)
                        {
				cnx.close();
                                cnx = null;
                        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		Connexion.url = url;
	}
        
        
	public static String getUser() {
		return user;
	}
	public static void setUser(String user) {
		Connexion.user = user;
	}
	public static void setPassword(String password) {
		Connexion.password = password;
	}
        
        public static void setConfig(String user, String password, String url){
                Connexion.user = user;
                Connexion.password = password;
                Connexion.url = url;
        }
        
        public static Connection startConnection(String user, String password, String url, String driver) throws ClassNotFoundException{
                Class.forName(driver);
                Connexion.user = user;
                Connexion.password = password;
                Connexion.url = url;             
            return getInstance();
        }
        
        public static Connection startConnection(String driver) throws ClassNotFoundException{
                Class.forName(driver);/*
                Connexion.user = user;
                Connexion.password = password;
                Connexion.url = url; */            
            return getInstance();
        }
        
}
