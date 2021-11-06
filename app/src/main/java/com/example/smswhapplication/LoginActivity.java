package com.example.smswhapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; // firebase auth
    private DatabaseReference mDatabaseRef; //realtime db

    EditText login_email, login_pw;
    Button btn_login, btn_to_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_pw = findViewById(R.id.login_email);
        btn_login = findViewById(R.id.btn_login);
        btn_to_signup = findViewById(R.id.btn_to_signup);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = login_email.getText().toString();
                String strPw = login_pw.getText().toString();

                //이메일 db에 있는지 확인하는 코드 여기에

                Intent intent1 = new Intent(LoginActivity.this, MatchingStartActivity.class);
                intent1.putExtra("email",strEmail);
                startActivity(intent1);
                finish();
            }
        });

        btn_to_signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }


}