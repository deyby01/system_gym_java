/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system_gym;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conection {
    String bd = "system_gym";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "gym_user";
    String password = "Gym_2024!";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;
    public Conection(){ }
    
    public Connection conectar(){
     try{
       Class.forName(driver);
       con = DriverManager.getConnection(url+bd,user,password);
       System.out.println("Se conecto a la bd: "+bd);
     }catch(ClassNotFoundException |SQLException ex){
       System.out.println("No se conecto a la bd: "+bd);
       System.out.println(ex);
     }  
     return con;
    }
    public void desconectar(){
     try{
         con.close();
     }catch(SQLException ex){ }    
    }
    
}  
