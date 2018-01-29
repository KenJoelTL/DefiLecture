/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

/**
 *
 * @author Charles
 */
public class Config {
    
    public static String URL = "jdbc:mysql://localhost/defilecture?useUnicode=true&characterEncoding=UTF-8";
    public static String DB_HOST = "localhost";
    public static String DB_USER = "root"; //utiliser les para de config
    public static String DB_PWD = "root";
    public static String DB_NAME = "defilecture";
    public static String DRIVER = "com.mysql.jdbc.Driver"; //Ajout de l'attribut contenant info du pilote - Joel
 
}
