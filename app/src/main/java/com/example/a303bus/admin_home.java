package com.example.a303bus;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class admin_home extends Fragment {


    EditText edfrom, edto, edtp, edtc;
    Button btnadd;
    TextView txttime, txtdate;
    String date, time, fromWhere, toWhere, tctPrice0, tctComp;
    String adminID, dateFormat, depTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);


        btnadd = (Button) view.findViewById(R.id.btnadd);

        edfrom = (EditText) view.findViewById(R.id.from);
        edto = (EditText) view.findViewById(R.id.edto);
        edtp = (EditText) view.findViewById(R.id.edtp);
        edtc = (EditText) view.findViewById(R.id.edfrom);

        txtdate = (TextView) view.findViewById(R.id.txtdate2);
        date = txtdate.getText().toString();
        AdminInfo adminInfo = new AdminInfo();
        adminID = adminInfo.getAdminID();
        txttime = (TextView) view.findViewById(R.id.txttime);
        time = txttime.getText().toString();

        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onValidate();
            }
        });

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
        @SuppressLint("SetTextI18n")
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            dateFormat = String.format(Locale.getDefault(), "%02d-%02d-%02d", year, month+1, day);
            txtdate.setText(dateFormat);
        }

    };
    TimePickerDialog.OnTimeSetListener timeset = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int min) {
            depTime = String.format(Locale.getDefault(),"%02d:%02d", hour, min);
            txttime.setText(depTime);
        }
    };

    private void onValidate() {


        fromWhere = edfrom.getText().toString();
        toWhere = edto.getText().toString();
        tctPrice0 = edtp.getText().toString();
//        double tctPrice = Double.parseDouble(edtp.getText().toString());
        tctComp = edtc.getText().toString();
        if (!fromWhere.isEmpty() && !toWhere.isEmpty() && !tctComp.isEmpty()) {
            if (fromWhere.length() < 4) {
                edfrom.setError("It must be at least 4 characters.");
            }
            if (toWhere.length() < 4) {
                edto.setError("It must be at least 4 characters.");
            }
            if (Double.parseDouble(tctPrice0) <= 0) {
                edtp.setError("Ticket price must be greater than zero.");
            }
            if (tctComp.length() < 4) {
                edtc.setError("It must be at least 4 characters.");
            }
            if (fromWhere.length() >= 4 && toWhere.length() >= 4 && Double.parseDouble(tctPrice0) > 0 && tctComp.length() >= 4) {
//                Random ticket ID generated
                int min = 100;
                int max = 999;
                Random random = new Random();
                int tick_num = random.nextInt(max + 1 - min) + min;
//                Toast.makeText(getActivity(), "Now Admin ID: " + adminID, Toast.LENGTH_SHORT).show();
                if (Build.VERSION.SDK_INT >= 23) {
                    ExternalStoragePermission.verifyStoragePermissions(getActivity());
                }
                try {
                    File inStorage = requireActivity().getExternalFilesDir(null);
                    String desPath = inStorage.getAbsolutePath() + "/bus303/database/";
                    SQLiteDatabase bus_db = SQLiteDatabase.openDatabase(desPath + "/303bus_db.sqlite", null, 0);
                    ContentValues values = new ContentValues();
                    values.put("source", fromWhere);
                    values.put("destination", toWhere);
                    values.put("price", tctPrice0);
                    values.put("transport_company", tctComp);
                    values.put("ticket_NO", String.format(Locale.getDefault(), "%06d", tick_num));
                    values.put("dep_date", dateFormat);
                    values.put("dep_time", depTime);

                    bus_db.insertOrThrow("tickets", null, values);
                    Toast.makeText(getActivity(), "Ticket added successfully!", Toast.LENGTH_LONG).show();
                    edfrom.setText("");
                    edto.setText("");
                    edtp.setText("");
                    edtp.setText("");
                } catch (Exception e)
                {
                    Log.e("INSERT_ERROR", "Cannot insert into tickets because " + e.getMessage());
                }
            }
        } else {
            if (fromWhere.isEmpty()) {
                edfrom.setError("From place required.");
            }
            if (toWhere.isEmpty()) {
                edto.setError("To place required.");
            }
            if (tctPrice0.isEmpty()) {
                edtp.setError("Ticket price required.");
            }
            if (tctComp.isEmpty()) {
                edtc.setError("Ticket company required.");
            }
        }
    }


}