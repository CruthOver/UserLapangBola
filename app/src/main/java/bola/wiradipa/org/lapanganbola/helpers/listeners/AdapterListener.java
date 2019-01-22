package bola.wiradipa.org.lapanganbola.helpers.listeners;

public abstract class AdapterListener<t> {
    public abstract void onItemSelected(t data);
    public abstract void onItemLongSelected(t data);
}
