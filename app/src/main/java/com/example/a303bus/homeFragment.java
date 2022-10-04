package com.example.a303bus;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class homeFragment extends Fragment {

    Button btnSearchUser;
    EditText edUserFrom,edUserTo;
    TextView txtUserDate;
    String dateUserVal, formattedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);
        btnSearchUser= view.findViewById(R.id.btnSearchUser);
        txtUserDate= view.findViewById(R.id.txt_user_dep_date);
        dateUserVal=txtUserDate.getText().toString();
        edUserFrom= view.findViewById(R.id.ed_user_from);
        String edfrom=edUserFrom.getText().toString();
        edUserTo= view.findViewById(R.id.ed_user_to);
        String edto=edUserTo.getText().toString();
        btnSearchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateUserVal=txtUserDate.getText().toString();
                String edfrom=edUserFrom.getText().toString();
                String edto=edUserTo.getText().toString();

                if (edfrom.isEmpty())
                {
                    edUserFrom.setError("Source required.");
                }
                if (edto.isEmpty()) {
                    edUserTo.setError("Destination required.");
                }
                if (dateUserVal.isEmpty() || !dateUserVal.equals(formattedDate)) {
                    txtUserDate.setError("Date required.");
                }

                if (!edfrom.isEmpty() && !edto.isEmpty() && (!dateUserVal.isEmpty() || dateUserVal.equals(formattedDate)))
                {
                    Intent searchIntent = new Intent(getActivity(), BookTicketsActivity.class);
                    searchIntent.putExtra("FROM_WHERE", edfrom);
                    searchIntent.putExtra("TO_WHERE", edto);
                    searchIntent.putExtra("DEP_DATE", dateUserVal);
                    startActivity(searchIntent);
                }


            }
        });

        txtUserDate.setOnClickListener(new View.OnClickListener() {
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
        onGetValue();
        return view;
    }
    DatePickerDialog.OnDateSetListener dateset=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%02d", year, month + 1, day);
            txtUserDate.setText(formattedDate);
        }
    };

    private void onGetValue()
    {
        try {
            Bundle myBund = getArguments();
            Toast.makeText(getActivity(), "User loged in with user ID " + Objects.requireNonNull(myBund).getString("userID"), Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e)
        {
            Log.e("BUNDLE_DATA", e.getMessage());
        }
    }
}

