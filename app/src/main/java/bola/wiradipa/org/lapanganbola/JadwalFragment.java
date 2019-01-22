package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class JadwalFragment extends Fragment {


    public  JadwalFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.list_item,container,false);

        ArrayList<Jadwal> jadwal = new ArrayList<>();

        jadwal.add(new Jadwal("Jumat, \n27 Juli 2018","20.00 - 21.00",R.drawable.ic_navigate_next_black_24dp));
        jadwal.add(new Jadwal("Sabtu, \n28 Juli 2018","20.00 - 22.00",R.drawable.ic_navigate_next_black_24dp));
        jadwal.add(new Jadwal("Senin, \n03 Agustus 2018","18.00 - 21.00",R.drawable.ic_navigate_next_black_24dp));
        jadwal.add(new Jadwal("Minggu, \n04 September 2018","06.00 - 07.00",R.drawable.ic_navigate_next_black_24dp));

        ListView listView = view.findViewById(R.id.list_item);

        final JadwalAdapter adapter = new JadwalAdapter(getActivity(),jadwal);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position);
                Intent intent = new Intent(getActivity(), AkanDatangActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
