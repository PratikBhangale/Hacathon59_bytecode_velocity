package com.example.hacathonbeta.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hacathonbeta.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.sql.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, MessageAdapter.MessageHolder> {

    public MessageAdapter(@NonNull FirestoreRecyclerOptions< Message > options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageAdapter.MessageHolder holder, int position, @NonNull Message model) {
        holder.textViewname.setText(model.getName());
        holder.textViewmessage.setText(model.getText());
        holder.textViewname.setText(model.getDate().toString());
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message_item,parent,false);
        return new MessageHolder(v);

    }

    static class MessageHolder extends RecyclerView.ViewHolder{
        TextView textViewmessage,timestamp,textViewname;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessage = itemView.findViewById(R.id.text_message);
            timestamp = itemView.findViewById(R.id.time_stamp);
            textViewname = itemView.findViewById(R.id.username);
        }
    }
}
