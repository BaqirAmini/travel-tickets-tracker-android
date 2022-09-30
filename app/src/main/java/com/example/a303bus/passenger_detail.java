package com.example.a303bus;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class passenger_detail extends AppCompatActivity {

    EditText edname,edlastname,edphone,edcardno,edcvv,edmmyy,edidcardno;
    RadioButton visa,mastercard,cash;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_detail);
        getSupportActionBar().hide();
        edname.findViewById(R.id.edname2);
        edlastname.findViewById(R.id.edlastname);
        edphone.findViewById(R.id.edphone2);
        edidcardno.findViewById(R.id.edidcard);
        edcardno.findViewById(R.id.edcardno);
        edcvv.findViewById(R.id.edcvv);
        edmmyy.findViewById(R.id.edmmyy);

        String name=edname.getText().toString();
        String lastname=edlastname.getText().toString();
        String phone=edphone.getText().toString();
        String idcard=edidcardno.getText().toString();





        mastercard.findViewById(R.id.mastercard);
        visa.findViewById(R.id.visa);
        cash.findViewById(R.id.cash);
        rg.findViewById(R.id.group);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (visa.isChecked()){
                    Toast.makeText(passenger_detail.this, "visa card has been selected", Toast.LENGTH_SHORT).show();

                }else if(mastercard.isChecked()){
                    Toast.makeText(passenger_detail.this, "mastercard card has been selected", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(passenger_detail.this, "cash has been selected", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
}