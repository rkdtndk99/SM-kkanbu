package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
    private String kkanbuUid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.getValue(String.class);
//                new Handler().postDelayed(new Runnable() {
//                    String a;
//                    @Override
//                    public void run() {
//                        System.out.println("ㅁㅁㅁㅁ?"+ kkanbuUid);
//                        a=kkanbuUid;
//                        if(a!=""){
//                            System.out.println("왜... 여기..?");
//                            System.out.println("ㅁㅁㅁㅁ???"+ kkanbuUid);
//                            Intent intent = new Intent(MainActivity.this, YesKkanbuActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                        else{
//                            Intent intent = new Intent(MainActivity.this, MatchingStartActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                }, 1500);
                if(a!=""){
                    System.out.println("왜... 여기..?");
                    System.out.println("ㅁㅁㅁㅁ???"+ kkanbuUid);
                    Intent intent1 = new Intent(MainActivity.this, YesKkanbuActivity.class);
                    startActivity(intent1);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, MatchingStartActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}