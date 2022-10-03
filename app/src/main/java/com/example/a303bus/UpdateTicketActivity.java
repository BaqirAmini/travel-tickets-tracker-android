package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class UpdateTicketActivity extends AppCompatActivity {

    EditText edfrom, edto, edtp, edtc;
    Button btnupdate;
    TextView txttime, txtdate;
    String date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ticket);
        getSupportActionBar().hide();
        btnupdate = (Button) findViewById(R.id.btnupdate2);

        edfrom = (EditText) findViewById(R.id.from);
        String from = edfrom.getText().toString();
        edto = (EditText) findViewById(R.id.edto);
        String to = edto.getText().toString();
        edtp = (EditText) findViewById(R.id.edtp);
        String tp = edtp.getText().toString();
        edtc = (EditText) findViewById(R.id.edfrom);
        String tc = edtc.getText().toString();

        txtdate = (TextView) findViewById(R.id.txtdate2);
        date = txtdate.getText().toString();

        txttime = (TextView) findViewById(R.id.txttime);
        time = txttime.getText().toString();


        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getApplicationContext(), dateset, year, month, day);
                dpd.show();
            }
        });
        txttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int min = cal.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(getApplicationContext(), timeset, hour, min, false);
                tpd.show();
            }
        });


    }

    DatePickerDialog.OnDateSetListener dateset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            txtdate.setText(year + "-" + (month + 1) + "-" + day);
        }

    };
    TimePickerDialog.OnTimeSetListener timeset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {
            txttime.setText(hour + ":" + min);
        }
    };
}
