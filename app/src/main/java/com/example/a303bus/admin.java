package com.example.a303bus;
import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin extends AppCompatActivity {

    Fragment fragment=new admin_home();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        int adminId = Integer.parseInt(intent.getExtras().getString("ADMIN_ID"));
        Bundle b = new Bundle();
        b.putString("ADMIN_ID", adminId+"");
        admin_home adminHome = new admin_home();
        adminHome.setArguments(b);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
        BottomNavigationView bnb=findViewById(R.id.bottom_nav_bar_admin);
        bnb.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.admin_home:
                        fragment=new admin_home();
                        break;
                    case R.id.search:
                        fragment=new admin_search();
                        break;
                    case R.id.dashboard:
                        fragment=new admin_dashboard();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
                return true;
            }
        });

    }

}
