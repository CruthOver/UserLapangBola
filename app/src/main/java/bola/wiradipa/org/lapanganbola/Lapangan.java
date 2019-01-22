package bola.wiradipa.org.lapanganbola;

public class Lapangan {
    private String nama_lapangan, lokasi_lapangan, fasilitas, harga;
    private int img_logo_lapangan, img_bintang;

    public Lapangan(String nama_lapangan, String lokasi_lapangan, String fasilitas, String harga, int img_logo_lapangan, int img_bintang) {
        this.nama_lapangan = nama_lapangan;
        this.lokasi_lapangan = lokasi_lapangan;
        this.fasilitas = fasilitas;
        this.harga = harga;
        this.img_logo_lapangan = img_logo_lapangan;
        this.img_bintang = img_bintang;
    }

    public String getNama_lapangan() {
        return nama_lapangan;
    }

    public void setNama_lapangan(String nama_lapangan) {
        this.nama_lapangan = nama_lapangan;
    }

    public String getLokasi_lapangan() {
        return lokasi_lapangan;
    }

    public void setLokasi_lapangan(String lokasi_lapangan) {
        this.lokasi_lapangan = lokasi_lapangan;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getImg_logo_lapangan() {
        return img_logo_lapangan;
    }

    public void setImg_logo_lapangan(int img_logo_lapangan) {
        this.img_logo_lapangan = img_logo_lapangan;
    }

    public int getImg_bintang() {
        return img_bintang;
    }

    public void setImg_bintang(int img_bintang) {
        this.img_bintang = img_bintang;
    }
}
