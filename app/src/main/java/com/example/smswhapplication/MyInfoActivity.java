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
import android.widget.ImageView;
import android.widget.TextView;

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
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String kkanbuUid;
    TextView tv_myname, tv_email, tv_mybday, tv_mymajor, tv_mynum;
    ImageView iv_myprofile;

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

//            databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("kkanbu").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (!task.isSuccessful()) {
//                        Log.e("firebase", "Error getting data", task.getException());
//                        kkanbuUid = "";
//                    }
//                    else {
//                        kkanbuUid = String.valueOf(task.getResult().getValue());
//                    }
//                }
//            });
            switch(item.getItemId()){
//                case R.id.icon_kkanbu:
//                    if(!kkanbuUid.equals("")) {
//                        Intent intent1_1 = new Intent(MyInfoActivity.this, KkanbuActivity.class);
//                        intent1_1.putExtra("kkanbu-uid", kkanbuUid);
//                        startActivity(intent1_1);
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    }
//                    else{
//                        Intent intent1_2 = new Intent(MyInfoActivity.this, NoKkanbuActivity.class);
//                        startActivity(intent1_2);
//                        overridePendingTransition(0, 0);
//                        finish();
//                        return true;
//                    }
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

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("Userprofile");

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        tv_myname = findViewById(R.id.tv_myname);
        tv_email = findViewById(R.id.tv_email);
        tv_mybday = findViewById(R.id.tv_mybday);
        tv_mymajor = findViewById(R.id.tv_mymajor);
        tv_mynum = findViewById(R.id.tv_mynum);
        iv_myprofile = findViewById(R.id.iv_myprofile);

        ValueEventListener valueEventListener = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserAccount user = dataSnapshot.getValue(UserAccount.class);
                tv_myname.setText(user.getName());
                tv_email.setText(user.getEmail());
                tv_mybday.setText(user.getBirthday());
                tv_mymajor.setText(user.getMajor());
                tv_mynum.setText(user.getStuNum());

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
    }
}