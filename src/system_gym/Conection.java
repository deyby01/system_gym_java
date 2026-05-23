/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system_gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conection {
    String dbName = "system_gym";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "gym_user";
    String password = "Gym_2024!";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;
    public Conection(){ }

    public Connection connect(){
     try{
       Class.forName(driver);
       con = DriverManager.getConnection(url+dbName,user,password);
       System.out.println("Connected to database: "+dbName);
     }catch(ClassNotFoundException |SQLException ex){
       System.out.println("Failed to connect to database: "+dbName);
       System.out.println(ex);
     }
     return con;
    }
    public void disconnect(){
     try{
         con.close();
     }catch(SQLException ex){ }
    }
    
}  
