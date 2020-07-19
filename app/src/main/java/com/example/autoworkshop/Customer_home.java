package com.example.autoworkshop;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class Customer_home extends AppCompatActivity {
    private Spinner dropdownMenu;
    RecyclerView customerlist;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String name;
    FirebaseUser userID;
    RequestClass requestClass;
    ArrayList<RequestFields> requestFields;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    Note note = new Note();
Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customeractivity_home);
        dropdownMenu = findViewById(R.id.dropdownmenu);
        List<String> menu = new ArrayList<>();
        menu.add("Home");
        menu.add("Notifications");
        menu.add("History");
        menu.add("Make a Request");
        menu.add("Payments");

        customerlist = (RecyclerView) findViewById(R.id.requestList);
        mContext = Customer_home.this;
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser();



        requestFields = new ArrayList<>();
        final DocumentReference documentReference1 = firestore.collection("users").document(userID.getUid());
        documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name = documentSnapshot.getString("Name");
                Toast.makeText(Customer_home.this,name +" logged in ",Toast.LENGTH_SHORT).show();

            }
        });


        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menu);
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(menuAdapter);



        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = dropdownMenu.getItemAtPosition(i).toString();
                if (item.equals("Make a Request")) {
                    Intent intent = new Intent(Customer_home.this, RequestPage.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CollectionReference collectionReference = firestore.collection("users").document(userID.getUid()).collection("Registeration Cards");
        final DocumentReference documentReference = collectionReference.document("Request");
        Query query = collectionReference;
        FirestoreRecyclerOptions<RequestFields> options = new FirestoreRecyclerOptions.Builder<RequestFields>()
                .setQuery(query, RequestFields.class)
                .build();
        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<RequestFields, RequestViewHolder>(options) {




            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_lists, parent, false);
                return new RequestViewHolder(view);
            }
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull final RequestFields model) {

                holder.carname.setText("Car Name:  " + model.getCarName());
                holder.carnumber.setText("Plate Number: " + model.getCarNumber());
                Boolean progress = model.getRequestStatus();
                if(progress==true){
                    holder.carstatus.setText("Status: In progress");
                    holder.itemView.setBackgroundColor(Color.parseColor("#9F9B9B"));

                }
                String CarImage = model.getCarImage();
                Picasso.with(mContext)
                        .load(CarImage)
                        .into(holder.img1);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            Intent intent = new Intent(Customer_home.this,ViewInformation.class);
                            intent.putExtra("InspectionForm" , model.getInspectionForm().toString());
                            intent.putExtra("CarImage", model.getCarImage());
                            intent.putExtra("CarName", model.getCarName());
                            intent.putExtra("Name", name);
                            startActivity(intent);



                    }
                });


            }


        };
        customerlist.setHasFixedSize(true);
        customerlist.setLayoutManager(new LinearLayoutManager(this));
        customerlist.setAdapter(firestoreRecyclerAdapter);
    }
         /* collectionReference.document("Images").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 String carname = documentSnapshot.get("Car Name") + "";
                 String carnumber = documentSnapshot.get("Car Number")+ "";
                 String imageuri = documentSnapshot.get("Car Image")+"";

                 RequestFields request  = new RequestFields(carname,carnumber,imageuri);
                 requestFields.add(request);
                 requestClass = new RequestClass(Customer_home.this,requestFields);
                 customerlist.setAdapter(requestClass);


             }
         });*/


    private class RequestViewHolder extends RecyclerView.ViewHolder {
        private TextView carname,carnumber,carstatus;
        private ImageView img1;

        public RequestViewHolder(View itemView) {
            super(itemView);
            carname = itemView.findViewById(R.id.c_name);
            carnumber = itemView.findViewById(R.id.c_number);
            img1 = itemView.findViewById(R.id.carimage);
            carstatus = itemView.findViewById(R.id.status);
        }
    }

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


