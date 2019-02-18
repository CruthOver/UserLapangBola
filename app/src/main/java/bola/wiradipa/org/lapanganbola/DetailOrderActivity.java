package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.QRCodeHelper;
import bola.wiradipa.org.lapanganbola.models.Rent;
import bola.wiradipa.org.lapanganbola.models.User;

public class DetailOrderActivity extends BaseActivity {

    public final static int QRcodeWidth = 300 ;
//    private static final String IMAGE_DIRECTORY = "/QRcodeDemonuts";

    private TextView tvRentDate, tvTitleField, tvTitleVenue, tvHour, tvEndHour, tvDuration,
            tvPayment, tvCancel, tv_back, tvPaymentStatus, tvIdTransaction;
    private ImageView imgQrCode;
    private Button submit;
    private Rent rent;
    Bitmap bitmap ;
    QRCodeHelper qrCodeHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        checkSession();

        qrCodeHelper = new QRCodeHelper(this);
        tvTitleField = findViewById(R.id.title_field);
        tvTitleVenue = findViewById(R.id.title_venue);
        tvRentDate= findViewById(R.id.rent_date);
        tvHour= findViewById(R.id.hour);
        tvEndHour= findViewById(R.id.end_hour);
        tvDuration= findViewById(R.id.duration);
        tvPayment = findViewById(R.id.payment);
        tvCancel = findViewById(R.id.cancel);
        submit = findViewById(R.id.pay);
        tv_back = findViewById(R.id.back);
        tvPaymentStatus = findViewById(R.id.payment_status);
        tvIdTransaction =  findViewById(R.id.id_transaction);
        imgQrCode = findViewById(R.id.image_qr_code);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra(DATA, new Gson().toJson(rent));
                startActivity(intent);
            }
        });

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

//        initData();
    }

    private void initData(){
        rent = new Gson().fromJson(getStringExtraData(DATA), Rent.class);

        tvTitleField.setText(rent.getField());
        tvTitleVenue.setText(rent.getVenue());
//        tvRentDate.setText(DateHelper.formatDate("dd-MM-yyyy", rent.getRental_date()));
        tvIdTransaction.setText(rent.getId_transaction());
        tvRentDate.setText(DateHelper.getDate(rent.getRental_date()));
        tvHour.setText(rent.getStart_hour()+":00");
        tvEndHour.setText(rent.getEnd_hour()+":00");
        if(rent.getEnd_hour()<rent.getStart_hour())
            rent.setEnd_hour(rent.getEnd_hour()+24);
        tvDuration.setText(rent.getEnd_hour()-rent.getStart_hour()+" jam");
        User user = getUserSession();
        tvPayment.setText(MoneyHelper.toMoneyWithRp(""+rent.getAmount()));
        String[] statuses = getResources().getStringArray(R.array.dp_status);
        if(rent.getDown_payment_status()<4)
            tvPaymentStatus.setText(statuses[rent.getDown_payment_status()]);
        if(rent.getRejection_reason()!=null)
            tvPaymentStatus.setText(statuses[rent.getDown_payment_status()]+rent.getRejection_reason());

        switch (rent.getDown_payment_status()){
            case 1:
                submit.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
                imgQrCode.setVisibility(View.VISIBLE);
                generateQRCode();
                break;
            case 3:
                submit.setText(getString(R.string.label_reupload_payment));
                break;
        }
    }

    private void generateQRCode(){
        rent = new Gson().fromJson(getStringExtraData(DATA), Rent.class);
        try {
            bitmap = qrCodeHelper.TextToImageEncode(rent.getId_transaction(), QRcodeWidth);

            imgQrCode.setImageBitmap(bitmap);
//            String path = qrCodeHelper.saveImage(bitmap, IMAGE_DIRECTORY);  //give read write permission
//            Toast.makeText(this, "QRCode saved to -> "+path, Toast.LENGTH_SHORT).show();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
        }

        return super.onOptionsItemSelected(item);
    }

    private void back(){
        Intent intent = new Intent(context, OrdersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
