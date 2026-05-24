/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system_gym;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the Exercise entity.
 * Handles all database operations related to exercises: create, update, delete,
 * retrieve and routine generation. No SQL should be written outside this class.
 */
public class ExerciseDAO {
    Conection con_db;
    Connection conect;
    Statement st;

    public ExerciseDAO() {
        con_db = new Conection();
    }

    /**
     * Inserts a new exercise into the database.
     * The id is assigned automatically by MySQL (AUTO_INCREMENT).
     *
     * @param e The exercise object to save (Strength or Cardio).
     * @return true if the exercise was inserted successfully, false otherwise.
     */
    public boolean createExercise(Exercise e) {
        String sql = "INSERT INTO exercises(name, type, intensity_level, estimated_time, description, last_used) VALUES('"+ e.getName() +"', '"+ e.getType() +"', '"+ e.getIntensityLevel() +"', "+ e.getEstimatedTime() +", '"+ e.getDescription() +"', "+ e.getLastUsed() +")";

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            int rows = st.executeUpdate(sql);
            con_db.disconnect();
            return rows > 0;

        } catch(Exception err) {
            System.out.println("Error in create Exercise: " + err);
            return false;
        }
    }
    
    /**
     * Updates an existing exercise in the database.
     * The exercise is identified by its id, which must be set in the object.
     *
     * @param e The exercise object with the updated values.
     * @return true if the exercise was updated successfully, false otherwise.
     */
    public boolean updateExercise(Exercise e) {
        String sql = "UPDATE exercises SET name='"+ e.getName() +"', type='"+ e.getType() +"', intensity_level='"+ e.getIntensityLevel() +"', estimated_time="+ e.getEstimatedTime() +", description='"+ e.getDescription() +"', last_used="+ e.getLastUsed() +" WHERE id="+ e.getId() +" ";
        
        try {
            conect = con_db.connect();
            st = conect.createStatement();
            int rows = st.executeUpdate(sql);
            con_db.disconnect();
            return rows > 0;
        
        } catch(Exception err) {
            System.out.println("Error updating Exercise: " + err);
            return false;
        }
    }
    
    /**
     * Retrieves all exercises stored in the database.
     *
     * @return A list of all exercises. Returns an empty list if none exist or an error occurs.
     */
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercises";

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String intensityLevel = rs.getString("intensity_level");
                double estimatedTime = rs.getDouble("estimated_time");
                String description = rs.getString("description");
                int lastUsed = rs.getInt("last_used");

                Exercise e = new Exercise(id, name, type, intensityLevel, estimatedTime, description, lastUsed);
                exercises.add(e);
            }

            con_db.disconnect();

        } catch (Exception err) {
            System.out.println("Error fetching exercises: " + err);
        }

        return exercises;
    }
    
    /**
     * Retrieves all exercises that match the given intensity level.
     *
     * @param level The intensity level to filter by ("Beginner", "Intermediate", "Advanced", "High Performance").
     * @return A list of matching exercises. Returns an empty list if none are found or an error occurs.
     */
    public List<Exercise> searchByLevel(String level) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercises WHERE intensity_level='"+ level +"' ";

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String intensityLevel = rs.getString("intensity_level");
                double estimatedTime = rs.getDouble("estimated_time");
                String description = rs.getString("description");
                int lastUsed = rs.getInt("last_used");

                Exercise e = new Exercise(id, name, type, intensityLevel, estimatedTime, description, lastUsed);
                exercises.add(e);
            }

            con_db.disconnect();

        } catch (Exception err) {
            System.out.println("Error fetching exercise: " + err);
        }

        return exercises;
    }

    /**
     * Generates a workout routine by selecting exercises of a given level
     * that have not been used in the last 2 weeks.
     * Each selected exercise is marked with the current week to prevent
     * it from appearing in a routine again until 2 weeks have passed.
     *
     * @param level        The intensity level for the routine.
     * @param numExercises The number of exercises to include in the routine.
     * @return A list of selected exercises. May be smaller than numExercises
     *         if not enough eligible exercises exist.
     */
    public List<Exercise> generateRoutine(String level, int numExercises) {
        List<Exercise> routine = new ArrayList<>();
        int currentWeek = java.util.Calendar.getInstance().get(java.util.Calendar.WEEK_OF_YEAR);
        String sql = "SELECT * FROM exercises WHERE intensity_level='" + level + "' AND last_used <= " + (currentWeek - 2) + " LIMIT " + numExercises;

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String intensityLevel = rs.getString("intensity_level");
                double estimatedTime = rs.getDouble("estimated_time");
                String description = rs.getString("description");
                int lastUsed = rs.getInt("last_used");

                Exercise e = new Exercise(id, name, type, intensityLevel, estimatedTime, description, lastUsed);
                routine.add(e);

                String updateSql = "UPDATE exercises SET last_used=" + currentWeek + " WHERE id=" + id;
                st.executeUpdate(updateSql);
            }

            con_db.disconnect();

        } catch (Exception err) {
            System.out.println("Error generating routine: " + err);
        }

        return routine;
    }

    /**
     * Deletes an exercise from the database permanently.
     * The exercise is identified by its id.
     *
     * @param e The exercise object to delete. Must have a valid id.
     * @return true if the exercise was deleted successfully, false otherwise.
     */
    public boolean deleteExercise(Exercise e) {
        String sql = "DELETE FROM exercises WHERE id="+ e.getId() +" ";

        try {
            conect = con_db.connect();
            st = conect.createStatement();
            int rows = st.executeUpdate(sql);
            con_db.disconnect();
            return rows > 0;

        } catch(Exception err) {
            System.out.println("Error deleting Exercise: " + err);
            return false;
        }
    }
    
}
