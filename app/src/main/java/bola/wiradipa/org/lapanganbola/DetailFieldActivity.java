package bola.wiradipa.org.lapanganbola;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.DatePicker;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.FieldPagerAdapter;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Booking;
import bola.wiradipa.org.lapanganbola.helpers.adapters.GridViewAdapter;
import bola.wiradipa.org.lapanganbola.models.Field;

public class DetailFieldActivity extends BaseActivity {

    private TextView tvTitleField,tvTitleVenue,tvRentRate,tvPitchSize,tvGrassType,tvBookingCancel
            , tvTanggal;
    private Button btnSubmit;
    private ViewPager viewPager;
    private GridView gvSchedule;
    private GridViewAdapter gvAdapter;
    private FieldPagerAdapter vpAdapter;
    private LinearLayout sliderDotspanel;

    private int dotscount;
    private ImageView[] dots;
    private boolean canSubmit=false;
    private NonScrollGridView nonScrollGridView;
    private RecyclerView recyclerView;

    DatePickerDialog.OnDateSetListener mDatePicker;
    String df_full, mDate, name;


    private Booking booking;
    private List<Booking> bookingList;
    private Field field;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_field);

        checkSession();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookingList = new ArrayList<Booking>();
        tvTitleField = findViewById(R.id.title_field);
        tvTitleVenue = findViewById(R.id.title_venue);
        tvRentRate = findViewById(R.id.rent_rate);
        tvPitchSize = findViewById(R.id.pitch_size);
        tvGrassType = findViewById(R.id.grass_type);
        tvBookingCancel = findViewById(R.id.booking_cancel);
        tvTanggal = findViewById(R.id.tanggal);
        btnSubmit = findViewById(R.id.submit);
//        gvSchedule = findViewById(R.id.gridview);

        nonScrollGridView = (NonScrollGridView) findViewById(R.id.spotsView);
        nonScrollGridView.setExpanded(true);
        recyclerView=findViewById(R.id.gridview);

        gvAdapter = new GridViewAdapter(this, bookingList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(gvAdapter);

        gvAdapter.notifyDataSetChanged();
            canSubmit = getBooleanExtraData(CAN_SUBMIT);
//        parsingData();
        Toast.makeText(context, getStringExtraData(FILTER_DATE), Toast.LENGTH_SHORT).show();


        if(canSubmit) {
            btnSubmit.setVisibility(View.VISIBLE);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RentActivity.class);
                    intent.putExtra(EXTRA_ID, field.getId());
                    intent.putExtra(EXTRA_NAME, field.getName());
                    intent.putExtra(EXTRA_PARENT_NAME, field.getVenue_name());
                    intent.putExtra(FILTER_DATE, getStringExtraData(FILTER_DATE));
                    intent.putExtra(FILTER_HOUR, getStringExtraData(FILTER_HOUR));
                    intent.putExtra(FILTER_DURATION, getStringExtraData(FILTER_DURATION));
                    startActivity(intent);
                }
            });
        } else
            btnSubmit.setVisibility(View.GONE);

        viewPager = findViewById(R.id.viewpager);
        sliderDotspanel = findViewById(R.id.dots_layout);

        tvTitleVenue.setText(getStringExtraData(EXTRA_PARENT_NAME));
        tvTitleField.setText(getStringExtraData(EXTRA_NAME));

        showProgressBar(true);
        mApiService.getField(getLongExtraData(EXTRA_ID), getUserToken()).enqueue(getCallback.build());
        if (getStringExtraData(FILTER_DATE).equals(getString(R.string.hint_rent_date))){
            mApiService.getScheduleField(getUserToken(), getLongExtraData(EXTRA_ID), getDate(getStringExtraData(FILTER_DATE)))
                    .enqueue(getSchedule.build());
        } else {
            mApiService.getScheduleField(getUserToken(), getLongExtraData(EXTRA_ID), getStringExtraData(FILTER_DATE)).enqueue(getSchedule.build());
        }
    }

    private void initData(){


        tvRentRate.setText(MoneyHelper.toMoney(""+field.getRent_rate()));
        tvPitchSize.setText(field.getPitch_size()+" "+getString(R.string.label_unit_length));
        tvGrassType.setText(field.getGrass_type_name());
        tvBookingCancel.setText("-");

        List<Field> fields = new ArrayList<Field>();
        fields.add(field);

        vpAdapter = new FieldPagerAdapter(context, fields, null);
        viewPager.setAdapter(vpAdapter);

        Date rentDate = DateHelper.parsingDate("dd-MM-yyyy kk:mm",
                getStringExtraData(FILTER_DATE)+" "+getStringExtraData(FILTER_HOUR));

        gvAdapter = new GridViewAdapter(context, bookingList);
        gvAdapter.setBackgroundStartHour(getStringExtraData(FILTER_HOUR));
        if (!getStringExtraData(FILTER_DURATION).equals("Pilih Durasi")){
            Date endDate = DateHelper.addDate(rentDate,DateHelper.HOUR,
                    Integer.parseInt(getStringExtraData(FILTER_DURATION).split(" ")[0]));
            gvAdapter.setBackgroundEndHour(DateHelper.formatDate("kk:mm", endDate));
        }
        recyclerView.setAdapter(gvAdapter);
//        gvSchedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("ResourceType")
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                if (gvAdapter.lyBackground.getTag().equals(R.drawable.dot_tersedia)){
////                    gvAdapter.lyBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.dot_green));
////                    gvAdapter.lyBackground.setId(R.drawable.dot_green);
////                }
//                Toast.makeText(context, adapterView.getSelectedItem()+"", Toast.LENGTH_SHORT).show();
//            }
//        });

        dotscount = vpAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    private String getDate(String date){
//        final Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        String tempDate = null;
//
//        Locale local = new Locale("id", "id");
//
//        month = month + 1;
//        date = year + "-" + checkDigit(month) + "-" + checkDigit(day);
//
//        cal.setTimeInMillis(0);
//        cal.set(year, month, day, 0, 0, 0);
//
//        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, local);
//        df_full = dateFormat.format(cal.getTime());
//
//        tempDate = getIntent().getStringExtra("date");
//        if(tempDate!=null){
//            date = tempDate;
//        }else{
//            date = year + "-" + checkDigit(month) + "-" + checkDigit(day);
//        }
//
//        tvTanggal.setText(df_full);
//        mDatePicker = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                String tempDate = year + "-" + checkDigit(month) + "-" + checkDigit(dayOfMonth);
//                cal.set(year, month, dayOfMonth, 0 , 0 , 0);
//                df_full = dateFormat.format(cal.getTime());
////                if (!getStringExtraData(FILTER_DATE).equals(R.string.hint_rent_date)){
////                    df_full = getStringExtraData(FILTER_DATE);
////                } else {
////                    df_full = dateFormat.format(cal.getTime());
////                }
//                tvTanggal.setText(df_full);
//                gvAdapter.clearData();
//                mApiService.getScheduleField(getUserToken(), getLongExtraData(EXTRA_ID), tempDate)
//                        .enqueue(getSchedule.build());
////                refreshActivity();
//
//            }
//        };
//
//        tvTanggal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(DetailFieldActivity.this,
//                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
//                        mDatePicker,year,month,day);
//                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//                dialog.show();
//            }
//        });
//
//        return date;
//    }

//    public String checkDigit(int number)
//    {
//        return number<=9?"0"+number:String.valueOf(number);
//    }

    private String getDate(String date){
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String tempDate = null;

        Locale local = new Locale("id", "id");

        month = month + 1;
        date = year + "-" + checkDigit(month) + "-" + checkDigit(day);

        cal.setTimeInMillis(0);
        cal.set(year, month, day, 0, 0, 0);

        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, local);
        df_full = dateFormat.format(cal.getTime());

        tempDate = getIntent().getStringExtra("date");
        if(tempDate!=null){
            date = tempDate;
        }else{
            date = year + "-" + checkDigit(month) + "-" + checkDigit(day);
        }

        tvTanggal.setText(df_full);
        mDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String tempDate = year + "-" + checkDigit(month) + "-" + checkDigit(dayOfMonth);
                cal.set(year, month, dayOfMonth, 0 , 0 , 0);
                df_full = dateFormat.format(cal.getTime());
//                if (!getStringExtraData(FILTER_DATE).equals(R.string.hint_rent_date)){
//                    df_full = getStringExtraData(FILTER_DATE);
//                } else {
//                    df_full = dateFormat.format(cal.getTime());
//                }
                tvTanggal.setText(df_full);
                gvAdapter.clearData();
                mApiService.getScheduleField(getUserToken(), getLongExtraData(EXTRA_ID), tempDate)
                        .enqueue(getSchedule.build());
//                refreshActivity();

            }
        };

        tvTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(DetailFieldActivity.this,
                        R.style.Theme_AppCompat_DayNight_Dialog_MinWidth,
                        mDatePicker,year,month,day);
                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        return date;
    }

//    private void parsingData(){
//        if (getStringExtraData(FILTER_DATE).equals(R.string.hint_rent_date)){
//            getDate(getStringExtraData(FILTER_DATE));
//            Toast.makeText(context, getStringExtraData(FILTER_DATE), Toast.LENGTH_SHORT).show();
//        } else if (!getStringExtraData(FILTER_HOUR).equals(R.string.hint_hour)){
//
//        }
//    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
            field = gson.fromJson(result, Field.class);
            Log.d(TAG, new Gson().toJson(field));
            if(field!=null)
                initData();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

    ApiCallback getSchedule = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
            ApiData<Booking> apiData = gson.fromJson(result, new TypeToken<ApiData<Booking>>(){}.getType());
            Log.d(TAG, new Gson().toJson(apiData));
            bookingList = apiData.getData();
            gvAdapter.setData(bookingList);
            if(bookingList.size()==0)
                showSnackbar(R.string.error_zero_data);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

    public void setValue(String name){
        this.name = name;
    }
}
