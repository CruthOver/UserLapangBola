package bola.wiradipa.org.lapanganbola;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import bola.wiradipa.org.lapanganbola.models.Booking;

public class GridViewAdapter extends BaseAdapter {

    Context mContext;
    List<Booking> listJadwal;
    private String mStartHour, mEndHour;

    public GridViewAdapter(Context mContext, List<Booking> listJadwal) {
        this.mContext = mContext;
        this.listJadwal = listJadwal;
    }

    public class ViewHolder{
        TextView tvCost, tvHour;
        LinearLayout lyBackground;
    }

    @Override
    public int getCount() {
        return listJadwal.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public String getStartHour(int i){
        return listJadwal.get(i).getJam();
    }

    public String getEndHour(int i){
        return listJadwal.get(i).getEndHour();
    }

    public void setData(List<Booking> dataList){
        this.listJadwal = dataList;
        notifyDataSetChanged();
    }

    @SuppressLint("ResourceType")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.content_grid_view, viewGroup, false);
            holder = new ViewHolder();
            holder.tvHour = (TextView) view.findViewById(R.id.jam);
            holder.tvCost = (TextView) view.findViewById(R.id.harga);
            holder.lyBackground = (LinearLayout) view.findViewById(R.id.ly_grid_view);

            final Booking bookingList = listJadwal.get(i);
            if (bookingList != null){
                holder.tvHour.setText(bookingList.getJam()+":00");
                if (bookingList.getHarga().equals("")){
                    holder.tvCost.setText("0K");
                } else {
                    holder.tvCost.setText(bookingList.getHarga());
                }

                if (bookingList.getStatus() == 1){
                    holder.lyBackground.setBackground(ContextCompat.getDrawable(mContext,R.drawable.dot_yellow));
                    holder.lyBackground.setId(R.drawable.dot_yellow);
                } else {
                    holder.lyBackground.setBackground(ContextCompat.getDrawable(mContext,R.drawable.dot_tersedia));
                    holder.lyBackground.setId(R.drawable.dot_tersedia);

                }

                Boolean isCheck = false;
                if (mStartHour.equals(R.string.hint_hour) || mEndHour == null){
                    isCheck = false;
                }
                else {
                    String[] splitStartHour = mStartHour.split(":");
                    String[] splitEndHour = mEndHour.split(":");
                    int startHour = Integer.parseInt(String.valueOf(splitStartHour[0]));
                    int endHour = Integer.parseInt(splitEndHour[0]);
                    for (int x=startHour; x<endHour; x++){
                        if (String.valueOf(x).equals(bookingList.getJam())){
                            holder.lyBackground.setBackground(ContextCompat.getDrawable(mContext,R.drawable.dot_green));
                            holder.lyBackground.setId(R.drawable.dot_green);
                        }
                    }
                    isCheck = true;
                }
            }

            final ViewHolder finalHolder = holder;
            holder.lyBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int parseStartHour = Integer.parseInt(getStartHour(i));
                    int parseEndHour = Integer.parseInt(getEndHour(i));
                    for (int a=parseStartHour; a<parseEndHour; a++){
                        if (finalHolder.lyBackground.getId() == R.drawable.dot_tersedia){
                            finalHolder.lyBackground.setBackground(ContextCompat.getDrawable(mContext,R.drawable.dot_green));
                            finalHolder.lyBackground.setId(R.drawable.dot_green);

                        } else if (finalHolder.lyBackground.getId() == R.drawable.dot_green) {
                            finalHolder.lyBackground.setBackground(ContextCompat.getDrawable(mContext,R.drawable.dot_tersedia));
                            finalHolder.lyBackground.setId(R.drawable.dot_tersedia);
                        } else if (finalHolder.lyBackground.getId() == R.drawable.dot_yellow){
                            Toast.makeText(mContext, "Sudah dibooking oleh orang lain", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        return view;
    }

    public String checkDigit(String number) {
        return Integer.parseInt(number)<=9?"0"+number+":00":number +":00";
    }

    public void setBackgroundStartHour(String mStartHour){
        this.mStartHour = mStartHour;
    }

    public void setBackgroundEndHour(String mEndHour){
        this.mEndHour = mEndHour;
    }

    public void clearData(){
        listJadwal.clear();
    }
}
