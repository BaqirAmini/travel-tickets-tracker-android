package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edemail,edpassword;
    Button btnlogin;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        edemail=findViewById(R.id.edemail);
        edpassword=findViewById(R.id.edpass);

        TextView signup=findViewById(R.id.signup);
        TextView txtforgot=findViewById(R.id.forgotpss);


        btnlogin=findViewById(R.id.btnlogin);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,singup.class);
                startActivity(i);
            }
        });

        txtforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,forgotpassword.class);
                startActivity(i);
                finish();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();
            }
        });

    }

    public void login() {

    }
}
