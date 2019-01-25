package bola.wiradipa.org.lapanganbola;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.app.DatePickerDialog;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuatPertandinganActivity extends AppCompatActivity implements View.OnClickListener,
        OnMapReadyCallback {
    RadioButton rb1,rb2,rb3,rb4,rb5;
    GoogleMap mMap;
    TextView kalender;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Context mContext;
    private String mStartHour, mEndHour;
    Spinner jammulai,jamselesai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;

        SupportMapFragment mapFragment =(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        inicomponent();

    }
    public void inicomponent() {

        rb1 = (RadioButton) findViewById(R.id.rb_choice_1);
        rb2 = (RadioButton) findViewById(R.id.rb_choice_2);
        rb3 = (RadioButton) findViewById(R.id.rb_choice_3);
        rb4 = (RadioButton) findViewById(R.id.rb_choice_4);
        rb5 = (RadioButton) findViewById(R.id.rb_choice_5);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);

        jamselesai = (Spinner) findViewById(R.id.spn_jamselesai);
        jammulai = (Spinner) findViewById(R.id.spn_jammulai);


        kalender = (TextView) findViewById(R.id.kalendar);
        kalender.setOnClickListener(this);

        final Calendar calendar = Calendar.getInstance();
        String formatdate = "dd MMMM yyyy";
        SimpleDateFormat dateFormat= new SimpleDateFormat(formatdate);
        kalender.setText(dateFormat.format(calendar.getTime()));
        endhour();
        starthour();
    }

    private void endhour(){
        ArrayAdapter start = ArrayAdapter
                .createFromResource(mContext, R.array.EndHour,R.layout.spinner_jadwal);
        start.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        jamselesai.setAdapter(start);
        jamselesai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection =(String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals("--- pilih jam --")){
                        Toast.makeText(mContext ,"pilih jam terlebih dahulu",Toast.LENGTH_SHORT).show();
                    }
                    if(selection.equals("00.00")){
                        mEndHour = "00.00";
                    }else if(selection.equals("01.00")){
                        mEndHour ="01.00";
                    }else if(selection.equals("02.00")){
                        mEndHour ="02.00";
                    }else if(selection.equals("03.00")){
                        mEndHour ="03.00";
                    }else if(selection.equals("04.00")){
                        mEndHour ="04.00";
                    }else if(selection.equals("05.00")){
                        mEndHour ="05.00";
                    }else if(selection.equals("06.00")){
                        mEndHour ="06.00";
                    }else if(selection.equals("06.00")){
                        mEndHour ="06.00";
                    }else if(selection.equals("06.00")){
                        mEndHour ="06.00";
                    }else if(selection.equals("07.00")){
                        mEndHour ="07.00";
                    }else if(selection.equals("08.00")){
                        mEndHour ="08.00";
                    }else if(selection.equals("09.00")){
                        mEndHour ="09.00";
                    }else if(selection.equals("10.00")){
                        mEndHour ="10.00";
                    }else if(selection.equals("11.00")){
                        mEndHour ="11.00";
                    }else if(selection.equals("12.00")){
                        mEndHour ="12.00";
                    }else if(selection.equals("13.00")){
                        mEndHour ="13.00";
                    }else if(selection.equals("14.00")){
                        mEndHour ="14.00";
                    }else if(selection.equals("15.00")){
                        mEndHour ="15.00";
                    }else if(selection.equals("16.00")){
                        mEndHour ="16.00";
                    }else if(selection.equals("17.00")){
                        mEndHour ="17.00";
                    }else if(selection.equals("18.00")){
                        mEndHour ="18.00";
                    }else if(selection.equals("19.00")){
                        mEndHour ="19.00";
                    }else if(selection.equals("20.00")){
                        mEndHour ="20.00";
                    }else if(selection.equals("21.00")){
                        mEndHour ="21.00";
                    }else if(selection.equals("22.00")){
                        mEndHour ="22.00";
                    }else if(selection.equals("23.00")){
                        mEndHour ="23.00";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(mContext, "Hari belum dipilih", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void starthour(){
        ArrayAdapter fromdayspinner = ArrayAdapter.createFromResource(mContext,
                R.array.StartHour,R.layout.spinner_jadwal);
        fromdayspinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        jammulai.setAdapter(fromdayspinner);
        jammulai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection =(String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals("--- pilih jam --")){
                        Toast.makeText(mContext ,"pilih jam terlebih dahulu",Toast.LENGTH_SHORT).show();
                    }
                    if(selection.equals("00.00")){
                        mStartHour = "00.00";
                    }else if(selection.equals("01.00")){
                        mStartHour ="01.00";
                    }else if(selection.equals("02.00")){
                        mStartHour ="02.00";
                    }else if(selection.equals("03.00")){
                        mStartHour ="03.00";
                    }else if(selection.equals("04.00")){
                        mStartHour ="04.00";
                    }else if(selection.equals("05.00")){
                        mStartHour ="05.00";
                    }else if(selection.equals("06.00")){
                        mStartHour ="06.00";
                    }else if(selection.equals("06.00")){
                        mStartHour ="06.00";
                    }else if(selection.equals("06.00")){
                        mStartHour ="06.00";
                    }else if(selection.equals("07.00")){
                        mStartHour ="07.00";
                    }else if(selection.equals("08.00")){
                        mStartHour ="08.00";
                    }else if(selection.equals("09.00")){
                        mStartHour ="09.00";
                    }else if(selection.equals("10.00")){
                        mStartHour ="10.00";
                    }else if(selection.equals("11.00")){
                        mStartHour ="11.00";
                    }else if(selection.equals("12.00")){
                        mStartHour ="12.00";
                    }else if(selection.equals("13.00")){
                        mStartHour ="13.00";
                    }else if(selection.equals("14.00")){
                        mStartHour ="14.00";
                    }else if(selection.equals("15.00")){
                        mStartHour ="15.00";
                    }else if(selection.equals("16.00")){
                        mStartHour ="16.00";
                    }else if(selection.equals("17.00")){
                        mStartHour ="17.00";
                    }else if(selection.equals("18.00")){
                        mStartHour ="18.00";
                    }else if(selection.equals("19.00")){
                        mStartHour ="19.00";
                    }else if(selection.equals("20.00")){
                        mStartHour ="20.00";
                    }else if(selection.equals("21.00")){
                        mStartHour ="21.00";
                    }else if(selection.equals("22.00")){
                        mStartHour ="22.00";
                    }else if(selection.equals("23.00")){
                        mStartHour ="23.00";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(mContext, "Hari belum dipilih", Toast.LENGTH_SHORT).show();
            }



        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_choice_1:
                if(rb1.isSelected()){
                    rb1.setSelected(false);
                    rb1.setChecked(false);
                }else{
                    rb1.setSelected(true);
                    rb1.setChecked(true);
                    rb2.setChecked(false);
                    rb2.setSelected(false);
                    rb3.setChecked(false);
                    rb3.setSelected(false);
                    rb4.setChecked(false);
                    rb4.setSelected(false);
                    rb5.setChecked(false);
                    rb5.setSelected(false);
                }
                break;
            case R.id.rb_choice_2:
                if(rb2.isSelected()){
                    rb2.setSelected(false);
                    rb2.setChecked(false);
                }else {
                    rb2.setSelected(true);
                    rb2.setSelected(true);
                    rb1.setChecked(false);
                    rb1.setSelected(false);
                    rb3.setChecked(false);
                    rb3.setSelected(false);
                    rb4.setChecked(false);
                    rb4.setSelected(false);
                    rb5.setChecked(false);
                    rb5.setSelected(false);
                }
                break;
            case R.id.rb_choice_3:
                if(rb3.isSelected()){
                    rb3.setSelected(false);
                    rb3.setChecked(false);
                }else {
                    rb3.setSelected(true);
                    rb3.setChecked(true);
                    rb1.setChecked(false);
                    rb1.setSelected(false);
                    rb2.setChecked(false);
                    rb2.setSelected(false);
                    rb4.setChecked(false);
                    rb4.setSelected(false);
                    rb5.setChecked(false);
                    rb5.setSelected(false);
                }
                break;
            case R.id.rb_choice_4:
                if(rb4.isSelected()){
                    rb4.setSelected(false);
                    rb4.setChecked(false);
                }else {
                    rb4.setSelected(true);
                    rb4.setChecked(true);
                    rb1.setChecked(false);
                    rb1.setSelected(false);
                    rb2.setChecked(false);
                    rb2.setSelected(false);
                    rb3.setChecked(false);
                    rb3.setSelected(false);
                    rb5.setChecked(false);
                    rb5.setSelected(false);
                }
                break;
            case R.id.rb_choice_5:
                if(rb5.isSelected()){
                    rb5.setSelected(false);
                    rb5.setChecked(false);
                }else {
                    rb5.setSelected(true);
                    rb5.setChecked(true);
                    rb1.setChecked(false);
                    rb1.setSelected(false);
                    rb2.setChecked(false);
                    rb2.setSelected(false);
                    rb3.setChecked(false);
                    rb3.setSelected(false);
                    rb4.setChecked(false);
                    rb4.setSelected(false);
                }
                break;
        }if (v == kalender){
            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                            calendar.set(Calendar.YEAR, mYear);
                            calendar.set(Calendar.MONTH, mMonth);
                            calendar.set(Calendar.DAY_OF_MONTH, mDay);
                            String formatdate = "dd MMMM yyyy";
                            SimpleDateFormat dateFormat= new SimpleDateFormat(formatdate);
                            kalender.setText(dateFormat.format(calendar.getTime()));
                        }
                    },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
