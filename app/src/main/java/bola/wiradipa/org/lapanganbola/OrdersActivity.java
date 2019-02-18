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
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.MyDividerItemDecoration;
import bola.wiradipa.org.lapanganbola.helpers.adapters.RentAdapter;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterAlternativeListener;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Rent;

public class OrdersActivity extends BaseActivity {
    private RentAdapter adapter;
    private RecyclerView recyclerView;
    private List<Rent> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        checkSession();

        Log.d(getUserToken());

        dataList = new ArrayList<>();
        adapter = new RentAdapter(context, dataList, new AdapterAlternativeListener<Rent>() {
            @Override
            public void onDetailSelected(Rent data) {
                if(data.getDown_payment_status()!=0) {
                    gotoDetail(data);
                } else gotoPayment(data);
            }

            @Override
            public void onNextSelected(Rent data) {
                gotoPayment(data);
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(context, MyDividerItemDecoration.VERTICAL_LIST, 2));
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        initData();
    }

    private void initData(){
        showProgressBar(true);
        mApiService.getRentals(getUserToken()).enqueue(new ApiCallback() {
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                ApiData<Rent> apiData = new Gson().fromJson(result, new TypeToken<ApiData<Rent>>(){}.getType());
                Log.d(TAG, new Gson().toJson(apiData));
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

    private void gotoDetail(Rent rent){
        Intent intent = new Intent(context, DetailOrderActivity.class);
        intent.putExtra(DATA, new Gson().toJson(rent));
        startActivity(intent);
    }

    private void gotoPayment(Rent rent){
        Intent intent = new Intent(context, PaymentActivity.class);
        intent.putExtra(DATA, new Gson().toJson(rent));
        startActivity(intent);
    }
}
