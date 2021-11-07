package com.example.smswhapplication;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KkanbuActivity extends AppCompatActivity {

    private TextView kkanbu_name, kkanbu_major, kkanbu_stunum, kkanbu_birthday;
    private ImageButton chat_btn;
    private String usernum="", chatname="", username=".", kkanbuUid="";

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference().child("UserProfile");
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");
    private DatabaseReference databaseReference2 = firebaseDatabase.getReference("SMSWH");
    String a = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkanbu);
        if (a == "1") {
            Log.i("111111111", a);
        }

        kkanbu_name = (TextView) findViewById(R.id.kkanbu_name);
        kkanbu_major = (TextView) findViewById(R.id.kkanbu_major);
        kkanbu_birthday = (TextView) findViewById(R.id.kkanbu_birthday);
        kkanbu_stunum = (TextView) findViewById(R.id.kkanbu_stunum);
        chat_btn = (ImageButton) findViewById(R.id.chat_btn);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (a == "1") {
            Log.i("2222222222", firebaseUser.getUid());
        }
        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount current = dataSnapshot.getValue(UserAccount.class);

                kkanbuUid = current.getKkanbu();
                username = current.getName();
                usernum = current.getStuNum();
                kkanbu_stunum.setText(kkanbuUid);
                if (a == "1") {
                    Log.i("333333", kkanbuUid);
                    Log.i("44444", username);
                    Log.i("555555", usernum);
                }
                databaseReference2.child("UserAccount").child(kkanbuUid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("0000000", kkanbuUid);
                        Log.i("0000000", usernum);
                        UserAccount user = dataSnapshot.getValue(UserAccount.class);

                        String b = user.getBirthday();
                        String n = user.getMajor();
                        String s = user.getStuNum();
                        kkanbu_birthday.setText(b);
                        kkanbu_name.setText(user.getName());
                        kkanbu_major.setText(n);
                        kkanbu_stunum.setText(s);
                        Log.i("0000000", s);

                        if(Integer.parseInt(usernum) > Integer.parseInt(kkanbu_stunum.getText().toString()))
                            chatname = usernum;
                        else
                            chatname = kkanbu_stunum.getText().toString();

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

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });
//
//        if (a == "1") {
//            Log.i("6666666", a);
//            Log.i("7777777", username);
//            Log.i("dhrlsgka", usernum);
//        }





    }


}