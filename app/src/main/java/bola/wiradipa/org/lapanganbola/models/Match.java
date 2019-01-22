package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Match {
    private long id;
    @SerializedName("home_name")
    private String home_team;
    @SerializedName("away_name")
    private String away_team;
    @SerializedName("home_score")
    private int home_goal;
    @SerializedName("away_score")
    private int away_goal;
    private String time;

    public Match(long id, String time, String home_team, String away_team, int home_goal, int away_goal){
        this.id = id;
        this.time = time;
        this.home_team = home_team;
        this.away_team = away_team;
        this.home_goal = home_goal;
        this.away_goal = away_goal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAway_goal() {
        return away_goal;
    }

    public int getHome_goal() {
        return home_goal;
    }

    public String getAway_team() {
        return away_team;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getTime() {
        return time;
    }

    public void setAway_goal(int away_goal) {
        this.away_goal = away_goal;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public void setHome_goal(int home_goal) {
        this.home_goal = home_goal;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
