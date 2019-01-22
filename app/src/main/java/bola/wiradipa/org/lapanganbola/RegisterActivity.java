package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;

public class RegisterActivity extends BaseActivity {
    private EditText etName, etUsername, etEmail, etPhone, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkSession2();

        etName = findViewById(R.id.name);
        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPhone = findViewById(R.id.phone);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirm_password);

        etConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

    public void onClickRegister(View v){
        switch (v.getId()){
            case R.id.submit:
                //REGISTER
                submitNow();
                break;
        }
    }

    private boolean validateData(String name, String username, String email, String phone,
                                 String password, String confirmPassword){
        if(name.length()<4){
            etName.setError(getString(R.string.error_name));
            etName.requestFocus();
            return false;
        }
        if(username.length()<4){
            etUsername.setError(getString(R.string.error_username));
            etUsername.requestFocus();
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getString(R.string.error_email));
            etEmail.requestFocus();
            return false;
        }
        if(!Patterns.PHONE.matcher(phone).matches()){
            etPhone.setError(getString(R.string.error_phone));
            etPhone.requestFocus();
            return false;
        }
        if(password.length()<4){
            etPassword.setError(getString(R.string.error_password));
            etPassword.requestFocus();
            return false;
        }
        if(confirmPassword.length()<4){
            etConfirmPassword.setError(getString(R.string.error_confirm_password));
            etConfirmPassword.requestFocus();
            return false;
        }
        if(password.compareTo(confirmPassword)!=0){
            etConfirmPassword.setError(getString(R.string.error_password_not_match));
            etConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkBack(){
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if(name.isEmpty()&&username.isEmpty()&&email.isEmpty()&&phone.isEmpty()&&password.isEmpty()
                &&confirmPassword.isEmpty())
            return true;
        return false;
    }

    private void submitNow(){
        String name = etName.getText().toString();
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        if(!validateData(name, username, email, phone, password, confirmPassword))
            return;

        showProgressBar(true);
        mApiService.signupRequest(username,name,email,phone,password,confirmPassword)
                .enqueue(registerCallback.build());
    }

    ApiCallback registerCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Intent i = new Intent(context, ActivationActivity.class);
            i.putExtra("phone_number", etPhone.getText().toString());
            i.putExtra("username", etUsername.getText().toString());
            i.putExtra("password", etPassword.getText().toString());
            startActivity(i);
            finish();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

    @Override
    public void onBackPressed() {
        if(checkBack()){
            finish();
        } else
            finishWithWarning();

    }
}
