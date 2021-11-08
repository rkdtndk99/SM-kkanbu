package com.example.smswhapplication;

import static java.lang.String.valueOf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MyInfoActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");
    private DatabaseReference databaseReference2 = firebaseDatabase.getReference("SMSWH");
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String kkanbuUid="";
    TextView tv_myname, tv_email, tv_mybday, tv_mymajor, tv_mynum, kkanbu;
    ImageView iv_myprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        tv_myname = findViewById(R.id.tv_myname);
        tv_email = findViewById(R.id.tv_email);
        tv_mybday = findViewById(R.id.tv_mybday);
        tv_mymajor = findViewById(R.id.tv_mymajor);
        tv_mynum = findViewById(R.id.tv_mynum);
        iv_myprofile = findViewById(R.id.iv_myprofile);
        kkanbu = findViewById(R.id.kk);

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount user = dataSnapshot.getValue(UserAccount.class);
                tv_myname.setText(user.getName());
                tv_email.setText(user.getEmail());
                tv_mybday.setText(user.getBirthday());
                tv_mymajor.setText(user.getMajor());
                tv_mynum.setText(user.getStuNum());
                kkanbuUid = user.getKkanbu();

                String uid = firebaseUser.getUid();;
                StorageReference profileRef = storageReference.child(uid);
                profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri).into(iv_myprofile);
                        HashMap<String,Object> userMap = new HashMap<>();
                        userMap.put("profileURL",uri.toString());
                        databaseReference.child("UserAccount/"+uid).updateChildren(userMap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d("실패했음", e.getMessage());
                    }
                });
            }
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("파이어베이스", "Error getting data");
            }
        };
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).addValueEventListener(valueEventListener);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Userprofile");


        final Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃
                mFirebaseAuth.signOut();
                Toast.makeText(MyInfoActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MyInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.icon_kkanbu:
                        if(kkanbuUid!="") {
                            Intent intent1_1 = new Intent(MyInfoActivity.this, KkanbuActivity.class);
                            startActivity(intent1_1);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        else{
                            Intent intent1_2 = new Intent(MyInfoActivity.this, NoKkanbuActivity.class);
                            startActivity(intent1_2);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        return true;

                    case R.id.icon_matching:
                        if(kkanbuUid!=""){
                            Intent intent1 = new Intent(MyInfoActivity.this, YesKkanbuActivity.class);
                            startActivity(intent1);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        else{
                            Intent intent1 = new Intent(MyInfoActivity.this, MatchingStartActivity.class);
                            startActivity(intent1);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                        return true;

                    default :
                        return false;

                }
            }
        };

        @SuppressLint("ResourceType")
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }

}