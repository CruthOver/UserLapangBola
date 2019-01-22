package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.adapters.ImageShareAdapter;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.ImageShare;

public class ShareActivity extends BaseActivity {
    private List<ImageShare> dataList;
    private RecyclerView recyclerView;
    private ImageShareAdapter adapter;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        checkSession();

        dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        next = findViewById(R.id.submit);
        adapter = new ImageShareAdapter(context, dataList, new AdapterListener<ImageShare>() {
            @Override
            public void onItemSelected(ImageShare data) {
            }

            @Override
            public void onItemLongSelected(ImageShare data) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StatisticActivity.class);
                intent.putExtra(EXTRA_ID, getLongExtraData(EXTRA_ID));
                startActivity(intent);
            }
        });

        initData();
    }

    private void initData(){
        dataList.add(new ImageShare(1, "url"));
        dataList.add(new ImageShare(2, "url"));
        dataList.add(new ImageShare(3, "url"));
        adapter.setData(dataList);
    }
}
