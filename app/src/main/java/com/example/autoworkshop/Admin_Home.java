package com.example.autoworkshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Admin_Home extends AppCompatActivity {
    ListView listView;
     RecyclerView Recyclerview;
    DatabaseReference mDatabaseRef;
    private List<ImageUpload> uploads;
    FirebaseAuth mAuth;
    FirebaseFirestore fStore;

  String userId;
  private CollectionReference dataRef ;
  TextView name,phone;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
   // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
       // listView = (ListView)findViewById(R.id.listview);

        Recyclerview = (RecyclerView) findViewById(R.id.recyclerview);



      mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




        // DocumentReference documentReference = fStore.collection("users").document(userId);


      // imageAdapter = new mAdapter(this,uploads);
      //  mDatabaseRef = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database");


        Query query = fStore.collection("users");
        //Query query1 = fStore.collection("users").document("user1d").collection("Registeration Cards").startAt("Images");
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query,Note.class)
                .build();
       firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Note, NoteViewHolder>(options) {
            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_items,parent,false);
                return new NoteViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull final Note model) {
                holder.C_name.setText("Name: " + model.getName());
                holder.C_number.setText("Number: " + model.getPhone());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        Intent intent = new Intent(Admin_Home.this,CarUpdateInfo.class);
                        intent.putExtra("Name",model.getName());
                        intent.putExtra("ID",model.getID());


                        startActivity(intent);
                    }
                });

            }
        };
       Recyclerview.setHasFixedSize(true);
       Recyclerview.setLayoutManager(new LinearLayoutManager(this));
       Recyclerview.setAdapter(firestoreRecyclerAdapter);

    }

    private class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView C_name,C_number;
        String C_ID;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            C_name = itemView.findViewById(R.id.Customername);
            C_number = itemView.findViewById(R.id.Customernunber);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firestoreRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firestoreRecyclerAdapter.stopListening();
    }
}
