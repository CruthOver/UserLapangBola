package bola.wiradipa.org.lapanganbola;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.adapters.SettingAdapter;
import bola.wiradipa.org.lapanganbola.models.Setting;

public class SettingActivity extends BaseActivity {
    private SettingAdapter adapter;
    private RecyclerView recyclerView;
    private List<Setting> dataList;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        checkSession();

        dataList = new ArrayList<>();
        adapter = new SettingAdapter(context, dataList, null);

        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        initData();
    }

    private void initData(){
        String[] titles = getResources().getStringArray(R.array.setting_titles);
        String[] descriptions = getResources().getStringArray(R.array.setting_descriptions);
        for(int i=0;i<titles.length;i++){
            dataList.add(new Setting(i+1, titles[i], descriptions[i]));
        }
        adapter.setData(dataList);
    }
}
