package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterAlternativeListener;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.Field;

/**
 * Created by emrekabir on 25/07/18.
 */

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.MyViewHolder> {
    private Context context;
    private List<Field> dataList;
    private AdapterAlternativeListener<Field> listener;
    private Picasso picasso;
    private boolean canSubmit=false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rent_rate, detail;
        public ImageView picture, detail_icon;
//        public Button submit;
        public LinearLayout top;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            rent_rate = view.findViewById(R.id.rent_rate);
            picture = view.findViewById(R.id.picture);
            detail_icon = view.findViewById(R.id.detail_icon);
            detail = view.findViewById(R.id.detail);
//            submit = view.findViewById(R.id.submit);
            top = view.findViewById(R.id.top);

            top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onDetailSelected(dataList.get(getAdapterPosition()));
                }
            });

            detail_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onDetailSelected(dataList.get(getAdapterPosition()));
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if(listener!=null)
                        listener.onDetailSelected(dataList.get(getAdapterPosition()));
                }
            });

            /*submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected product in callback
                    if (listener != null)
                        listener.onNextSelected(dataList.get(getAdapterPosition()));
                }
            });*/
        }
    }


    public FieldAdapter(Context context, List<Field> dataList, AdapterAlternativeListener<Field> listener) {
        this.context = context;
        this.listener = listener;
        this.dataList = dataList;
        this.picasso = Picasso.get();
    }

    public void setData(List<Field> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void setData(List<Field> dataList, boolean canSubmit){
        this.dataList = dataList;
        this.canSubmit = canSubmit;
        notifyDataSetChanged();
    }

    public void setCanSubmit(boolean canSubmit){
        this.canSubmit = canSubmit;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.field_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Field data = dataList.get(position);
        holder.name.setText(data.getName());
        holder.rent_rate.setText(MoneyHelper.toMoneyWithRp(""+data.getRent_rate()));
        picasso.load(UtilsApi.BASE_URL+data.getPicture())
                .fit()
                .centerCrop()
                .error(R.drawable.lapangan)
                .placeholder(R.drawable.lapangan)
                .into(holder.picture);

        /*if(canSubmit) {
            holder.submit.setVisibility(View.VISIBLE);
        } else holder.submit.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
