package com.example.a303bus;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

public class admin_search extends Fragment {
    EditText from, to;
    Button search;
    TextView txtdate;

    String edfrom, edto, date, selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_search, container, false);
        from = (EditText) view.findViewById(R.id.edfrom2);
        to = (EditText) view.findViewById(R.id.edto2);


        txtdate = (TextView) view.findViewById(R.id.txtdate);


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
                onSearch();
            }
        });


        return view;
    }

    DatePickerDialog.OnDateSetListener dateset = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
//            selectedDate = year + "-" + (month + 1) + "-" + day;
//            txtdate.setText(selectedDate);
            selectedDate = String.format(Locale.getDefault(),"%02d-%02d-%02d", year, month+1, day);
            txtdate.setText(selectedDate);
        }

    };

    private void onSearch()
    {
        edfrom = from.getText().toString();
        edto = to.getText().toString();
        date = txtdate.getText().toString();
        if (edfrom.isEmpty())
        {
            from.setError("Please insert the source.");
        }
        if (edto.isEmpty()) {
            to.setError("Please insert the destination.");
        }
        if (date.isEmpty() || !date.equals(selectedDate)) {
            txtdate.setError("Please insert a date to search.");
        }

        if (!edfrom.isEmpty() && !edto.isEmpty() && (!date.isEmpty() || date.equals(selectedDate)))
        {
            Intent searchIntent = new Intent(getActivity(), TicketsListActivity.class);
            searchIntent.putExtra("FROM_WHERE", edfrom);
            searchIntent.putExtra("TO_WHERE", edto);
            searchIntent.putExtra("DEP_DATE", date);
            startActivity(searchIntent);

        }
    }
}

