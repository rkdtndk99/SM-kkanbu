package com.example.smswhapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    EditText signup_name, signup_birth, signup_major, signup_stunum, signup_email, signup_pw;
    ImageButton btn_signup;
    ImageView signup_img;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("SMSWH");

        signup_name = findViewById(R.id.signup_name);
        signup_birth = findViewById(R.id.signup_birth);
        signup_major = findViewById(R.id.signup_major);
        signup_stunum = findViewById(R.id.signup_stunum);
        signup_email = findViewById(R.id.signup_email);
        signup_pw = findViewById(R.id.signup_pw);
        btn_signup = findViewById(R.id.btn_signup);
        signup_img = findViewById(R.id.signup_img);

        signup_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!signup_name.equals("") && !signup_birth.equals("") && !signup_major.equals("") && !signup_stunum.equals("") && !signup_email.equals("") && !signup_pw.equals("")){

                    firebaseAuth.createUserWithEmailAndPassword(signup_email.getText().toString(), signup_pw.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("login" ,  signup_email.getText().toString());

                                    if(task.isSuccessful()) {
                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        StorageReference mStorageRef = storageReference.child("Userprofile/"+firebaseUser.getUid());

                                        if(imageUri!=null){
                                            mStorageRef.putFile(imageUri)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Toast.makeText(SignUpActivity.this, "이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }

                                        UserAccount user = new UserAccount();
                                        user.setIdToken(firebaseUser.getUid());
                                        user.setName(signup_name.getText().toString());
                                        user.setBirthday(signup_birth.getText().toString());
                                        user.setMajor(signup_major.getText().toString());
                                        user.setStuNum(signup_stunum.getText().toString());
                                        user.setEmail(firebaseUser.getEmail());
                                        user.setPw(signup_pw.getText().toString());
                                        user.setKkanbu("");
                                        //아직 저장되지 않은 값 초기화
                                        user.setAge(0);
                                        user.setInterest(0);

                                        Log.d("user",user.getName());
                                        Log.d("db", databaseReference.toString());

                                        databaseReference.child("UserAccount").child(firebaseUser.getUid()).setValue(user);

                                        //HashMap<String,Object> map = new HashMap<>();

                                        Toast.makeText(SignUpActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                                        Intent intent2 = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent2);
                                    } else {
                                        Log.d("error",task.getException().getMessage());
                                        Toast.makeText(SignUpActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    //사진 가져오기
    private void chooseImage(){
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent1, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data.getData()!=null){
            imageUri=data.getData();
            signup_img.setImageURI(imageUri);
        }
    }
}