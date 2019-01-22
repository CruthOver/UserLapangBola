package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Player {
    private String name;
    private String username;
    private String email;
    @SerializedName("phone_number")
    private String phone;
    @SerializedName("student_status")
    private int studentStatus;
    @SerializedName("photo_url")
    private String photoUrl;
    @SerializedName("student_card_url")
    private String studentCardUrl;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getStudentStatus() {
        return studentStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setStudentStatus(int studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getStudentCardUrl() {
        return studentCardUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setStudentCardUrl(String studentCardUrl) {
        this.studentCardUrl = studentCardUrl;
    }
}
