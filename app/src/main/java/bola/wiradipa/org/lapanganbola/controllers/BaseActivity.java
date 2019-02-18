package bola.wiradipa.org.lapanganbola.controllers;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import bola.wiradipa.org.lapanganbola.BuildConfig;
import bola.wiradipa.org.lapanganbola.LoginActivity;
import bola.wiradipa.org.lapanganbola.MainActivity;
import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.AppSession;
import bola.wiradipa.org.lapanganbola.helpers.DateHelper;
import bola.wiradipa.org.lapanganbola.helpers.listeners.UniversalListener;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.BaseApiService;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.ApiStatus;
import bola.wiradipa.org.lapanganbola.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {
    public final static String TAG = "Lapangbola App";
    public static String FILTER_DATE = "rent_date";
    public static String FILTER_HOUR = "rent_hour";
    public static String FILTER_DURATION = "rent_duration";
    public static String FILTER_AREA = "rent_area";
    public static String FILTER_FIELD_TYPE = "rent_field_type";
    public static String EXTRA_ID = "id";
    public static String EXTRA_NAME = "name";
    public static String EXTRA_PARENT_NAME = "parent_name";
    public static String EXTRA_IMAGE = "image_path";
    public static String CAN_SUBMIT = "can_submit";
    public static String DATA = "data";

    private static int calendarResourceId ;//= R.id.birth_date;
    public AppSession appSession;
    public Context context;
    public BaseApiService mApiService;
    private Dialog progressDialog;

    public String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private int[] back_layouts = {R.layout.activity_filter_venues, R.layout.activity_venues,
            R.layout.activity_detail_venue, R.layout.activity_fields, R.layout.activity_rent,
            R.layout.activity_search_city, R.layout.activity_profile, R.layout.activity_edit_profile,
            R.layout.activity_upload_card, R.layout.activity_matches, R.layout.activity_share,
            R.layout.activity_statistic, R.layout.activity_detail_order, R.layout.activity_orders,
            R.layout.activity_setting, R.layout.activity_image};

    private int[] no_action_bars = {R.layout.activity_activation, R.layout.activity_register,
            R.layout.activity_login, R.layout.activity_forgot_password};

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initViews(layoutResID);
    }

    protected void initViews(int layout){
        context = this;
        appSession = new AppSession(this);
        mApiService = UtilsApi.getApiService();

        if(isNoActionbar(layout)){
            getWindow().setBackgroundDrawableResource(R.drawable.background);
        }

        if(isBack(layout)){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new Dialog(context);
    }

    private boolean isBack(int layout){
        for (int i=0;i<back_layouts.length;i++){
            if(layout==back_layouts[i])
                return true;
        }
        return false;
    }

    private boolean isNoActionbar(int layout){
        for (int i=0;i<no_action_bars.length;i++){
            if(layout==no_action_bars[i])
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean checkSession(){
        if(!appSession.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            startActivity(i);
            finish();
            return false;
        }
        return true;
    }

    public void checkSession2(){
        if(appSession.isLogin()) {
            Intent i = new Intent(context, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.label_logout)
                .setMessage(R.string.hint_logout);
        builder.setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    showProgressBar(true);
                    mApiService.logoutRequest(appSession.getData(AppSession.TOKEN))
                            .enqueue(logoutCallback.build());
                }
        });
        builder.setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void showProgressBar(boolean show){
        if(show){
            showProgres();
        } else {
            progressDialog.dismiss();
        }
    }

    public void showProcessBar(boolean show){
        if(show){
            showProcess();
        } else {
            progressDialog.dismiss();
        }
    }

    public abstract class ApiCallback{
        private Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        ApiStatus status = new Gson().fromJson(result, ApiStatus.class);
                        if(status==null){
                            onApiFailure(getString(R.string.error_parsing));
                            return;
                        }
                        if(status.getStatus()!=null) {
                            if (status.getStatus().compareToIgnoreCase("success") == 0) {
                                onApiSuccess(result);
                            } else {
                                onApiFailure(status.getMessage());
                            }
                        } else {
                            onApiFailure(getString(R.string.error_parsing));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String errormsg = "";
                    switch (response.code()){
                        case 500:
                            errormsg = getString(R.string.error_500);
                            break;
                        case 400:
                            errormsg = getString(R.string.error_400);
                            break;
                        case 403:
                            errormsg = getString(R.string.error_403);
                            break;
                        case 404:
                            errormsg = getString(R.string.error_404);
                            break;
                        default:
                            errormsg = getString(R.string.error_xxx);
                            break;
                    }
                    onApiFailure(errormsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onApiFailure(t.getMessage());
            }
        };

        public Callback<ResponseBody> build() {
            return callback;
        }

        public abstract void onApiSuccess(String result);
        public abstract void onApiFailure(String errorMessage);
    }

    ApiCallback logoutCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            appSession.logout();
            checkSession();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            appSession.logout();
            checkSession();
//            showSnackbar(errorMessage);
        }
    };

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public TextView holder;
        public UniversalListener listener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, this, year, month, day);
            dpd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.label_reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(holder!=null)
                        holder.setText(holder.getHint());
                    TextView date = getActivity().findViewById(calendarResourceId);
                    if(date!=null)
                        date.setText(date.getHint());
                    if(listener!=null)
                        listener.onResetData();
                }
            });
            dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dpd.getDatePicker().setMinDate(new Date().getTime());
            dpd.getDatePicker().setMaxDate(DateHelper.addDate(new Date(), DateHelper.MONTH, 1).getTime());
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String setDate = getTwoDigitNumber(day)+"-"+getTwoDigitNumber(month+1)+"-"+year;
            if(holder!=null)
                holder.setText(setDate);
            TextView date = getActivity().findViewById(calendarResourceId);
            if(date!=null)
                date.setText(setDate);
            if(listener!=null)
                listener.onSetData();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        calendarResourceId = v.getId();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showWarning(int title, int message, int ok, int cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    finish();
                }
            });
        }
        if(cancel!=0) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void showEmptyMessage(int title, int message, int ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    dialog.dismiss();
                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void showWarning(final Intent intent, int title, int message, int ok, int cancel){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    startActivity(intent);
                    finish();
                }
            });
        }
        if(cancel!=0) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button

                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void alertSubmitDone(int title, int message, int ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public void alertSubmitDone(String title, String message, int ok){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
        if(ok!=0){
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
        }

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    public String replaceCommaToDot(String s){
        return s.replace(",",".");
    }

    public String replaceDotToComma(String s){
        return s.replace(".",",");
    }

    public User getUserSession(){
        return new Gson().fromJson(appSession.getData(AppSession.USER), User.class);
    }

    public static String getTwoDigitNumber(int number){
        if(number/10 > 0)return ""+number;
        return "0"+number;
    }

    public String getUserToken(){
        return appSession.getData(AppSession.TOKEN);
    }

    public String getUserPhone(){
        if(BuildConfig.DEBUG)
            return "+628112229922";
        return getUserSession().getPhoneNumber();
    }

    public void showSnackbar(String message){
        if(findViewById(R.id.coordinator_layout)==null)return;
        Snackbar.make(findViewById(R.id.coordinator_layout), message,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    public void showSnackbar(int messageId){
        if(findViewById(R.id.coordinator_layout)==null)return;
        Snackbar.make(findViewById(R.id.coordinator_layout), messageId,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    public void showToast(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int messageId){
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
    }

    private void showProgres(){
        progressDialog.setContentView(R.layout.progressdialog);
        TextView message = progressDialog.findViewById(R.id.tv_process);
        message.setText(R.string.process_api);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void showProcess(){
        progressDialog.setContentView(R.layout.progressdialog);
        TextView message = progressDialog.findViewById(R.id.tv_process);
        message.setText(R.string.processing);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public String getStringExtraData(String name){
        return getIntent().getStringExtra(name);
    }

    public long getLongExtraData(String name){
        return getIntent().getLongExtra(name, 0);
    }

    public boolean getBooleanExtraData(String name){
        return getIntent().getBooleanExtra(name, false);
    }

    public void finishWithWarning(){
        showWarning(R.string.warning_title, R.string.warning_back, R.string.warning_ok,
                R.string.warning_cancel);
    }

    public boolean checkAllPermission(){
        for(int i=0;i<permissions.length;i++){
            if(ContextCompat.checkSelfPermission(context,
                    permissions[i]) != PackageManager.PERMISSION_GRANTED)
                return false;
        }

        return true;
    }

    public boolean checkPermission(int requestCode){
        if (!checkAllPermission()) {
            ActivityCompat.requestPermissions((Activity) context,
                    permissions, requestCode);
            return false;
        } else {
            return true;
        }
    }

    public String changeToServerDate(String date){
        if(date==null) return null;
        String[] dateSplit = date.split("-");
        if(dateSplit.length<3) return null;
        return dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
    }
}
