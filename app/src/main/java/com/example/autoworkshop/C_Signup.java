package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;



import java.util.HashMap;
import java.util.Map;

public class C_Signup extends AppCompatActivity implements View.OnClickListener {
    private EditText Username, Email, Password, Phone;
    private FirebaseAuth mAuth;
    String userID;
    DatabaseReference databaseRef;
    FirebaseFirestore fstore;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_signup);
        Username = findViewById(R.id.C_Username);
        Email= findViewById(R.id.C_Email);
        Password=findViewById(R.id.C_Password);
        Phone=findViewById(R.id.Phone);
       databaseRef = FirebaseDatabase.getInstance().getReference("users");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        findViewById(R.id.RegisterBtn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.RegisterBtn:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        final String name = Username.getText().toString().trim();
        final String email= Email.getText().toString().trim();
        final String password= Password.getText().toString().trim();
        final String phone = Phone.getText().toString().trim();

        if(name.isEmpty()) {
            Username.setError("Name Required");
            Username.requestFocus();
            return;
        }
        if(email.isEmpty()) {
            Email.setError("Email Required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Enter a valid email");
            Email.requestFocus();
            return;

        }

        if(password.isEmpty()) {
            Password.setError("Password Required");
            Password.requestFocus();
            return;
        }

        if(password.length()<6) {
            Password.setError("Password should be at least 6 characters long");
            Password.requestFocus();
            return;
        }
        if(phone.isEmpty()) {
            Phone.setError("Phone Required");
            Phone.requestFocus();
            return;
        }
        if(phone.length() != 10) {
            Phone.setError("Enter a valid phone number");
            Phone.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                  User user = new User(
                          name,
                          email,
                          phone,
                          password
                  );
                    userID = mAuth.getCurrentUser().getUid();


                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String,Object> UserID = new HashMap<>();
                    UserID.put("Name",name);
                    UserID.put("Phone", phone);
                    UserID.put("ID", userID);



                    databaseRef.push().setValue(UserID);
                    firebaseUser = task.getResult().getUser();
                    documentReference.set(UserID).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(C_Signup.this,"Success",Toast.LENGTH_LONG).show();
                        }
                    });
                  OpenHomePage();

                }
                else {

                    Toast.makeText(C_Signup.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    public void OpenHomePage() {
        Intent intent = new Intent (C_Signup.this, RequestPage.class);
        startActivity(intent);
    }

}
