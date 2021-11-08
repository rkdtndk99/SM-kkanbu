package com.example.smswhapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("SMSWH");
    public String name;
    private LayoutInflater inflater;
    ArrayList<Message> list = new ArrayList<>();
    public MyAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = itemView.findViewById(R.id.sentTxt);
        }
    }
    public class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView nameTxt, messageTxt;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.nameTxt);
            messageTxt = itemView.findViewById(R.id.receivedTxt);
        }
    }
    @Override
    public int getItemViewType(int position) {
        Message message = list.get(position);
        if ((message.getUserName()).equals(name)) {
            return TYPE_MESSAGE_SENT;
        }
        else {
            return TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.sent_maessage, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:

                view = inflater.inflate(R.layout.is_sent, parent, false);
                return new ReceivedMessageHolder(view);
        }

        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Message m = list.get(position);
        getName();
        if ((m.getUserName()).equals(name)) {
            SentMessageHolder messageHolder = (SentMessageHolder) holder;
            messageHolder.messageTxt.setText(m.getMessage());

        }
        else {
            ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
            messageHolder.nameTxt.setText(m.getUserName());
            messageHolder.messageTxt.setText(m.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem (Message m) {
        list.add(m);
        notifyDataSetChanged();
    }
    public void removeItem (Message m) {
        list.remove(m);
        notifyDataSetChanged();
    }
    public void getName() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.getValue(String.class);
                name=a;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child("UserAccount").child(firebaseUser.getUid()).child("name").addListenerForSingleValueEvent(valueEventListener);
    }

}