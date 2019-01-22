package bola.wiradipa.org.lapanganbola;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import bola.wiradipa.org.lapanganbola.controllers.BasePhotoActivity;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Bank;
import bola.wiradipa.org.lapanganbola.models.Rent;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PaymentActivity extends BasePhotoActivity {
    private TextView tvTitleField, tvTitleVenue, tvCopy, tvAccount;
    private ImageView ivFile, ivBank;
    private Button btnSubmit;
    private Spinner spBanks;
    private List<Bank> banks;
    private Rent rent;
    private Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        checkSession();

        banks = new ArrayList<>();
        ivFile = findViewById(R.id.payment_file_image);
        ivBank = findViewById(R.id.bank_logo);
        tvTitleField = findViewById(R.id.title_field);
        tvTitleVenue = findViewById(R.id.title_venue);
        tvAccount = findViewById(R.id.account);
        spBanks = findViewById(R.id.banks);
        tvCopy = findViewById(R.id.salin);
        btnSubmit = findViewById(R.id.submit);
        picasso = Picasso.get();
        spBanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Bank bank = banks.get(i);
                picasso.load(UtilsApi.BASE_URL+bank.getLogo())
                        .fit()
                        .into(ivBank);
                tvAccount.setText(bank.getAccountName()+"\n"+bank.getAccountNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(banks.size()>0) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    Bank bank = (Bank)spBanks.getSelectedItem();
                    ClipData clip = ClipData.newPlainText("account_number", bank.getAccountNumber());
                    clipboard.setPrimaryClip(clip);
                }
            }
        });

        initData();

    }

    public void onClickButton(View view){
        switch (view.getId()){
            case R.id.submit:
                submitData();
                break;
            case R.id.payment_file:
                whichImage();
                break;
            case R.id.back:
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void initData(){
        rent = new Gson().fromJson(getStringExtraData(DATA), Rent.class);

        tvTitleVenue.setText(rent.getVenue());
        tvTitleField.setText(rent.getField());
        SpannableString content = new SpannableString(getString(R.string.hint_copy_text));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvCopy.setText(content);

        showProgressBar(true);
        mApiService.getBankAccounts().enqueue(new ApiCallback() {
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                ApiData<Bank> apiData = new Gson().fromJson(result, new TypeToken<ApiData<Bank>>(){}.getType());
                banks = apiData.getData();
                ArrayAdapter<Bank> adapter = new ArrayAdapter<Bank>(context, android.R.layout.simple_list_item_1, banks);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBanks.setAdapter(adapter);
                spBanks.setSelection(0);
            }

            @Override
            public void onApiFailure(String errorMessage) {
                showProgressBar(false);
                showSnackbar(errorMessage);
            }
        }.build());
    }

    @Override
    public void setPic() {
        ivFile.setVisibility(View.VISIBLE);
        File image = new File(mCurrentPhotoPath);
        picasso.load(image)
                .fit()
                .into(ivFile);
        btnSubmit.setEnabled(true);
    }

    private void submitData(){
        String auth_token = getUserToken();
        long id = rent.getId();

        MultipartBody.Part body = createPartPhoto(mCurrentPhotoPath, "receipt");

        RequestBody rb_auth_token =
                RequestBody.create(
                        MediaType.parse("text/plain"), auth_token);

        showProgressBar(true);
        mApiService.uploadReceiptRent(id, rb_auth_token, body)
                .enqueue(new ApiCallback() {
                    @Override
                    public void onApiSuccess(String result) {
                        showProgressBar(false);
                        showToast(R.string.submit_ok);
                        Intent intent = new Intent(context, DetailRentActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra(DATA, new Gson().toJson(rent));
                        startActivity(intent);
                    }

                    @Override
                    public void onApiFailure(String errorMessage) {
                        showProgressBar(false);
                        showSnackbar(errorMessage);
                    }
                }.build());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, DetailOrderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(DATA, new Gson().toJson(rent));
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(context, DetailOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra(DATA, new Gson().toJson(rent));
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
