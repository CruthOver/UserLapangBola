package bola.wiradipa.org.lapanganbola;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatPertandinganFragment extends Fragment {

    public RiwayatPertandinganFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.list_item, container, false);

        ArrayList<Riwayat> riwayat = new ArrayList<>();

        riwayat.add(new Riwayat("Kamis,\n26 Juli 2018","12.00 - 13.00",R.drawable.ic_navigate_next_black_24dp));
        riwayat.add(new Riwayat("Senin,\n23 Juli 2018","10.00 - 11.00",R.drawable.ic_navigate_next_black_24dp));
        riwayat.add(new Riwayat("Rabu,\n03 Juli 2018","18.00 - 19.00",R.drawable.ic_navigate_next_black_24dp));
        riwayat.add(new Riwayat("Sabtu,\n03 Januari 2018","09.00 - 11.00",R.drawable.ic_navigate_next_black_24dp));

        ListView listView = view.findViewById(R.id.list_item);

        final RiwayatAdapter adapter = new RiwayatAdapter(getContext(), riwayat);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position);
            }
        });

        return view;
    }

}
