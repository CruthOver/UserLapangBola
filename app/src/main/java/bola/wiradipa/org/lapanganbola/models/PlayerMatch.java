package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class PlayerMatch {
    private long id;
    @SerializedName(value = "home_name", alternate = "home_club_name")
    private String home_team;
    @SerializedName(value = "away_name", alternate = "away_club_name")
    private String away_team;
    @SerializedName("home_score")
    private int home_goal;
    @SerializedName("away_score")
    private int away_goal;
    @SerializedName("player_name")
    private String player;
    @SerializedName("club_name")
    private String club;
    @SerializedName("position_name")
    private String position;
    @SerializedName("total_goal")
    private int goal;
    @SerializedName("total_shoot_on_target")
    private int shootOnTarget;
    @SerializedName("total_intercept")
    private int intercept;
    @SerializedName("total_clearance")
    private int clrearence;
    @SerializedName("total_save")
    private int save;
    @SerializedName("total_foul")
    private int foul;
    @SerializedName("total_tackle")
    private int tackle;
    @SerializedName("total_shoot_off_target")
    private int shootOffTarget;
    @SerializedName("total_assist")
    private int assist;
    @SerializedName("total_dribble_success")
    private int dribble;
    @SerializedName("total_block_cross")
    private int block;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getClrearence() {
        return clrearence;
    }

    public int getFoul() {
        return foul;
    }

    public int getGoal() {
        return goal;
    }

    public int getIntercept() {
        return intercept;
    }

    public int getSave() {
        return save;
    }

    public int getShootOnTarget() {
        return shootOnTarget;
    }

    public String getClub() {
        return club;
    }

    public int getShootOffTarget() {
        return shootOffTarget;
    }

    public String getPlayer() {
        return player;
    }

    public String getPosition() {
        return position;
    }

    public void setClrearence(int clrearence) {
        this.clrearence = clrearence;
    }

    public int getAssist() {
        return assist;
    }

    public int getTackle() {
        return tackle;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setFoul(int foul) {
        this.foul = foul;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getBlock() {
        return block;
    }

    public void setIntercept(int intercept) {
        this.intercept = intercept;
    }

    public int getDribble() {
        return dribble;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public void setDribble(int dribble) {
        this.dribble = dribble;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public void setShootOnTarget(int shootOnTarget) {
        this.shootOnTarget = shootOnTarget;
    }

    public void setShootOffTarget(int shootOffTarget) {
        this.shootOffTarget = shootOffTarget;
    }

    public void setTackle(int tackle) {
        this.tackle = tackle;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public void setHome_goal(int home_goal) {
        this.home_goal = home_goal;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public void setAway_goal(int away_goal) {
        this.away_goal = away_goal;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public int getHome_goal() {
        return home_goal;
    }

    public int getAway_goal() {
        return away_goal;
    }
}
