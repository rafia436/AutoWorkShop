package com.example.autoworkshop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EditErrors extends AppCompatActivity {
    ArrayList<String> getChecked;
    List<String> checkBoxes = new ArrayList<String>();
    ArrayList<Cars> data;
    ListView listView;
    EditClass editClass;
    ImageButton sendremarks;


    ArrayList<Cars> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editerrors_list);
        Bundle bundle = getIntent().getExtras();
            data = bundle.getParcelableArrayList("Checked");

            View v = getLayoutInflater().inflate(R.layout.editerrors_list,null);

            sendremarks = (ImageButton) findViewById(R.id.Remarks);




                Log.d("----", "First loop " + data);
               String[] array = {String.valueOf(data)};
        arrayList = new ArrayList<Cars>();
        listView = (ListView) findViewById(R.id.description);

        getChecked = new ArrayList<String>();

           editClass = new EditClass(this,data);

           listView.setAdapter(editClass);

           sendremarks.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   int integer = editClass.getTotal();

                   Intent intent = new Intent(EditErrors.this,Remarks.class);
                   intent.putExtra("Total",integer);
                   startActivity(intent);


                   Toast.makeText(EditErrors.this,integer + "Success",Toast.LENGTH_LONG).show();

               }
           });

    }
}
