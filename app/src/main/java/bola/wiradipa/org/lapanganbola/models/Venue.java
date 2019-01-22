package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Venue {
    private long id;
    private String name;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    @SerializedName("min_tariff")
    private int rent_rate;
    private int start_hour;
    private int end_hour;
    @SerializedName("picture_url")
    private String picture;
    private long owner_id;
    private String owner_name;
    private List<Facility> facilities;

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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public long getOwner_id() {
        return owner_id;
    }

    public String getDescription() {
        return description;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setOwner_id(long owner_id) {
        this.owner_id = owner_id;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getRent_rate() {
        return rent_rate;
    }

    public void setRent_rate(int rent_rate) {
        this.rent_rate = rent_rate;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }
}
