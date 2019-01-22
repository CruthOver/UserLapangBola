package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.MyDividerItemDecoration;
import bola.wiradipa.org.lapanganbola.helpers.adapters.CitiesAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.City;

public class SearchCityActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private List<City> dataList;
    private CitiesAdapter adapter;
    private SearchView searchView;
    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        checkSession();

        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<>();
        adapter = new CitiesAdapter(context, dataList, new AdapterListener<City>() {
            @Override
            public void onItemSelected(City data) {
                Intent intent=new Intent();
                intent.putExtra("city",new Gson().toJson(data));
                setResult(RESULT_OK,intent);
                finish();
            }

            @Override
            public void onItemLongSelected(City data) {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 1));
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        showProgressBar(true);
        mApiService.getCities(getUserToken()).enqueue(new ApiCallback(){
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
                ApiData<City> apiData = gson.fromJson(result, new TypeToken<ApiData<City>>(){}.getType());
                Log.d(TAG, new Gson().toJson(apiData));
                dataList = apiData.getData();
                adapter.setData(dataList);
                if(dataList.size()==0)
                    showSnackbar(R.string.error_zero_data);
                if(city!=null)
                    searchView.setQuery(city.getName(), true);
            }

            @Override
            public void onApiFailure(String errorMessage) {
                showProgressBar(false);
                showSnackbar(errorMessage);
            }
        }.build());

        String extra = getIntent().getStringExtra("city");
        if(extra!=null)
            city = new Gson().fromJson(extra, City.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.searchBar);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Cari Area");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(adapter!=null) {
                    if(adapter.getItemCount()>1)
                    adapter.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        searchView.setIconified(false);
        if(city!=null)
            searchView.setQuery(city.getName(), false);

        return true;
    }
}
