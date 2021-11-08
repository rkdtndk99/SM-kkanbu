package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

public class MatchInterestActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference2;
    private Vector<String> interest_v = new Vector<String>();

    Button btn_school, btn_career, btn_hobby, btn_food, btn_to_kkanbu, btn_random, btn_talk;
    Dialog nokkanbu_dialog, yeskkanbu_dialog;
    ImageView iv_closeDialog;

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.icon_kkanbu:
                    Intent intent1 = new Intent(MatchInterestActivity.this, KkanbuActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                case R.id.icon_me:
                    Intent intent2 = new Intent(MatchInterestActivity.this, MyInfoActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    finish();
                    return true;
                default:
                    return true;
            }
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
        databaseReference2 = FirebaseDatabase.getInstance().getReference("SMSWH");
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        btn_school = findViewById(R.id.btn_school);
        btn_career = findViewById(R.id.btn_career);
        btn_hobby = findViewById(R.id.btn_hobby);
        btn_food = findViewById(R.id.btn_food);
        btn_talk = findViewById(R.id.btn_talk);
        btn_random = findViewById(R.id.btn_random);

        nokkanbu_dialog = new Dialog(this);
        nokkanbu_dialog.setContentView(R.layout.dialog_match_fail);
        nokkanbu_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        yeskkanbu_dialog = new Dialog(this);
        yeskkanbu_dialog.setContentView(R.layout.dialog_match_success);
        yeskkanbu_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(1);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
        btn_career.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(2);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
        btn_hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(3);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(4);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
        btn_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(5);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
        btn_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("interest").setValue(6);
                databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()){
                            String kkanbu = String.valueOf(task.getResult().getValue());
                            getKkanbu(firebaseUser);
                            if(kkanbu.equals("")) no_showDialog();
                            else yes_showDialog();
                        } else{
                            Log.d("실패했음", " ");
                        }
                    }
                });
            }
        });
    }

    public void no_showDialog(){
        nokkanbu_dialog.show();
        iv_closeDialog = nokkanbu_dialog.findViewById(R.id.iv_closeDialog);
        btn_to_kkanbu = nokkanbu_dialog.findViewById(R.id.btn_to_kkanbu);

        iv_closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nokkanbu_dialog.dismiss();
            }
        });
        btn_to_kkanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nokkanbu_dialog.dismiss();
                no_changeActivity();
            }
        });
    }

    public void yes_showDialog(){
        yeskkanbu_dialog.show();
        iv_closeDialog = yeskkanbu_dialog.findViewById(R.id.iv_closeDialog);
        btn_to_kkanbu = yeskkanbu_dialog.findViewById(R.id.btn_to_kkanbu);

        iv_closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeskkanbu_dialog.dismiss();
            }
        });
        btn_to_kkanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeskkanbu_dialog.dismiss();
                yes_changeActivity();
            }
        });
    }

    public void no_changeActivity(){
        Intent intent_1 = new Intent(MatchInterestActivity.this, MatchingStartActivity.class);
        startActivity(intent_1);
        overridePendingTransition(0, 0);
        finish();
    }

    public void yes_changeActivity(){
        Intent intent_2 = new Intent(MatchInterestActivity.this, KkanbuActivity.class);
        startActivity(intent_2);
        overridePendingTransition(0, 0);
        finish();
    }

    public void getKkanbu(FirebaseUser firebaseUser){

        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount user = dataSnapshot.getValue(UserAccount.class);
                Integer interest = user.getInterest();

                databaseReference.child("UserAccount").orderByChild("interest").equalTo(interest).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        UserAccount k = dataSnapshot.getValue(UserAccount.class);
                        String k_k = k.getKkanbu();
                        if(!dataSnapshot.getKey().equals(user.getIdToken()) && k_k==""){      //본인 아이디 제외
                            Log.d("깐부 키", dataSnapshot.getKey());
                            matchInterest(dataSnapshot.getKey());
                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("파이어베이스", "Error getting data");
            }
        };
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(valueEventListener);
    }

    public void matchInterest(String s){
        interest_v.add(s);
        if(!interest_v.isEmpty()){
            firebaseAuth = FirebaseAuth.getInstance();
            databaseReference = FirebaseDatabase.getInstance().getReference("SMSWH");
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            String kkanbuToken = interest_v.get(0);
            Log.d("깐부 저장 전 키", kkanbuToken);
            databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").setValue(kkanbuToken);
            databaseReference.child("UserAccount").child(kkanbuToken).child("kkanbu").setValue(firebaseUser.getUid());
            Log.d("깐부 저장 후 키", kkanbuToken);
        } else{
            no_showDialog();
        }
    }
}