package com.example.autoworkshop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewInformation extends AppCompatActivity {
    String Username, carname, image,inspectionform;
    TextView name;
    ImageView carimage;
    EditText InspectionReport;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewinformation);
        name = (TextView) findViewById(R.id.Username);
        carimage = (ImageView) findViewById(R.id.carImage) ;
        InspectionReport = (EditText) findViewById(R.id.InspectionForm) ;

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
