package bola.wiradipa.org.lapanganbola;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RiwayatAdapter extends ArrayAdapter<Riwayat> {
    public RiwayatAdapter(@NonNull Context context, @NonNull List<Riwayat> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_riwayat_pertandingan,parent,false);
        Riwayat current = getItem(position);
        TextView hariRiwayat = (TextView) convertView.findViewById(R.id.hari_riwayat);
        TextView jamRiwayat = (TextView) convertView.findViewById(R.id.jam_riwayat);
        ImageView img_riwayat = (ImageView) convertView.findViewById(R.id.img_riwayat);

        hariRiwayat.setText(current.getHari());
        jamRiwayat.setText(current.getJam());
        img_riwayat.setImageResource(current.getImg_riwayat());

        return convertView;
    }
}
