package system_gym;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


public class Exercise {
    private int id;
    private String name;
    private String type;
    private String intensityLevel;
    private double estimatedTime;
    private String description;
    private int lastUsed;
    
    public Exercise() {}

    public Exercise(int id, String name, String type, String intensityLevel, double estimatedTime, String description, int lastUsed) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.intensityLevel = intensityLevel;
        this.estimatedTime = estimatedTime;
        this.description = description;
        this.lastUsed = lastUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(String intensityLevel) {
        this.intensityLevel = intensityLevel;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(int lastUsed) {
        this.lastUsed = lastUsed;
    }
    
    
    
}
