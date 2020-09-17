package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class customerpage extends AppCompatActivity {
    private Button btn;
    private Button signupBtn;
    private EditText username,password;
    private TextView forgetpass;

    FirebaseDatabase database;
    DatabaseReference users;
    FirebaseAuth fAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_activity);
        btn =(Button) findViewById(R.id.C_signin);
        signupBtn = (Button) findViewById(R.id.C_SignupBtn);
        username = (EditText)findViewById(R.id.c_username);
        password = (EditText)findViewById(R.id.C_Password);
        forgetpass=(TextView) findViewById(R.id.ForgotPass);
        fAuth=FirebaseAuth.getInstance();

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(customerpage.this,forget_password.class);
                startActivity(intent);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCustomerSignUp();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // signIn(username.getText().toString(),password.getText().toString());
                final String Username = username.getText().toString().trim();
                final String Password = password.getText().toString().trim();
                if(TextUtils.isEmpty(Username)){
                    username.setError("Name Required");
                    username.requestFocus();
                    return;
                }
                if(Password.isEmpty()) {
                    password.setError("Password Required");
                    password.requestFocus();
                    return;
                }

                if(Password.length()<6) {
                    password.setError("Password should be at least 6 characters long");
                    password.requestFocus();
                    return;
                }
                fAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                           OpenCustomerHome();
                       }




                });


            }
        });
    }





    public void OpenCustomerHome() {
        Intent intent = new Intent(getApplicationContext(),Customer_home.class);
        startActivity(intent);
    }
    public void OpenCustomerSignUp() {
        Intent intent = new Intent(getApplicationContext(),C_Signup.class);
        startActivity(intent);
    }
}
