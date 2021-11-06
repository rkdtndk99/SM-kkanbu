package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KkanbuActivity extends AppCompatActivity {

    private TextView kkanbu_name, kkanbu_major, kkanbu_stunum, kkanbu_birthday;
    private ImageButton chat_btn;
    private String usernum, chatname, username;
    private String KKANBU_UID;

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference().child("UserProfile");
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkanbu);

        Intent intent = getIntent();
        KKANBU_UID = intent.getStringExtra("kkanbu_uid");

        kkanbu_name = (TextView) findViewById(R.id.kkanbu_name);
        kkanbu_major = (TextView) findViewById(R.id.kkanbu_major);
        kkanbu_birthday = (TextView) findViewById(R.id.kkanbu_birthday);
        kkanbu_stunum = (TextView) findViewById(R.id.kkanbu_stunum);
        chat_btn = (ImageButton) findViewById(R.id.chat_btn);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("stunum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    usernum = String.valueOf(task.getResult().getValue());
                }
            }
        });
        databaseReference.child("UserAccount").child(KKANBU_UID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    kkanbu_name.setText(String.valueOf(task.getResult().child("name").getValue()));
                    kkanbu_major.setText(String.valueOf(task.getResult().child("major").getValue()));
                    kkanbu_stunum.setText(String.valueOf(task.getResult().child("stuNum").getValue()));
                    kkanbu_birthday.setText(String.valueOf(task.getResult().child("birthday").getValue()));
                }
            }
        });



        int user = Integer.parseInt(usernum);
        int kkan = Integer.parseInt(kkanbu_stunum.toString());

        if(user > kkan)
            chatname = username + kkanbu_stunum.toString();
        else
            chatname = kkanbu_stunum.toString() + username;



        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KkanbuActivity.this, ChatActivity.class);
                intent.putExtra("chatName", chatname);
                intent.putExtra("userName", username);
                startActivity(intent);
            }
        });


    }


}