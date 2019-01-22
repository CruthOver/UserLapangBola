package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {
    private long id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
