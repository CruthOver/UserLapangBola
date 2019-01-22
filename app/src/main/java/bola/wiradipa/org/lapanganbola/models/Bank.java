package bola.wiradipa.org.lapanganbola.models;

import com.google.gson.annotations.SerializedName;

public class Bank {
    private long id;
    @SerializedName("bank_name")
    private String name;
    @SerializedName("bank_code")
    private String code;
    @SerializedName("acc_name")
    private String accountName;
    @SerializedName("acc_number")
    private String accountNumber;
    @SerializedName("bank_logo_url")
    private String logo;

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

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCode() {
        return code;
    }

    public String getLogo() {
        return logo;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return name;
    }
}
