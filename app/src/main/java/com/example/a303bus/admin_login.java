package com.example.a303bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class admin_login extends AppCompatActivity {
    EditText email,pass;
    Button login;
    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        getSupportActionBar().hide();

        email=findViewById(R.id.adminedemail);
        pass=findViewById(R.id.adminedpass);
        login=findViewById(R.id.btnloginadmin);
        user=findViewById(R.id.user);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"admin login",Toast.LENGTH_LONG).show();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(admin_login.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}