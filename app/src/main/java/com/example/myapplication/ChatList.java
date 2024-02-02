package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ChatList extends Activity implements ChatListAdapter.OnItemClickListener {
    ChatListAdapter chatAdapter;
    RecyclerView rv;
    Button btnNewChat;
    String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        currentUsername = getIntent().getStringExtra("currentUsername");
        rv = findViewById(R.id.rv_chat_list);
        btnNewChat = findViewById(R.id.newChat);
        Query query = FirebaseDatabase.getInstance().getReference().child("Messages").limitToLast(50);
        FirebaseRecyclerOptions<ChatModel> options = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(query, ChatModel.class)
                .build();
        chatAdapter = new ChatListAdapter(options, this, currentUsername);
        rv.setLayoutManager(new LinearLayoutManager(ChatList.this));
        rv.setAdapter(chatAdapter);
        btnNewChat.setOnClickListener(v -> showNewChatDialog());
    }

    private void showNewChatDialog() {
        LayoutInflater inflater = LayoutInflater.from(ChatList.this);
        View dialogView = inflater.inflate(R.layout.new_chat_dailog, null);
        TextInputEditText emailEditText = dialogView.findViewById(R.id.etEmail);
        TextInputEditText messageEditText = dialogView.findViewById(R.id.etMessage);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatList.this);
        builder.setView(dialogView)
                .setTitle("New Chat")
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    String toUserEmail = emailEditText.getText().toString().trim();
                    String message = messageEditText.getText().toString().trim();
                    ChatModel chatModel = new ChatModel(currentUsername, toUserEmail, message);
                    FirebaseDatabase.getInstance().getReference().child("Messages").push().setValue(chatModel);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(ChatModel model) {
        Intent intent = new Intent(ChatList.this, activity_chat_details.class);
        intent.putExtra("currentUsername", currentUsername);
        intent.putExtra("toUsername", model.getTo_user());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        chatAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chatAdapter.stopListening();
    }
}
