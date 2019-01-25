package bola.wiradipa.org.lapanganbola;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CarilawanActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton rb1,rb2,rb3,rb4,rb5;
    Button btnDatePicker;
    EditText txtDate;
    private int mYear,mMonth,mDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb1 = (RadioButton) findViewById(R.id.btn_choice1);
        rb2 = (RadioButton) findViewById(R.id.btn_choice2);
        rb3 = (RadioButton) findViewById(R.id.btn_choice3);
        rb4 = (RadioButton) findViewById(R.id.btn_choice4);
        rb5 = (RadioButton) findViewById(R.id.btn_choice5);
        txtDate=(EditText)findViewById(R.id.rent_date);
        txtDate.setOnClickListener(this);

        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        rb3.setOnClickListener(this);
        rb4.setOnClickListener(this);
        rb5.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_choice1:
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
            case R.id.btn_choice2:
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
            case R.id.btn_choice3:
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
            case R.id.btn_choice4:
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
            case R.id.btn_choice5:
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
        }
        if (v == txtDate){
            final Calendar c = Calendar.getInstance();


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    c.set(Calendar.YEAR,year);
                    c.set(Calendar.MONTH,monthOfYear);
                    c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyy");
                    txtDate.setText(dateFormat.format(c.getTime()));
                }
            },c.get(Calendar.YEAR),c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();

        }
    }
}