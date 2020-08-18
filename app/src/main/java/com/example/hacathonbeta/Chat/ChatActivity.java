package com.example.hacathonbeta.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hacathonbeta.R;
import com.example.hacathonbeta.driver.EditDriverProfile;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private TextView get_name;
    private EditText editText;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore mRef= FirebaseFirestore.getInstance();
    private MessageAdapter messageAdapter;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private CollectionReference complaintref = mRef.collection("Chats");
    private Task<DocumentSnapshot>  noteRef ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mRecyclerView = (RecyclerView) findViewById(R.id.chat_room);
        editText = (EditText) findViewById(R.id.text_message);
        get_name = (TextView) findViewById(R.id.getname);
        imageButton = (ImageButton) findViewById(R.id.send_message);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        setupRecyclerview();

    }

    private void setupRecyclerview() {
        Query query = complaintref.orderBy("date",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>().setQuery(query,Message.class).build();
        messageAdapter = new MessageAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.chat_room);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

    }
    private void saveNote() {
        String message = editText.getText().toString();
        Task<DocumentSnapshot> mDatasnapshot = mRef.collection("Students").document(Objects.requireNonNull(mAuth.getUid())).get()
                .addOnSuccessListener(new OnSuccessListener< DocumentSnapshot >() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String sname = documentSnapshot.getString("name");
                        get_name.setText(sname);
                    }
                });

        String name1 = get_name.getText().toString();


        Map<String,Object> user = new HashMap<>();
        user.put("text",message);
        user.put("date",null);
        user.put("name",name1);

        if (message.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        mRef.collection("Messages").document(mAuth.getUid()).set(user).addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ChatActivity.this, "Message added", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        messageAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageAdapter.stopListening();
    }
}