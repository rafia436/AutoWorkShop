package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class A_Signup extends AppCompatActivity {
    private EditText A_Username, A_Email, A_Password, A_Phone;
    private FirebaseAuth fAuth;
    private ImageButton btn;
    String userID;
    DatabaseReference databaseRef;
    FirebaseFirestore Fstore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_signup);
        A_Username = findViewById(R.id.A_Username);
        A_Email = findViewById(R.id.A_Email);
        A_Password = findViewById(R.id.A_Password);
        A_Phone = findViewById(R.id.A_Phone);
        btn = (ImageButton) findViewById(R.id.A_registerbtn);

        fAuth = FirebaseAuth.getInstance();
        Fstore = FirebaseFirestore.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerAdmin();
            }
        });

    }

    private void registerAdmin() {
        final String A_name = A_Username.getText().toString().trim();
        final String A_email = A_Email.getText().toString().trim();
        final String A_pass = A_Password.getText().toString().trim();
        final String A_phone = A_Phone.getText().toString().trim();

        if (A_name.isEmpty()) {
            A_Username.setError("Name Required");
            A_Username.requestFocus();
            return;
        }
        if (A_email.isEmpty()) {
            A_Email.setError("Email Required");
            A_Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(A_email).matches()) {
            A_Email.setError("Enter a valid email");
            A_Email.requestFocus();
            return;

        }

        if (A_pass.isEmpty()) {
            A_Password.setError("Password Required");
            A_Password.requestFocus();
            return;
        }

        if (A_pass.length() < 6) {
            A_Password.setError("Password should be at least 6 characters long");
            A_Password.requestFocus();
            return;
        }
        if (A_phone.isEmpty()) {
            A_Phone.setError("Phone Required");
            A_Phone.requestFocus();
            return;
        }
        if (A_phone.length() != 10) {
            A_Phone.setError("Enter a valid phone number");
            A_Phone.requestFocus();
            return;
        }
        fAuth.createUserWithEmailAndPassword(A_email, A_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Admin admin = new Admin(
                            A_name,
                            A_email,
                            A_phone,
                            A_pass
                    );
                    userID = fAuth.getCurrentUser().getUid();


                    DocumentReference documentReference = Fstore.collection("admin").document(userID);
                    Map<String, Object> Admin = new HashMap<>();
                    Admin.put("Username", A_name);
                    Admin.put("Phone",A_phone);
                    documentReference.set(Admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(A_Signup.this,"Success",Toast.LENGTH_LONG).show();
                        }
                    });

                    openHomePage();
                }
            }


        });
    }

    private void openHomePage(){
        Intent intent = new Intent(A_Signup.this, Admin_Home.class);
        startActivity(intent);
    }
}

