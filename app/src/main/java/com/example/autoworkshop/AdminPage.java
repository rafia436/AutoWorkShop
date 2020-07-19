package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPage extends AppCompatActivity {
    Button signin, signup;
    EditText A_username, a_passwrd;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        signin = (Button) findViewById(R.id.a_signin);
        signup = (Button) findViewById(R.id.a_signup);
        A_username=(EditText) findViewById(R.id.a_username) ;
        a_passwrd = (EditText) findViewById(R.id.a_pass) ;
        fAuth = FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = A_username.getText().toString().trim();
                final String Password = a_passwrd.getText().toString().trim();
                if(TextUtils.isEmpty(userName)){
                    A_username.setError("Name Required");
                    A_username.requestFocus();
                    return;
                }
                if(Password.isEmpty()) {
                    a_passwrd.setError("Password Required");
                    a_passwrd.requestFocus();
                    return;
                }

                if(Password.length()<6) {
                    a_passwrd.setError("Password should be at least 6 characters long");
                    a_passwrd.requestFocus();
                    return;
                }
                fAuth.signInWithEmailAndPassword(userName,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Intent intent = new Intent(AdminPage.this, Admin_Home.class);
                        startActivity(intent);
                    }




                });


            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPage.this, A_Signup.class);
                startActivity(intent);
            }
        });

    }
}