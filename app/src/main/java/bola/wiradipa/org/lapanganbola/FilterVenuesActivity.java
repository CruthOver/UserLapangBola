package bola.wiradipa.org.lapanganbola;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.City;
import bola.wiradipa.org.lapanganbola.models.FieldType;
import bola.wiradipa.org.lapanganbola.models.Venue;

public class FilterVenuesActivity extends BaseActivity {
    private final int SEARCH_CITY = 1;

    private TextView tvRentDate, tvHour, tvDuration, tvCity;
    private Button btnSubmit;
    private Spinner spFieldType;
    private List<FieldType> fieldTypes;
    private City city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_venues);

        btnSubmit = findViewById(R.id.submit);
        tvRentDate = findViewById(R.id.rent_date);
        tvHour = findViewById(R.id.hour);
        tvDuration = findViewById(R.id.duration);
        tvCity = findViewById(R.id.city);

        spFieldType = findViewById(R.id.field_type);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                submitNow();
                Intent i = new Intent(context, VenuesActivity.class);
                i.putExtra(FILTER_DATE, tvRentDate.getText().toString());
                i.putExtra(FILTER_HOUR, tvHour.getText().toString());
                i.putExtra(FILTER_DURATION, tvDuration.getText().toString());
                if(fieldTypes.size()>0)
                    i.putExtra(FILTER_FIELD_TYPE, ""+((FieldType)spFieldType.getSelectedItem()).getId());
                if(city!=null)
                    i.putExtra(FILTER_AREA, ""+city.getId());
                startActivity(i);
            }
        });

        tvDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDurationDialog();
            }
        });

        tvHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHourDialog();
            }
        });

        tvRentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRentDateDialog();
            }
        });

        tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchCity();
            }
        });

        showProgressBar(true);
        mApiService.getFieldTypes(getUserToken()).enqueue(new ApiCallback() {
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                ApiData<FieldType> apiData = new Gson().fromJson(result, new TypeToken<ApiData<FieldType>>(){}.getType());
                fieldTypes = apiData.getData();
                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, fieldTypes);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spFieldType.setAdapter(adapter);
            }

            @Override
            public void onApiFailure(String errorMessage) {
                showProgressBar(false);
            }
        }.build());
    }

    private void submitNow(){
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
        mApiService.getFieldOwners(getUserToken(),filter_date, filter_hour, filter_duration, null, null)
                .enqueue(getCallback.build());
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiData<Venue> apiData = new Gson().fromJson(result, new TypeToken<ApiData<Venue>>(){}.getType());
            Log.d(TAG, new Gson().toJson(apiData));
            List<Venue> venues = apiData.getData();
            if(venues.size()>0){
                Intent intent = new Intent(context, VenuesActivity.class);
                intent.putExtra("data", new Gson().toJson(venues));
                startActivity(intent);
            } else
                showSnackbar(R.string.error_zero_data);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

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
            }
        });
        builder.setNegativeButton(R.string.label_reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvDuration.setText(tvDuration.getHint());
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
            }
        });
        builder.setNegativeButton(R.string.label_reset, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvHour.setText(tvHour.getHint());
            }
        });
        builder.show();
    }

    private void showRentDateDialog(){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.holder = tvRentDate;
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showSearchCity(){
        Intent intent = new Intent(context, SearchCityActivity.class);
        if(city!=null)
            intent.putExtra("city", new Gson().toJson(city));
        startActivityForResult(intent, SEARCH_CITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            city = new Gson().fromJson(data.getStringExtra("city"), City.class);
            tvCity.setText(city.getName());
        }
    }
}
