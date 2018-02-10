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
	private static Connection connection;
        private Connection cnx;
	private static String url;
	private static String user;		
        private static String password;

        public Connexion(){}
        
	public static Connection getInstance() throws SQLException {
            if (connection == null)
                if (user.equals(""))
                    connection = DriverManager.getConnection(url);
                else
                    connection = DriverManager.getConnection(url,user,password);

            return connection;
	}
        
        public Connection getCnx() throws SQLException{
            cnx = Connexion.getInstance();
            return cnx;
        }
        
        public Connection getConnection(){
            if (connection == null)
                try {
                    if (user.equals(""))
                        connection = DriverManager.getConnection(url);
                    else
                        connection = DriverManager.getConnection(url,user,password);

                } catch (SQLException e) {
                        e.printStackTrace();
                }
            return connection;
        }
        
	public static void reinit(){
            close();
            connection = null;
	}
        
	public static void close()
	{
            try {
                if (connection!=null) {
                    connection.close();
                    connection = null;
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
        
        /**
         * 
     * @param user
     * @param password
     * @param url
     * @param driver
     * @return Connection
     * @throws java.lang.ClassNotFoundException
         */
        public static Connection startConnection(String user, String password, String url, String driver) throws ClassNotFoundException, SQLException{
                Class.forName(driver);
                Connexion.user = user;
                Connexion.password = password;
                Connexion.url = url;             
            return getInstance();
        }
        /*
        public static Connection startConnection(String driver) throws ClassNotFoundException{
               // Class.forName(driver);
               // Connexion.user = user;
               // Connexion.password = password;
               // Connexion.url = url;             
            return getInstance();
        }*/
        
}
