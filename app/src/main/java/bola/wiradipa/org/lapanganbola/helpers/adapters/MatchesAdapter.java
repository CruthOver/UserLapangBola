package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.Match;

/**
 * Created by emrekabir on 25/07/18.
 */

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<Match> dataList;
    private List<Match> dataListFiltered;
    private AdapterListener<Match> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, home_team, away_team, home_goal, away_goal;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.time);
            home_team = view.findViewById(R.id.home_team);
            home_goal = view.findViewById(R.id.home_goal);
            away_team = view.findViewById(R.id.away_team);
            away_goal = view.findViewById(R.id.away_goal);

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


    public MatchesAdapter(Context context, List<Match> dataList, AdapterListener<Match> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.dataListFiltered = dataList;
    }

    public void setData(List<Match> dataList){
        this.dataList = dataList;
        this.dataListFiltered = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Match data = dataListFiltered.get(position);
        holder.time.setText("FT");
        holder.home_team.setText(data.getHome_team());
        holder.away_team.setText(data.getAway_team());
        holder.home_goal.setText(""+data.getHome_goal());
        holder.away_goal.setText(""+data.getAway_goal());
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
                    List<Match> filteredList = new ArrayList<>();
                    for (Match row : dataList) {

                        if (row.getHome_team().toLowerCase().contains(charString.toLowerCase())||
                                row.getAway_team().toLowerCase().contains(charString.toLowerCase())) {
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
                dataListFiltered = (ArrayList<Match>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
