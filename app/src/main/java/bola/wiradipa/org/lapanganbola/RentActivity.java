package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Date;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.models.Rent;
import bola.wiradipa.org.lapanganbola.models.User;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class RentActivity extends BaseActivity {
    private TextView tvRentDate, tvTitleField, tvTitleVenue, tvHour, tvEndHour, tvDuration,
            tvUserName, tvUserPhone, tvUserEmail;

    private String rent_date;
    private int start_hour, end_hour;
    private long field_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        checkSession();

        tvTitleField = findViewById(R.id.title_field);
        tvTitleVenue = findViewById(R.id.title_venue);
        tvRentDate= findViewById(R.id.rent_date);
        tvHour= findViewById(R.id.hour);
        tvEndHour= findViewById(R.id.end_hour);
        tvDuration= findViewById(R.id.duration);
        tvUserName= findViewById(R.id.user_name);
        tvUserPhone= findViewById(R.id.user_phone);
        tvUserEmail= findViewById(R.id.user_email);

        initData();
    }

    private void initData(){
        tvTitleField.setText(getStringExtraData(EXTRA_NAME));
        tvTitleVenue.setText(getStringExtraData(EXTRA_PARENT_NAME));
        tvRentDate.setText(getStringExtraData(FILTER_DATE));
        tvHour.setText(getStringExtraData(FILTER_HOUR));
        tvEndHour.setText(getStringExtraData(FILTER_HOUR));
        tvDuration.setText(getStringExtraData(FILTER_DURATION));
        User user = getUserSession();
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserPhone.setText(user.getPhoneNumber());
        Date rentDate = DateHelper.parsingDate("dd-MM-yyyy kk:mm",
                getStringExtraData(FILTER_DATE)+" "+getStringExtraData(FILTER_HOUR));
        Date endDate = DateHelper.addDate(rentDate,DateHelper.HOUR,
                Integer.parseInt(getStringExtraData(FILTER_DURATION).split(" ")[0]));
        tvEndHour.setText(DateHelper.formatDate("kk:mm", endDate));
        tvRentDate.setText(DateHelper.getDate(rentDate));

        field_id = getLongExtraData(EXTRA_ID);
        rent_date = changeToServerDate(getStringExtraData(FILTER_DATE));
        start_hour = Integer.parseInt(DateHelper.formatDate("kk", rentDate));
        end_hour = Integer.parseInt(DateHelper.formatDate("kk", endDate));
    }

    public void rentClick(View v){
        switch (v.getId()){
            case R.id.submit:
                submit();
                break;
            case R.id.cancel:
                finishWithWarning();
                break;
        }
    }

    private void submit(){
        showProgressBar(true);
        mApiService.createFieldRental(getUserToken(),rent_date,start_hour,end_hour,field_id)
                .enqueue(createCallback);
    }

    Callback<ResponseBody> createCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            showToast(R.string.submit_ok);
            Rent rent = new Gson().fromJson(result, Rent.class);
            Intent intent = new Intent(context, PaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(DATA, new Gson().toJson(rent));
            startActivity(intent);
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    }.build();

    @Override
    public void onBackPressed() {
        finishWithWarning();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishWithWarning();
        }

        return super.onOptionsItemSelected(item);
    }
}
