package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class NoKkanbuActivity extends AppCompatActivity {

    private ImageButton btn;

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.icon_me :
                    Intent intent2 = new Intent(NoKkanbuActivity.this, MyInfoActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;

                case R.id.icon_matching:
                    Intent intent1 = new Intent(NoKkanbuActivity.this, MatchingStartActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    finish();

                    return true;

                default :
                    return false;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_kkanbu);

        btn.findViewById(R.id.btn_go);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoKkanbuActivity.this, MatchingStartActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        @SuppressLint("ResourceType")
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }
}