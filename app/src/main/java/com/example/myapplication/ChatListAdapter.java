package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ChatModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
public class ChatListAdapter extends FirebaseRecyclerAdapter<ChatModel, ChatListAdapter.ViewHolder> {
    private Context loginActivity;
    private OnItemClickListener onItemClickListener;

    private String currentUsername;


    public interface OnItemClickListener {
        void onItemClick(ChatModel model);
    }

    public ChatListAdapter(@NonNull FirebaseRecyclerOptions<ChatModel> options, Context mainActivity, String currentUsername) {
        super(options);
        this.loginActivity = mainActivity;
        this.currentUsername = currentUsername;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_design, parent, false);
        return new ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ChatModel model) {
        if (currentUsername.equals(model.getFrom_user()) || currentUsername.equals(model.getTo_user())) {
            holder.name.setText(model.getFrom_user());
            holder.itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            });
        } else {
            // If the current user is not involved in the chat, hide the item (optional)
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
        }

        public void bind(ChatModel model) {
            name.setText(model.getFrom_user());
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(model);
                }
            });
        }
    }

}