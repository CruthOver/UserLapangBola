package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Setting {
    private long id;
    private String title;
    private String description;

    public Setting(long id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
