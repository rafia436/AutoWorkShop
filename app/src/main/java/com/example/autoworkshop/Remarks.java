package com.example.autoworkshop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Remarks extends AppCompatActivity {
    EditText labor,tbv,vat;
    TextView total, finaltotal;
    Button calculate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remarks_main);
        total = (TextView) findViewById(R.id.totalparts);
        labor = (EditText) findViewById(R.id.labour);
        tbv = (EditText) findViewById(R.id.TBV);
        vat = (EditText) findViewById(R.id.Vat);
        finaltotal = (TextView) findViewById(R.id.TotalPrice);
        calculate = (Button) findViewById(R.id.Calculate);
        Bundle bundle = getIntent().getExtras();
        total.setText(bundle.getString("Total"));





        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    int result = Integer.parseInt(tbv.getText().toString().trim())+Integer.parseInt(vat.getText().toString().trim());
                    finaltotal.setText(String.valueOf(result));
                    Toast.makeText(Remarks.this, result+" total", Toast.LENGTH_SHORT).show();


                }

            }
        });
    }
    private boolean validation() {

        if (tbv.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Field one empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (vat.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Field two empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
