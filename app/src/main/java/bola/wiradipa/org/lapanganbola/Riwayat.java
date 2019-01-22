package bola.wiradipa.org.lapanganbola;

public class Riwayat {
    private String hari, jam;
    private int img_riwayat;

    public Riwayat(String hari, String jam, int img_riwayat) {
        this.hari = hari;
        this.jam = jam;
        this.img_riwayat = img_riwayat;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public int getImg_riwayat() {
        return img_riwayat;
    }

    public void setImg_riwayat(int img_riwayat) {
        this.img_riwayat = img_riwayat;
    }
}
