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
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.FieldAdapter;
import bola.wiradipa.org.lapanganbola.helpers.listeners.AdapterAlternativeListener;
import bola.wiradipa.org.lapanganbola.helpers.listeners.UniversalListener;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Field;

public class FieldsActivity extends BaseActivity {
    private List<Field> dataList;
    private FieldAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tvRentDate, tvHour, tvDuration;
    private boolean canSubmit = false;
    private String areaId, fieldTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields);

        checkSession();

        tvRentDate = findViewById(R.id.rent_date);
        tvHour = findViewById(R.id.hour);
        tvDuration = findViewById(R.id.duration);
        recyclerView = findViewById(R.id.recycler_view);
        dataList = new ArrayList<Field>();
        adapter = new FieldAdapter(context, dataList, new AdapterAlternativeListener<Field>() {
            @Override
            public void onDetailSelected(Field data) {
                Intent intent = new Intent(context, DetailFieldActivity.class);
                intent.putExtra(EXTRA_ID, data.getId());
                intent.putExtra(EXTRA_NAME, data.getName());
                intent.putExtra(EXTRA_PARENT_NAME, getStringExtraData(EXTRA_NAME));
                intent.putExtra(FILTER_DATE, tvRentDate.getText().toString());
                intent.putExtra(FILTER_HOUR, tvHour.getText().toString());
                intent.putExtra(FILTER_DURATION, tvDuration.getText().toString());
                intent.putExtra(FILTER_AREA, getStringExtraData(FILTER_AREA));
                intent.putExtra(FILTER_FIELD_TYPE, getStringExtraData(FILTER_FIELD_TYPE));
                intent.putExtra(CAN_SUBMIT, canSubmit);
                startActivity(intent);
            }

            @Override
            public void onNextSelected(Field data) {
                if(canSubmit) {
                    Intent intent = new Intent(context, RentActivity.class);
                    intent.putExtra(EXTRA_ID, data.getId());
                    intent.putExtra(EXTRA_NAME, data.getName());
                    intent.putExtra(EXTRA_PARENT_NAME, getStringExtraData(EXTRA_NAME));
                    intent.putExtra(FILTER_DATE, tvRentDate.getText().toString());
                    intent.putExtra(FILTER_HOUR, tvHour.getText().toString());
                    intent.putExtra(FILTER_DURATION, tvDuration.getText().toString());
                    intent.putExtra(FILTER_AREA, getStringExtraData(FILTER_AREA));
                    intent.putExtra(FILTER_FIELD_TYPE, getStringExtraData(FILTER_FIELD_TYPE));
                    startActivity(intent);
                } showSnackbar(R.string.error_no_data_filter);
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
        try {
            getSupportActionBar().setTitle(getStringExtraData(EXTRA_NAME));
        } catch (NullPointerException e){
            Log.d(TAG, e.toString());
        }

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

        if(filter_date!=null&&filter_duration!=null&&filter_hour!=null) {
            canSubmit = true;
        } else canSubmit = false;

        showProgressBar(true);
        mApiService.getFields(getUserToken(),filter_date, filter_hour, filter_duration, getLongExtraData(EXTRA_ID), areaId, fieldTypeId)
                .enqueue(getCallback.build());
    }

    public void fieldClick(View v){
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
            ApiData<Field> apiData = gson.fromJson(result, new TypeToken<ApiData<Field>>(){}.getType());
            Log.d(TAG, new Gson().toJson(apiData));
            dataList = apiData.getData();
            adapter.setData(dataList, canSubmit);
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
