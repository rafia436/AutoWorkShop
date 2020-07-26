package com.example.autoworkshop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ErrorList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SearchView.OnQueryTextListener {
    private ListView llhchbx;

    private SearchView searchView;
    private String[] data = {"Bonnet","Front bumper","Left fender","Front left door","Rear left door", "Left rocker/sill panels","Left quarter pannel","Trunk/Boot door", "Rear Bumper",
    "Right quarter panel","Rear right door","Front right door", "Right rocker/sill panels","Right fender","Car pillars","Car roof","All body parts properly aligned"
    ,"Rearview and sideview mirrors","Convertible top(if applicable)"};
    ImageButton Send;
    private ArrayList<Cars> arrData ;
     ArrayList<String> checked;
    private ArrayList<InfoRowData> infoRowData;
    CarAdapter carAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_errors);


        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        llhchbx = (ListView) findViewById(R.id.errorslist);

        arrData = new ArrayList<Cars>();


       Send = (ImageButton) findViewById(R.id.send);


        for(int i = 0;i<data.length;i++){
            Cars c = new Cars(data[i],false);
            arrData.add(c);

        }

        carAdapter = new CarAdapter(arrData,this);

        displayCarsList();
        llhchbx.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

      Send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              ArrayList<Cars> arrayList = new ArrayList<Cars>();
              for(int index = 0;index <data.length;index++){
                  Cars c = arrData.get(index);

                //  String name = c.getName();
                  if(c.isselected){

                      arrayList.add(c);



                  }
              }

              Intent intent = new Intent(ErrorList.this,EditErrors.class);
              intent.putParcelableArrayListExtra("Checked",arrayList);
              startActivity(intent);
              }
      });



    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }


    public boolean onQueryTextChange(String newText) {
        String text = newText;
        carAdapter.filter(text);
        return false;
    }

    private void displayCarsList(){

        llhchbx.setAdapter(carAdapter);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int pos = llhchbx.getPositionForView(compoundButton);

        if(pos!= ListView.INVALID_POSITION){
            Cars cars=arrData.get(pos);
            cars.setIsselected(b);

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
