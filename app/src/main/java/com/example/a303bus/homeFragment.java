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


public class homeFragment extends Fragment {

    Button btnsearch;
    EditText from,to;
    TextView txtdate;
    String date1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        btnsearch= view.findViewById(R.id.btnsearch);
        txtdate= view.findViewById(R.id.txtdate1);
        date1=txtdate.getText().toString();

        from= view.findViewById(R.id.edfrom);
        String edfrom=from.getText().toString();
        to= view.findViewById(R.id.edto);
        String edto=to.getText().toString();
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=   cal.get(Calendar.YEAR);
                int month=  cal.get(Calendar.MONTH);
                int day=    cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd=new DatePickerDialog(getActivity(),dateset,year,month,day);
                dpd.show();
            }
        });

        return view;
    }
    DatePickerDialog.OnDateSetListener dateset=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
//            Toast.makeText(getActivity(), "selected data: "+year+"-"+(month+1)+"-"+day,Toast.LENGTH_LONG).show();
            txtdate.setText(year+"-"+(month+1)+"-"+day);
        }
    };
}

