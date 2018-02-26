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
	private static String url = Config.URL;
	private static String user = Config.DB_USER;		
        private static String password = Config.DB_PWD;

        public Connexion(){
        }
        
	public static Connection getInstance() throws SQLException {
            try{
                if (connection == null){
                    if (user.equals(""))
                        connection = DriverManager.getConnection(url);
                    else
                        connection = DriverManager.getConnection(url,user,password);
                }
            }
            catch(SQLException e){
                System.out.println("erreur connexion");
                System.out.println(e);
                connection = null;
            }
            return connection;
	}
        
        public Connection getConnection() throws SQLException, ClassNotFoundException{
            return Connexion.startConnection(user, password, url, Config.DRIVER);
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
