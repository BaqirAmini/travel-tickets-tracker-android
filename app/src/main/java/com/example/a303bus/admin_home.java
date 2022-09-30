package com.example.a303bus;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class admin_home extends Fragment {


    EditText edfrom, edto, edtp, edtc;
    Button btnadd;
    TextView txttime, txtdate;
    String date, time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);


        btnadd = (Button) view.findViewById(R.id.btnadd);

                edfrom = (EditText) view.findViewById(R.id.from);
                String from = edfrom.getText().toString();
                edto = (EditText) view.findViewById(R.id.edto);
                String to = edto.getText().toString();
                edtp = (EditText) view.findViewById(R.id.edtp);
                String tp = edtp.getText().toString();
                edtc = (EditText) view.findViewById(R.id.edfrom);
                String tc = edtc.getText().toString();

                txtdate = (TextView) view.findViewById(R.id.txtdate2);
                date = txtdate.getText().toString();

                txttime = (TextView) view.findViewById(R.id.txttime);
                time = txttime.getText().toString();


                txtdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpd = new DatePickerDialog(getActivity(), dateset, year, month, day);
                        dpd.show();
                    }
                });
                txttime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int hour = cal.get(Calendar.HOUR);
                        int min = cal.get(Calendar.MINUTE);

                        TimePickerDialog tpd = new TimePickerDialog(getActivity(), timeset, hour, min, false);
                        tpd.show();
                    }
                });
                return view;

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