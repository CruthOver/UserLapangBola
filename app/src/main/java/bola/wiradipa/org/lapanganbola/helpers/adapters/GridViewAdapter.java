package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.models.Booking;

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
public class GridViewAdapter extends ArrayAdapter<Booking> {

    public GridViewAdapter(Context context, List<Booking> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridview = convertView;
        if(gridview == null){
            gridview = LayoutInflater.from(getContext()).inflate(R.layout.content_grid_view,
                    parent, false);
        }
        Booking booking = getItem(position);
        TextView jam = (TextView)gridview.findViewById(R.id.jam);
        jam.setText(booking.getJam());
        TextView harga = (TextView)gridview.findViewById(R.id.harga);
        harga.setText(booking.getHarga());

        return gridview;
    }
}

