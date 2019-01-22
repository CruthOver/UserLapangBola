package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.Setting;

/**
 * Created by emrekabir on 25/07/18.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.MyViewHolder> {
    private Context context;
    private List<Setting> dataList;
    private AdapterListener<Setting> listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);

        }
    }


    public SettingAdapter(Context context, List<Setting> dataList, AdapterListener<Setting> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
    }

    public void setData(List<Setting> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Setting data = dataList.get(position);
        holder.title.setText(data.getTitle());
        holder.description.setText(data.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
