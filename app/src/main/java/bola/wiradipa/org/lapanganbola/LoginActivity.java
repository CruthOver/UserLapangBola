package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.models.User;

public class LoginActivity extends BaseActivity {

    EditText etUsername, etPassword;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkSession2();

        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        tvRegister = findViewById(R.id.register);

        SpannableString content = new SpannableString(getString(R.string.register));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvRegister.setText(content);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    submitNow();
                    handled = true;
                }
                return handled;
            }
        });
    }

    public void onClickLogin(View v){
        Intent i = null;
        switch (v.getId()){
            case R.id.submit:
                //LOGIN
                submitNow();
                break;
            case R.id.register:
                //REGISTER PAGE
                i = new Intent(context, RegisterActivity.class);
                break;
            case R.id.forgot_password:
                i = new Intent(context, ForgotPasswordActivity.class);
                break;
        }
        if(i!=null)
            startActivity(i);
    }

    private boolean validateData(String username, String password){
        if(username.length()<4){
            etUsername.setError(getString(R.string.error_username));
            etUsername.requestFocus();
            return false;
        }
        if(password.length()<4){
            etPassword.setError(getString(R.string.error_password));
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void submitNow(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(!validateData(username, password))return;

        showProgressBar(true);
        mApiService.signinRequest(username, password).enqueue(loginCallback.build());

    }

    ApiCallback loginCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            User user = new Gson().fromJson(result, User.class);
            if(user.getToken()!=null&&user.getId()!=0) {
                appSession.createSession(user.getToken(), user);
                Intent i = new Intent(context, MainActivity.class);
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
