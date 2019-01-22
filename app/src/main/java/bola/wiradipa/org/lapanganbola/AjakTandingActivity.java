package bola.wiradipa.org.lapanganbola;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AjakTandingActivity extends AppCompatActivity {


    final Calendar myKalendar = Calendar.getInstance();
    EditText kalendarText;
    EditText timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajak_tanding);
        kalendarText = (EditText) findViewById(R.id.kalendar);
        timeText = (EditText) findViewById(R.id.time_picker);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myKalendar.set(Calendar.YEAR, year);
                myKalendar.set(Calendar.MONTH, month);
                myKalendar.set(Calendar.DAY_OF_YEAR, dayOfMonth);
                updateLabel();
            }
        };

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int menit = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(AjakTandingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeText.setText(hourOfDay + ":" + minute);
                    }
                },hour,menit,true);
                timePickerDialog.setTitle("Selected Time");
                timePickerDialog.show();
            }
        });

        kalendarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AjakTandingActivity.this,
                        date,myKalendar.get(Calendar.YEAR), myKalendar.get(Calendar.MONTH),
                        myKalendar.get(Calendar.DAY_OF_YEAR)).show();
            }
        });


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        kalendarText.setText(simpleDateFormat.format(myKalendar.getTime()));
    }
}
