package com.example.hacathonbeta.driver.driver_list;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hacathonbeta.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DriverListAdapter extends FirestoreRecyclerAdapter<Driver,DriverListAdapter.DriverHolder> {

    private OnItemClickListener listener;
    public DriverListAdapter(@NonNull FirestoreRecyclerOptions< Driver > options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull DriverHolder holder, int position, @NonNull Driver model) {
        holder.drivername.setText(model.getName());
        holder.busdetails.setText(model.getBus());
        holder.timestamp.setText(String.valueOf(model.getDate()));
    }

    @NonNull
    @Override
    public DriverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new DriverHolder(view);
    }

    public class DriverHolder extends RecyclerView.ViewHolder {
        TextView drivername, busdetails, timestamp;

        public DriverHolder(@NonNull View itemView) {
            super(itemView);

            drivername = itemView.findViewById(R.id.driver_name);
            busdetails = itemView.findViewById(R.id.driver_busid);
            timestamp = itemView.findViewById(R.id.time_stamp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onClickItem(getSnapshots().getSnapshot(position), position);

                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClickItem(DocumentSnapshot documentSnapshot,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }

}
