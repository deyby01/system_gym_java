/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system_gym;

import java.sql.Connection;
import java.sql.Statement;

public class ExerciseDAO {
    Conection con_db;
    Connection conect;
    Statement st;

    public ExerciseDAO() {
        con_db = new Conection();
    }
    
    public boolean createExercise(Exercise e) {
        String sql = "INSERT INTO exercises(name, type, intensity_level, estimated_time, description, last_used) VALUES('"+ e.getName() +"', '"+ e.getType() +"', '"+ e.getIntensityLevel() +"', "+ e.getEstimatedTime() +", '"+ e.getDescription() +"', "+ e.getLastUsed() +")";

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            int rows = st.executeUpdate(sql);
            con_db.disconnect();
            return rows > 0;

        } catch(Exception err) {
            System.out.println("Error in createExercise: " + err);
            return false;
        }
    }
    
}
