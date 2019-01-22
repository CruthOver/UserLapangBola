package bola.wiradipa.org.lapanganbola;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LapanganAdapter extends ArrayAdapter<Lapangan> {
    public LapanganAdapter(@NonNull Context context, @NonNull List<Lapangan> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.venue_item,parent,false);
//        Lapangan current = getItem(position);
//        TextView nama_lapangan = (TextView) convertView.findViewById(R.id.nama_lapangan);
//        TextView lokasi_lapangan = (TextView) convertView.findViewById(R.id.lokasi_lapangan);
//        TextView fasilitas = (TextView) convertView.findViewById(R.id.fasilitas);
//        TextView harga = (TextView) convertView.findViewById(R.id.harga_lapangan);
//        ImageView img_icon_lapangan = (ImageView) convertView.findViewById(R.id.img_icon_lapangan);
//        ImageView img_bintang = (ImageView) convertView.findViewById(R.id.img_bintang);
//
//        nama_lapangan.setText(current.getNama_lapangan());
//        lokasi_lapangan.setText(current.getLokasi_lapangan());
//        fasilitas.setText(current.getFasilitas());
//        harga.setText(current.getHarga());
//        img_icon_lapangan.setImageResource(current.getImg_logo_lapangan());
//        img_bintang.setImageResource(current.getImg_bintang());

        return convertView;
    }
}
