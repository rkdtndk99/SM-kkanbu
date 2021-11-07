package com.example.smswhapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NoKkanbuActivity extends AppCompatActivity {

    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_kkanbu);

        btn.findViewById(R.id.btn_go);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoKkanbuActivity.this, MatchingStartActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }
}