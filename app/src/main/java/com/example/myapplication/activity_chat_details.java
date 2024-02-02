package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class activity_chat_details extends AppCompatActivity {

    private String currentUsername;
    private String toUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);
        toUsername = getIntent().getStringExtra("toUsername");
        currentUsername = getIntent().getStringExtra("currentUsername");
        RecyclerView recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        TextInputEditText editTextMessage = findViewById(R.id.editTextMessage);
        Button btnSend = findViewById(R.id.btnSend);
        Query query = FirebaseDatabase.getInstance().getReference().child("messages")
                .child(currentUsername).limitToLast(50);

        FirebaseRecyclerOptions<ChatModel> options = new FirebaseRecyclerOptions.Builder<ChatModel>()
                .setQuery(query, ChatModel.class)
                .build();

        MessageListAdapter messageAdapter = new MessageListAdapter(options);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);
        btnSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                ChatModel chatModel = new ChatModel(currentUsername, toUsername, messageText);
                FirebaseDatabase.getInstance().getReference().child("Messages").push().setValue(chatModel);
            }
        });
    }

}
