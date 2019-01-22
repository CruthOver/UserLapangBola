package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Field {
    private long id;
    private String name;
    private String description;
    @SerializedName("field_owner_id")
    private long venue_id;
    private long grass_type_id;
    private String grass_type_name;
    @SerializedName("field_owner_name")
    private String venue_name;
    private String pitch_size;
    @SerializedName("min_tariff")
    private int rent_rate;
    @SerializedName("picture_url")
    private String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public long getGrass_type_id() {
        return grass_type_id;
    }

    public String getPicture() {
        return picture;
    }

    public long getVenue_id() {
        return venue_id;
    }

    public String getGrass_type_name() {
        return grass_type_name;
    }

    public int getRent_rate() {
        return rent_rate;
    }

    public String getPitch_size() {
        return pitch_size;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public void setGrass_type_id(long grass_type_id) {
        this.grass_type_id = grass_type_id;
    }

    public void setGrass_type_name(String grass_type_name) {
        this.grass_type_name = grass_type_name;
    }

    public void setPitch_size(String pitch_size) {
        this.pitch_size = pitch_size;
    }

    public void setRent_rate(int rent_rate) {
        this.rent_rate = rent_rate;
    }

    public void setVenue_id(long venue_id) {
        this.venue_id = venue_id;
    }

    public void setVenue_name(String venue_name) {
        this.venue_name = venue_name;
    }
}
