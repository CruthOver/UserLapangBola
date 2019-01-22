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

public class JadwalAdapter extends ArrayAdapter<Jadwal> {
    public JadwalAdapter(@NonNull Context context, @NonNull List<Jadwal> objects) {
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_jadwal,parent,false);
        Jadwal current = getItem(position);
        TextView textJadwal = (TextView) convertView.findViewById(R.id.hari_jadwal);
        TextView textJam = (TextView) convertView.findViewById(R.id.jam_jadwal);
        ImageView img_jadwal = (ImageView) convertView.findViewById(R.id.img_jadwal);

        textJadwal.setText(current.getHari());
        textJam.setText(current.getJam());
        img_jadwal.setImageResource(current.getImgJadwal());

        return convertView;
    }
}
