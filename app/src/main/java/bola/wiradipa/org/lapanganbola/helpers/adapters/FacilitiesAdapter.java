package bola.wiradipa.org.lapanganbola.helpers.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.models.Facility;

public class FacilitiesAdapter extends BaseAdapter {
    private Context mContext;
    private List<Facility> facilities;

    public FacilitiesAdapter(Context c, List<Facility> facilities) {
        mContext = c;
        this.facilities = facilities;
    }

    public int getCount() {
        return facilities.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;

        Facility facility = facilities.get(position);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.facility_item, null);
        }

        title = convertView.findViewById(R.id.name);

//        picasso.load(UtilsApi.BASE_URL+infographic.getImage_url())
//                .fit()
//                .transform(new CropSquareTransformation())
//                .placeholder(R.drawable.default_image)
//                .error(R.drawable.default_image)
//                .into(imageView);
        title.setText("- "+facility.getName());

        return convertView;
    }

}
