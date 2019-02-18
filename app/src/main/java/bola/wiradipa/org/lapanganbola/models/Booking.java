package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Booking {
    int status;
    @SerializedName("tariff")
    String harga;
    @SerializedName("start_hour")
    String mStartHour;
    @SerializedName("end_hour")
    String mEndHour;

    public Booking() { }

    public Booking(String jam, String harga) {
        this.mStartHour = jam;
        this.harga = harga;
    }

    public String getJam() {
        return mStartHour;
    }

    public void setJam(String jam) {
        this.mStartHour = jam;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setEndHour(String mEndHour) {
        this.mEndHour = mEndHour;
    }

    public String getEndHour() {
        return mEndHour;
    }
}
