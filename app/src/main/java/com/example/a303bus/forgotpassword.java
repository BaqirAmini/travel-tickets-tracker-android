package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class forgotpassword extends AppCompatActivity {
    TextView back;
    EditText edname,edemail,edpassword;
    Button btncreate;
    String name,password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getSupportActionBar().hide();
        back=findViewById(R.id.txtsignup);
        edname=findViewById(R.id.edname);
        name=edname.getText().toString();

        edemail=findViewById(R.id.edemail);
        email=edemail.getText().toString();

        edpassword=findViewById(R.id.edpass);
        password=edpassword.getText().toString();

        btncreate=findViewById(R.id.btncreate);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(forgotpassword.this,MainActivity.class);
                startActivity(i);
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}