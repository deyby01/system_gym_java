/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package system_gym;


public class Strength extends Exercise{

    public Strength() {
    }

    public Strength(int id, String name, String intensityLevel, double estimatedTime, String description, int lastUsed) {
        super(id, name, "Strength", intensityLevel, estimatedTime, description, lastUsed);
    }

    public Strength(String name, String intensityLevel, double estimatedTime, String description, int lastUsed) {
        super(name, "Strength", intensityLevel, estimatedTime, description, lastUsed);
    }
    
}
