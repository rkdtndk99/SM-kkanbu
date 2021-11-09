package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                page(String.valueOf(task.getResult().getValue()));
            }

        });
    }
    public void page (String a){
        System.out.println("아니 a 가 ㅁ???"+ a);
        if(a.equals("")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MatchingStartActivity.class);
                    startActivity(intent);
                    finish();
            }}, 1500);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.out.println("왜... 여기..?");
                    System.out.println("ㅁㅁㅁㅁ???"+ a);
                    Intent intent = new Intent(MainActivity.this, YesKkanbuActivity.class);
                    startActivity(intent);
                    finish();
                }}, 1500);
        }
    }
}