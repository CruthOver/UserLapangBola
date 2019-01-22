package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.ImageShare;

/**
 * Created by emrekabir on 25/07/18.
 */

public class ImageShareAdapter extends RecyclerView.Adapter<ImageShareAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<ImageShare> dataList;
    private List<ImageShare> dataListFiltered;
    private AdapterListener<ImageShare> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemSelected(dataListFiltered.get(getAdapterPosition()));
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onItemLongSelected(dataListFiltered.get(getAdapterPosition()));
                    return true;
                }
            });
        }
    }


    public ImageShareAdapter(Context context, List<ImageShare> dataList, AdapterListener<ImageShare> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
    }

    public void setData(List<ImageShare> dataList){
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ImageShare data = dataListFiltered.get(position);
        holder.imageView.setImageResource(R.color.underline);
    }

    @Override
    public int getItemCount() {
        return dataListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    dataListFiltered = dataList;
                } else {
                    List<ImageShare> filteredList = new ArrayList<>();
                    for (ImageShare row : dataList) {

                        if (row.getUrl().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = dataListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                dataListFiltered = (ArrayList<ImageShare>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
