package bola.wiradipa.org.lapanganbola;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterListener;
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.listeners.UniversalListener;
import bola.wiradipa.org.lapanganbola.helpers.adapters.VenueAdapter;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Venue;

public class VenuesActivity extends BaseActivity {
    private List<Venue> dataList;
    private VenueAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvRentDate, tvHour, tvDuration;
    private String fieldTypeId, areaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);

        checkSession();

        tvRentDate = findViewById(R.id.rent_date);
        tvHour = findViewById(R.id.hour);
        tvDuration = findViewById(R.id.duration);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Venue>();
        adapter = new VenueAdapter(context, dataList, new AdapterListener<Venue>() {
            @Override
            public void onItemSelected(Venue data) {
                Intent i = new Intent(context, DetailVenueActivity.class);
                i.putExtra(EXTRA_ID, data.getId());
                i.putExtra(FILTER_DATE, tvRentDate.getText().toString());
                i.putExtra(FILTER_HOUR, tvHour.getText().toString());
                i.putExtra(FILTER_DURATION, tvDuration.getText().toString());
                i.putExtra(FILTER_AREA, areaId);
                i.putExtra(FILTER_FIELD_TYPE, fieldTypeId);
                startActivity(i);
            }

            @Override
            public void onItemLongSelected(Venue data) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        whiteNotificationBar(recyclerView);

        tvRentDate.setText(getStringExtraData(FILTER_DATE));
        tvHour.setText(getStringExtraData(FILTER_HOUR));
        tvDuration.setText(getStringExtraData(FILTER_DURATION));
        areaId = getStringExtraData(FILTER_AREA);
        fieldTypeId = getStringExtraData(FILTER_FIELD_TYPE);

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData(){
        String rent_date = tvRentDate.getText().toString();
        String start_hour = tvHour.getText().toString();
        String duration = tvDuration.getText().toString();

        String filter_date=null, filter_hour=null, filter_duration=null;

        if(rent_date.compareToIgnoreCase(getString(R.string.hint_rent_date))!=0)
            filter_date = changeToServerDate(rent_date);
        if(start_hour.compareToIgnoreCase(getString(R.string.hint_hour))!=0)
            filter_hour = start_hour;
        if(duration.compareToIgnoreCase(getString(R.string.hint_duration))!=0)
            filter_duration = duration.split(" ")[0];

        showProgressBar(true);
        mApiService.getFieldOwners(getUserToken(),filter_date, filter_hour, filter_duration, areaId, fieldTypeId)
                .enqueue(getCallback.build());
    }

    public void venueClick(View v){
        switch (v.getId()){
            case R.id.holder_duration:
                showDurationDialog();
                break;
            case R.id.holder_hour:
                showHourDialog();
                break;
            case R.id.holder_rent_date:
                showRentDateDialog();
                break;
        }
    }

    private void showDurationDialog(){
        NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(0);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvDuration.setText(newVal+" "+getString(R.string.unit_duration));
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(numberPicker);
        builder.setTitle(R.string.label_duration);
        builder.setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initData();
            }
        });
        builder.setNegativeButton(R.string.label_reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvDuration.setText(tvDuration.getHint());
                initData();
            }
        });
        builder.show();
    }

    private void showHourDialog(){
        NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMaxValue(24);
        numberPicker.setMinValue(1);
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value+":00";
            }
        });
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvHour.setText(newVal+":00");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(numberPicker);
        builder.setTitle(R.string.label_hour);
        builder.setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initData();
            }
        });
        builder.setNegativeButton(R.string.label_reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvHour.setText(tvHour.getHint());
                initData();
            }
        });
        builder.show();
    }

    private void showRentDateDialog(){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.holder = tvRentDate;
        newFragment.listener = new UniversalListener() {
            @Override
            public void onSetData() {
                initData();
            }

            @Override
            public void onResetData() {
                initData();
            }
        };
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
            ApiData<Venue> apiData = gson.fromJson(result, new TypeToken<ApiData<Venue>>(){}.getType());
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
    };


}
