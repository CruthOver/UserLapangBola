package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.FieldPagerAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.GridViewAdapter;
import bola.wiradipa.org.lapanganbola.models.Booking;
import bola.wiradipa.org.lapanganbola.models.Field;

public class DetailFieldActivity extends BaseActivity {

    private TextView tvTitleField,tvTitleVenue,tvRentRate,tvPitchSize,tvGrassType,tvBookingCancel;
    private Button btnSubmit;
    private ViewPager viewPager;
    private FieldPagerAdapter vpAdapter;
    private LinearLayout sliderDotspanel;
    private GridView mGridView;

    private int dotscount;
    private ImageView[] dots;
    private boolean canSubmit=false;
    private NonScrollGridView nonScrollGridView;

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

        tvTitleField = findViewById(R.id.title_field);
        tvTitleVenue = findViewById(R.id.title_venue);
        tvRentRate = findViewById(R.id.rent_rate);
        tvPitchSize = findViewById(R.id.pitch_size);
        tvGrassType = findViewById(R.id.grass_type);
        tvBookingCancel = findViewById(R.id.booking_cancel);
        btnSubmit = findViewById(R.id.submit);
        mGridView = findViewById(R.id.gridview);

        final ArrayList<Booking> booking = new ArrayList<Booking>();
        booking.add(new Booking("00.00 - 01.00" , "100k"));
        booking.add(new Booking("01.00 - 02.00" , "100k"));
        booking.add(new Booking("02.00 - 03.00" , "100k"));
        booking.add(new Booking("03.00 - 04.00" , "100k"));
        booking.add(new Booking("04.00 - 05.00" , "100k"));
        booking.add(new Booking("05.00 - 06.00" , "100k"));
        booking.add(new Booking("06.00 - 07.00" , "100k"));
        booking.add(new Booking("07.00 - 08.00" , "125k"));
        booking.add(new Booking("08.00 - 09.00" , "125k"));
        booking.add(new Booking("09.00 - 10.00" , "125k"));
        booking.add(new Booking("10.00 - 11.00" , "125k"));
        booking.add(new Booking("11.00 - 12.00" , "125k"));
        booking.add(new Booking("12.00 - 13.00" , "150k"));
        booking.add(new Booking("13.00 - 14.00" , "150k"));
        booking.add(new Booking("14.00 - 15.00" , "150k"));
        booking.add(new Booking("15.00 - 16.00" , "150k"));
        booking.add(new Booking("16.00 - 17.00" , "150k"));
        booking.add(new Booking("17.00 - 18.00" , "175k"));
        booking.add(new Booking("18.00 - 19.00" , "175k"));
        booking.add(new Booking("19.00 - 20.00" , "175k"));
        booking.add(new Booking("20.00 - 21.00" , "175k"));
        booking.add(new Booking("21.00 - 22.00" , "175k"));
        booking.add(new Booking("22.00 - 23.00" , "175k"));
        booking.add(new Booking("23.00 - 24.00" , "175k"));

        nonScrollGridView = (NonScrollGridView) findViewById(R.id.spotsView);
        nonScrollGridView.setExpanded(true);
        GridViewAdapter adapter = new GridViewAdapter(this, booking);
        nonScrollGridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        canSubmit = getBooleanExtraData(CAN_SUBMIT);

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
}
