package system_gym;

public class Routine {
    private int id;
    private String level;
    private double totalTime;
    private String createdAt;

    public Routine(int id, String level, double totalTime, String createdAt) {
        this.id = id;
        this.level = level;
        this.totalTime = totalTime;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getLevel() { return level; }
    public double getTotalTime() { return totalTime; }
    public String getCreatedAt() { return createdAt; }
}
