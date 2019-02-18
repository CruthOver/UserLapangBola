package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.AppSession;
import bola.wiradipa.org.lapanganbola.models.ApiData;
import bola.wiradipa.org.lapanganbola.models.Info;

public class MainActivity extends BaseActivity {

//    private TextView mTextMessage;
    private HomeFragment homeFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.fragment_container, homeFragment).commit();
                    return true;
                case R.id.order:
                    Intent order = new Intent(context, OrdersActivity.class);
                    startActivity(order);
                    return true;
                case R.id.help:
//                    logout();
                    return true;
                case R.id.message:
                    return true;
                case R.id.setting:
                    Intent setting = new Intent(context, SettingActivity.class);
                    startActivity(setting);
                    return true;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(checkSession()) {

            checkPermission(0);

            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            Log.d("TOKEEEEN", getUserToken());

            homeFragment = new HomeFragment();
            homeFragment.setUser(getUserSession());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment, "Beranda");
            fragmentTransaction.commit();

//            mTextMessage = findViewById(R.id.message);
            BottomNavigationViewEx navigation = findViewById(R.id.navigation);
            navigation.enableAnimation(false);
            navigation.enableShiftingMode(false);
            navigation.enableItemShiftingMode(false);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            mApiService.getSliders().enqueue(new ApiCallback() {
                @Override
                public void onApiSuccess(String result) {
                    ApiData<Info> apiData = new Gson().fromJson(result, new TypeToken<ApiData<Info>>() {
                    }.getType());
                    homeFragment.initSlider(apiData.getData());
                }

                @Override
                public void onApiFailure(String errorMessage) {

                }
            }.build());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(homeFragment!=null) {
            appSession = new AppSession(context);
            homeFragment.setUser(getUserSession());
            homeFragment.refresh();
        }
    }
}