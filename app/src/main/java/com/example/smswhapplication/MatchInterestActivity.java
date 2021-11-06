package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchInterestActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    Button btn_school, btn_career, btn_hobby, btn_food, btn_to_kkanbu;
    Dialog dialog;
    ImageView iv_closeDialog;

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
                case R.id.icon_me:
                    Intent intent2 = new Intent(MatchInterestActivity.this, MyInfoActivity.class);
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
        setContentView(R.layout.activity_match_interest);

        @SuppressLint("ResourceType")
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("SMSWH");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        btn_school = findViewById(R.id.btn_school);
        btn_career = findViewById(R.id.btn_career);
        btn_hobby = findViewById(R.id.btn_hobby);
        btn_food = findViewById(R.id.btn_food);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_match_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(1);
                showDialog();
            }
        });
        btn_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(2);
                //alert dialog
            }
        });
        btn_hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(3);
                //alert dialog
            }
        });
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(4);
                //alert dialog
            }
        });
    }

    public void showDialog(){
        dialog.show();
        iv_closeDialog = dialog.findViewById(R.id.iv_closeDialog);
        btn_to_kkanbu = dialog.findViewById(R.id.btn_to_kkanbu);

        iv_closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_to_kkanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(MatchInterestActivity.this, MyInfoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}