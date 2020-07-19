package com.example.autoworkshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button3);
        button2 = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenAdmin();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCustomer();
            }
        });
    }
    public void OpenAdmin() {
        Intent intent = new Intent(getApplicationContext(),AdminPage.class);
        startActivity(intent);
    }
    public void OpenCustomer() {
        Intent intent = new Intent(getApplicationContext(), customerpage.class);
        startActivity(intent);
    }
}