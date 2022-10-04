package com.example.a303bus;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
    String userName, userEmail, userPhone;
    Fragment fragment = new homeFragment();
    Bundle bundle = new Bundle();
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = getIntent();
        userName = intent.getStringExtra("USER_NAME");
        userEmail = intent.getStringExtra("USER_EMAIL");
        userPhone = intent.getStringExtra("USER_PHONE");

        getSupportActionBar().hide();
        bundle.putString("userID", intent.getStringExtra("USER_ID"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_user,fragment).commit();
        BottomNavigationView bnb=findViewById(R.id.bottom_nav_bar);
        bnb.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        fragment=new homeFragment();
                        bundle.putString("userID", intent.getStringExtra("USER_ID"));
                        fragment.setArguments(bundle);
                        break;
                    case R.id.prfile:
                        fragment=new profileFragment();
                        bundle.putString("user_name", userName);
                        bundle.putString("user_email", userEmail);
                        bundle.putString("user_phone", userPhone);
                        fragment.setArguments(bundle);
                      /*  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_for_user, fragment);
                        ft.addToBackStack(null);
                        ft.commit();*/
                        break;
                    case R.id.about:
                        fragment=new aboutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_for_user,fragment).commit();
                return true;
            }
        });

    }
}