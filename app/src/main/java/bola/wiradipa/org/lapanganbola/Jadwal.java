package bola.wiradipa.org.lapanganbola;

public class Jadwal {
    private String hari, jam;
    private int imgJadwal;

    public Jadwal(String hari, String jam, int imgJadwal) {
        this.hari = hari;
        this.jam = jam;
        this.imgJadwal = imgJadwal;
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

    public int getImgJadwal() {
        return imgJadwal;
    }

    public void setImgJadwal(int imgJadwal) {
        this.imgJadwal = imgJadwal;
    }
}
