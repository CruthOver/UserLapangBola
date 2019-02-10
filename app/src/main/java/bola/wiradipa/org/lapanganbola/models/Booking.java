package bola.wiradipa.org.lapanganbola.models;

/**
 * Created by ikhsan ramadhan
 * =========================================
 * MyApplication
 * Copyright (C) 2/10/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhamad Ikhsan Ramadhan
 * E-mail   : ikhsanramadhan28@gmail.com
 * Majors   : D3 Teknik Informatika 2016
 * Campus   : Telkom University
 * -----------------------------------------
 */
public class Booking {
    String jam, harga;

    public Booking(String jam, String harga) {
        this.jam = jam;
        this.harga = harga;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
