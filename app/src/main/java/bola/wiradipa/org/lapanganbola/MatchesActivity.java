package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.adapters.MatchesAdapter;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.BaseApiService;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Match;

public class MatchesActivity extends BaseActivity {
    private List<Match> dataList;
    private MatchesAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        checkSession();

        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Match>();
        adapter = new MatchesAdapter(context, dataList, new AdapterListener<Match>() {
            @Override
            public void onItemSelected(Match data) {
                Intent i = new Intent(context, ImageActivity.class);
                i.putExtra(EXTRA_ID, data.getId());
                startActivity(i);
            }

            @Override
            public void onItemLongSelected(Match data) {
                Intent i = new Intent(context, ImageActivity.class);
                i.putExtra(EXTRA_ID, data.getId());
                startActivity(i);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        initData();
    }

    private void initData(){
        BaseApiService liveService = UtilsApi.getApiLiveService();
        showProgressBar(true);
        liveService.getMatches(getUserPhone()).enqueue(new ApiCallback() {
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                ApiData<Match> apiData = new Gson().fromJson(result, new TypeToken<ApiData<Match>>(){}.getType());
                dataList = apiData.getData();
                adapter.setData(dataList);
                if(dataList.size()==0)
                    showSnackbar(R.string.error_zero_data);
            }

            @Override
            public void onApiFailure(String errorMessage) {
                showProgressBar(false);
                showSnackbar(errorMessage);
            }
        }.build());
    }
}
