package com.example.smswhapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private String CHAT_NAME;
    private String USER_NAME;

    private RecyclerView recyclerView;
    private EditText chat_edit;
    private Button chat_send;
    private MyAdapter myAdapter;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 위젯 ID 참조
        String a ="0";
        if(a=="0"){
            Log.i("aaaaaaaa",a);
        }

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");

        Log.i("chatname",CHAT_NAME);
        // 채팅 방 입장
        openChat(CHAT_NAME);
        Log.i("chatname",CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                Message chat = new Message(USER_NAME, chat_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                chat_edit.setText(""); //입력창 초기화

            }
        });
    }

    private void addMessage(DataSnapshot dataSnapshot, MyAdapter adapter) {
        Message message = dataSnapshot.getValue(Message.class);
        adapter.addItem(message);
    }

    private void removeMessage(DataSnapshot dataSnapshot, MyAdapter adapter) {
        Message message = dataSnapshot.getValue(Message.class);
        adapter.removeItem(message);
    }

    private void openChat(String chatName) {
        recyclerView = findViewById(R.id.chat_view);
        chat_edit = findViewById(R.id.chat_edit);
        chat_send = findViewById(R.id.chat_send);

        // 리스트 어댑터 생성 및 세팅
        myAdapter = new MyAdapter(getLayoutInflater());
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, myAdapter);
                recyclerView.smoothScrollToPosition(myAdapter.getItemCount() - 1);
                Log.e("LOG", "s:"+s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, myAdapter);
                recyclerView.smoothScrollToPosition(myAdapter.getItemCount() - 1);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}