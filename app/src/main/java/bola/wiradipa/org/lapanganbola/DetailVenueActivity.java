package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.Log;
import bola.wiradipa.org.lapanganbola.helpers.MoneyHelper;
import bola.wiradipa.org.lapanganbola.helpers.adapters.DoubleTypeAdapter;
import bola.wiradipa.org.lapanganbola.helpers.adapters.FacilitiesAdapter;
import bola.wiradipa.org.lapanganbola.models.Venue;

public class DetailVenueActivity extends BaseActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private GridView gridView;
    private FacilitiesAdapter adapter;
    private TextView tvName,tvDescription,tvRentRate,tvAddress;
    private Button btnNext;
    private Venue venue;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_venue);

        checkSession();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        gridView = findViewById(R.id.gridview);
        tvName = findViewById(R.id.name);
        tvDescription = findViewById(R.id.description);
        tvRentRate = findViewById(R.id.rent_rate);
        tvAddress = findViewById(R.id.address);
        btnNext = findViewById(R.id.next);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(venue!=null) {
                    Intent intent = new Intent(context, FieldsActivity.class);
                    intent.putExtra(EXTRA_ID, venue.getId());
                    intent.putExtra(EXTRA_NAME, venue.getName());
                    intent.putExtra(FILTER_DATE, getStringExtraData(FILTER_DATE));
                    intent.putExtra(FILTER_HOUR, getStringExtraData(FILTER_HOUR));
                    intent.putExtra(FILTER_DURATION, getStringExtraData(FILTER_DURATION));
                    intent.putExtra(FILTER_AREA, getStringExtraData(FILTER_AREA));
                    intent.putExtra(FILTER_FIELD_TYPE, getStringExtraData(FILTER_FIELD_TYPE));
                    startActivity(intent);
                }
            }
        });
    }

    private void initData(){
        tvName.setText(venue.getName());
        tvDescription.setText(venue.getDescription());
        tvRentRate.setText(MoneyHelper.toMoneyWithRp(""+venue.getRent_rate()));
        tvAddress.setText(venue.getAddress());
        adapter = new FacilitiesAdapter(context, venue.getFacilities());
        gridView.setAdapter(adapter);
        if(venue.getLatitude()!=0.0&&venue.getLongitude()!=0.0){
            LatLng loc = new LatLng(venue.getLatitude(), venue.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(loc);
            gMap.addMarker(markerOptions);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15.0f));
        }
    }

    ApiCallback getCallback = new ApiCallback() {
        @Override
        public void onApiSuccess(String result) {
            showProgressBar(false);
            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).create();
            venue = gson.fromJson(result, Venue.class);
            Log.d(TAG, new Gson().toJson(venue));
            if(venue!=null)
                initData();
        }

        @Override
        public void onApiFailure(String errorMessage) {
            showProgressBar(false);
            showSnackbar(errorMessage);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(10);
        gMap.setIndoorEnabled(true);
        UiSettings uiSettings = gMap.getUiSettings();
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setScrollGesturesEnabled(false);
        uiSettings.setTiltGesturesEnabled(false);
        uiSettings.setZoomGesturesEnabled(false);

        LatLng starting = new LatLng(-6.914744, 107.609810);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(starting));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(starting,10.0f));
        mApiService.getFieldOwner(getLongExtraData(EXTRA_ID), getUserToken()).enqueue(getCallback.build());
    }
}
