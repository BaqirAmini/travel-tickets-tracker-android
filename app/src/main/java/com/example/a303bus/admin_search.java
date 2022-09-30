package com.example.a303bus;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class admin_search extends Fragment {
    EditText from, to;
    Button search;
    TextView txtdate;

    String edfrom, edto, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_search, container, false);
        from = (EditText) view.findViewById(R.id.edfrom2);
        edfrom = from.getText().toString();
        to = (EditText) view.findViewById(R.id.edto2);
        edto = to.getText().toString();

        txtdate = (TextView) view.findViewById(R.id.txtdate);
        date = txtdate.getText().toString();

        search = (Button) view.findViewById(R.id.btnsearch2);
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}

