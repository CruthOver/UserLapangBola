package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.Venue;

/**
 * Created by emrekabir on 25/07/18.
 */

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.MyViewHolder> {
    private Context context;
    private List<Venue> dataList;
    private AdapterListener<Venue> listener;
    private Picasso picasso;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, description, rent_rate;
        public ImageView picture;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            address = view.findViewById(R.id.address);
            description = view.findViewById(R.id.description);
            rent_rate = view.findViewById(R.id.rent_rate);
            picture = view.findViewById(R.id.picture);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemSelected(dataList.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemLongSelected(dataList.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }


    public VenueAdapter(Context context, List<Venue> dataList, AdapterListener<Venue> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.picasso = Picasso.get();
    }

    public void setData(List<Venue> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.venue_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Venue data = dataList.get(position);
        holder.name.setText(data.getName());
        holder.address.setText(data.getAddress());
        holder.description.setText(data.getDescription());
        holder.rent_rate.setText(MoneyHelper.toMoneyWithRp(""+data.getRent_rate()));
        picasso.load(UtilsApi.BASE_URL+data.getPicture())
                .fit()
                .centerCrop()
                .error(R.drawable.lapangbola_logo)
                .placeholder(R.drawable.lapangbola_logo)
                .into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
