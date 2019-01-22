package bola.wiradipa.org.lapanganbola.models;

public class ImageShare {
    private long id;
    private String url;

    public ImageShare(long id, String url){
        this.id = id;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
