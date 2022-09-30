package com.example.a303bus;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class home extends AppCompatActivity {
    Fragment fragment=new homeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
        BottomNavigationView bnb=findViewById(R.id.bottom_nav_bar);
        bnb.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        fragment=new homeFragment();
                        break;
                    case R.id.prfile:
                        fragment=new profileFragment();
                        break;
                    case R.id.about:
                        fragment=new aboutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                return true;
            }
        });

    }
}