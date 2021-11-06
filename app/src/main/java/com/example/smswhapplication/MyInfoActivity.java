package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyInfoActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.icon_kkanbu:
                    //Intent intent1 = new Intent(MatchingStartActivity.this, MyKkanbuActivity.class);
                    //startActivity(intent1);
                    //overridePendingTransition(0, 0);
                    //finish();
                    return true;
                case R.id.icon_matching:
                    Intent intent2 = new Intent(MyInfoActivity.this, MatchingStartActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        @SuppressLint("ResourceType")
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }
}