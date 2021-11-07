package com.example.smswhapplication;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class KkanbuActivity extends AppCompatActivity {

    private TextView kkanbu_name, kkanbu_major, kkanbu_stunum, kkanbu_birthday;
    private ImageButton chat_btn;
    private String usernum = "", chatname = "", username = ".", kkanbuUid = "";
    private ImageView iv_kkanbu;

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference().child("Userprofile");
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");
    private DatabaseReference databaseReference2 = firebaseDatabase.getReference("SMSWH");

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.icon_matching:
                    Intent intent1 = new Intent(KkanbuActivity.this, YesKkanbuActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.icon_me:
                    Intent intent2 = new Intent(KkanbuActivity.this, MyInfoActivity.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    return true;
                default:
                    return true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kkanbu);

        @SuppressLint("ResourceType")
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        kkanbu_name = (TextView) findViewById(R.id.kkanbu_name);
        kkanbu_major = (TextView) findViewById(R.id.kkanbu_major);
        kkanbu_birthday = (TextView) findViewById(R.id.kkanbu_birthday);
        kkanbu_stunum = (TextView) findViewById(R.id.kkanbu_stunum);
        chat_btn = (ImageButton) findViewById(R.id.chat_btn);
        iv_kkanbu = findViewById(R.id.iv_kkanbu);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount current = dataSnapshot.getValue(UserAccount.class);

                kkanbuUid = current.getKkanbu();
                username = current.getName();
                usernum = current.getStuNum();
                kkanbu_stunum.setText(kkanbuUid);

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

                        Log.d("깐부 아이디", kkanbuUid);
                        StorageReference profileRef = storageReference.child(kkanbuUid);
                        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(getApplicationContext()).load(uri).into(iv_kkanbu);
                                HashMap<String,Object> userMap = new HashMap<>();
                                userMap.put("profileURL",uri.toString());
                                databaseReference.child("UserAccount/"+ kkanbuUid).updateChildren(userMap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Log.d("실패했음", e.getMessage());
                            }
                        });

                        if (Integer.parseInt(usernum) > Integer.parseInt(kkanbu_stunum.getText().toString()))
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

    }

}