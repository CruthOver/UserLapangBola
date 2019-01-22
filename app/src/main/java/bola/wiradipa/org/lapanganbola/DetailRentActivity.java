package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.models.Rent;
import bola.wiradipa.org.lapanganbola.models.User;

public class DetailRentActivity extends BaseActivity {

    private TextView tvRentDate, tvTitleField, tvTitleVenue, tvHour, tvEndHour, tvDuration,
            tvUserName, tvUserPhone, tvUserEmail, tvPayment, tvTransactionNumber, tvPaymentStatus;

    private String rent_date;
    private int start_hour, end_hour;
    private long field_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rent);

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
        tvPayment = findViewById(R.id.payment);
        tvTransactionNumber = findViewById(R.id.transaction_number);
        tvPaymentStatus = findViewById(R.id.payment_status);

        initData();
    }

    private void initData(){
        Rent rent = new Gson().fromJson(getStringExtraData(DATA), Rent.class);

        tvTitleField.setText(rent.getField());
        tvTitleVenue.setText(rent.getVenue());
//        tvRentDate.setText(DateHelper.formatDate("dd-MM-yyyy", rent.getRental_date()));
        tvRentDate.setText(DateHelper.getDate(rent.getRental_date()));
        tvHour.setText(rent.getStart_hour()+":00");
        tvEndHour.setText(rent.getEnd_hour()+":00");
        if(rent.getEnd_hour()<rent.getStart_hour())
            rent.setEnd_hour(rent.getEnd_hour()+24);
        tvDuration.setText(rent.getEnd_hour()-rent.getStart_hour()+" jam");
        User user = getUserSession();
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserPhone.setText(user.getPhoneNumber());
        tvTransactionNumber.setText(rent.getId_transaction());
        tvPayment.setText(MoneyHelper.toMoneyWithRp(""+rent.getAmount()));
        String[] statuses = getResources().getStringArray(R.array.dp_status);
        if(rent.getDown_payment_status()<4)
            tvPaymentStatus.setText(statuses[rent.getDown_payment_status()]);
        if(rent.getRejection_reason()!=null)
            tvPaymentStatus.setText(statuses[rent.getDown_payment_status()]+rent.getRejection_reason());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
