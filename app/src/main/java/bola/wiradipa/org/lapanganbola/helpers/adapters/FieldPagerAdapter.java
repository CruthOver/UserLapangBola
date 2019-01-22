package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.Field;

public class FieldPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Field> dataList;
    private Picasso picasso;
    private AdapterListener<Field> listener;

    public FieldPagerAdapter(Context context, List<Field> dataList, AdapterListener<Field> listener) {
        this.context = context;
        this.dataList = dataList;
        this.picasso = Picasso.get();
        this.listener = listener;
        if(dataList.size()>5){
            this.dataList = dataList.subList(0,5);
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Field data= dataList.get(position);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.image_slider_custom, null);
        ImageView imageView = view.findViewById(R.id.image_slider);
        picasso.load(UtilsApi.BASE_URL+data.getPicture())
                .placeholder(R.drawable.lapangan)
                .error(R.drawable.lapangan)
                .into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null)
                    listener.onItemSelected(data);

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}