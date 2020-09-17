package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewInformation extends AppCompatActivity {
    String Username, carname, image,inspectionform;
    TextView name;
    ImageView carimage;
    EditText InspectionReport;
    private Spinner dropdownMenu;
    boolean select=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinformation);
        name = (TextView) findViewById(R.id.Username);
        carimage = (ImageView) findViewById(R.id.carImage) ;
        InspectionReport = (EditText) findViewById(R.id.InspectionForm) ;
        dropdownMenu = findViewById(R.id.dropdown);

        List<String> menu = new ArrayList<>();
        menu.add("View Cars");
        menu.add("Home");
        menu.add("Notifications");
        menu.add("History");
        menu.add("Make a Request");
        menu.add("Payments");

        ArrayAdapter<String> menuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, menu);
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownMenu.setAdapter(menuAdapter);

        dropdownMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                select=true;
                return false;
            }
        });

        dropdownMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = dropdownMenu.getItemAtPosition(i).toString();

                if(select==true) {
                    if (item.equals("Home")) {
                        Intent intent = new Intent(ViewInformation.this, Customer_home.class);
                        startActivity(intent);
                        select=false;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                select=false;



            }
        });

        Bundle bundle = getIntent().getExtras();
        name.setText("Hello, " + bundle.getString("Name"));
        image = bundle.getString("CarImage");
        Picasso.with(this)
                .load(image)
                .into(carimage);
        inspectionform = bundle.getString("InspectionForm");

        InspectionReport.setText(inspectionform);



    }
}
