package bola.wiradipa.org.lapanganbola;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.models.ApiStatus;
import bola.wiradipa.org.lapanganbola.models.User;

public class ActivationActivity extends BaseActivity {

    private EditText etActivation;
    private Button btnSubmit;
    private String phoneNumber, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        checkSession2();

        phoneNumber = getIntent().getStringExtra("phone_number");
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

        etActivation = findViewById(R.id.activation_code);
        btnSubmit = findViewById(R.id.submit);

        etActivation.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    activationNow();
                    handled = true;
                }
                return handled;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activationNow();
            }
        });
    }


    private boolean validateData(){
        String activationCode = etActivation.getText().toString();
        if(activationCode.length()<4){
            etActivation.setError(getString(R.string.error_activation));
            etActivation.requestFocus();
            return false;
        }
        return true;
    }

    private void activationNow(){
        String activationCode = etActivation.getText().toString();

        if(validateData()) {
            showProgressBar(true);
            mApiService.activation(phoneNumber, activationCode)
                    .enqueue(activationCallback.build());
        }
    }

    @Override
    public void onBackPressed() {
        finishWithWarning();
    }

    ApiCallback activationCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            ApiStatus status = new Gson().fromJson(result, ApiStatus.class);
            alertSubmitDone(status.getStatus(), status.getMessage(), R.string.label_ok);
//            showSnackbar(R.string.register_ok);
//            finish();
        }
        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

    @Override
    public void alertSubmitDone(String title, String message, int ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mApiService.signinRequest(username, password).enqueue(loginCallback.build());
                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    ApiCallback loginCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            User user = new Gson().fromJson(result, User.class);
            if(user.getToken()!=null&&user.getId()!=0) {
                appSession.createSession(user.getToken(), user);
                Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            }
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };
}