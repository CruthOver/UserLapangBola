package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterAlternativeListener;
import bola.wiradipa.org.lapanganbola.models.Rent;

/**
 * Created by emrekabir on 25/07/18.
 */

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.MyViewHolder> {
    private Context context;
    private List<Rent> dataList;
    private AdapterAlternativeListener<Rent> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView venue, amount, rent_date, duration;
        public Button pay;
        public LinearLayout top;

        public MyViewHolder(View view) {
            super(view);
            venue = view.findViewById(R.id.venue);
            amount = view.findViewById(R.id.amount);
            rent_date = view.findViewById(R.id.rent_date);
            duration = view.findViewById(R.id.duration);
            pay = view.findViewById(R.id.pay);
            top = view.findViewById(R.id.top);

            top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onDetailSelected(dataList.get(getAdapterPosition()));
                }
            });

            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if (listener != null)
                        listener.onNextSelected(dataList.get(getAdapterPosition()));
                }
            });
        }
    }


    public RentAdapter(Context context, List<Rent> dataList, AdapterAlternativeListener<Rent> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
    }

    public void setData(List<Rent> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Rent data = dataList.get(position);
        holder.venue.setText(data.getVenue());
        holder.amount.setText(MoneyHelper.toMoneyWithRp(""+data.getAmount()));
        holder.rent_date.setText(DateHelper.getDate(data.getRental_date()));
        holder.duration.setText("Jam Mulai : "+data.getStart_hour()+":00");

        switch (data.getDown_payment_status()){
            case 0:
                holder.pay.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.pay.setVisibility(View.VISIBLE);
                holder.pay.setText(R.string.label_reupload_payment);
                break;
            case 3:
                holder.pay.setVisibility(View.VISIBLE);
                holder.pay.setText(R.string.label_reupload_payment);
                break;
            default:
                holder.pay.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
