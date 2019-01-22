package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Rent {
    private long id;
    private String id_transaction;
    private Date rental_date;
    private int start_hour;
    private int end_hour;
    private int duration;
    private int down_payment;
    private int down_payment_status;
    private int receivable;
    private String player_phone_number;
    private int amount;
    @SerializedName(value = "field", alternate = "field_name")
    private String field;
    @SerializedName("field_owner_name")
    private String venue;
    @SerializedName(value = "player", alternate = "player_name")
    private String player;
    private String payment_status;
    private String rejection_reason;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public Date getRental_date() {
        return rental_date;
    }

    public int getAmount() {
        return amount;
    }

    public String getField() {
        return field;
    }

    public String getId_transaction() {
        return id_transaction;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public String getPlayer() {
        return player;
    }

    public String getVenue() {
        return venue;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setId_transaction(String id_transaction) {
        this.id_transaction = id_transaction;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setRental_date(Date rental_date) {
        this.rental_date = rental_date;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getDown_payment() {
        return down_payment;
    }

    public int getDown_payment_status() {
        return down_payment_status;
    }

    public int getDuration() {
        return duration;
    }

    public String getPlayer_phone_number() {
        return player_phone_number;
    }

    public int getReceivable() {
        return receivable;
    }

    public void setDown_payment(int down_payment) {
        this.down_payment = down_payment;
    }

    public void setDown_payment_status(int down_payment_status) {
        this.down_payment_status = down_payment_status;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPlayer_phone_number(String player_phone_number) {
        this.player_phone_number = player_phone_number;
    }

    public void setReceivable(int receivable) {
        this.receivable = receivable;
    }

    public String getRejection_reason() {
        return rejection_reason;
    }

    public void setRejection_reason(String rejection_reason) {
        this.rejection_reason = rejection_reason;
    }
}
