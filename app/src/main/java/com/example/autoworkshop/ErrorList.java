package com.example.autoworkshop;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ErrorList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SearchView.OnQueryTextListener {
    private ListView llhchbx;
    private String[] title = {"Car Body"};
    private SearchView searchView;
    private String[] data = {"Bonnet","Front bumper","Left fender","Front left door","Rear left door", "Left rocker/sill panels","Left quarter pannel","Trunk/Boot door", "Rear Bumper",
    "Right quarter panel","Rear right door","Front right door", "Right rocker/sill panels","Right fender","Car pillars","Car roof","All body parts properly aligned"
    ,"Rearview and sideview mirrors","Convertible top(if applicable)"};



    private ArrayList<Cars> arrData ;
     ArrayList<String> arraytitle = null;
    private ArrayList<InfoRowData> infoRowData;
    CarAdapter carAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_errors);

       // arraytitle = new ArrayList<String>();
        //arraytitle.add("Car Body");
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);



        llhchbx = (ListView) findViewById(R.id.errorslist);

        arrData = new ArrayList<Cars>();



       /* arrData.add(new Cars("Bonnet",false));
        arrData.add(new Cars("Front bumper",false));
        arrData.add(new Cars("Left fender",false));
        arrData.add(new Cars("Front left door",false));
        arrData.add(new Cars("Rear left door",false));
        arrData.add(new Cars("Left rocker/sill panels",false));
        arrData.add(new Cars("Left quarter pannel",false));
        arrData.add(new Cars("Trunk/Boot door",false));
        arrData.add(new Cars("Rear Bumper",false));
        arrData.add(new Cars("Right quarter panel",false));
        arrData.add(new Cars("Rear right door",false));
        arrData.add(new Cars("Front right door",false));
        arrData.add(new Cars("Right rocker/sill panels",false));
        arrData.add(new Cars("Right fender",false));
        arrData.add(new Cars("Car pillars",false));
        arrData.add(new Cars("Car roof",false));
        arrData.add(new Cars("All body parts properly aligned",false));
        arrData.add(new Cars("Rearview and sideview mirrors",false));
        arrData.add(new Cars("Convertible top(if applicable)",false)); */

        for(int i = 0;i<data.length;i++){
            Cars c = new Cars(data[i],false);
            arrData.add(c);

        }

        carAdapter = new CarAdapter(arrData,this);

        displayCarsList();



      /*  SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);*/



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

        //ListView listView= (ListView) findViewById(R.id.errorslist);
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




    /*public class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            View row = null;
            row = View.inflate(getApplicationContext(), R.layout.listview_errors, null);
            // TextView Body = (TextView) row.findViewById(R.id.cartitle);
            //Body.setText("Car Body");
            final CheckBox cb = (CheckBox) row.findViewById(R.id.bodycheckbox);
            cb.setText(data[i]);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (infoRowData.get(i).isclicked)
                        infoRowData.get(i).isclicked = false;
                    else
                        infoRowData.get(i).isclicked = true;

                    for (int position = 0; position < infoRowData.size(); position++) {
                        if (infoRowData.get(position).isclicked) {
                            System.out.println("Selectes Are == " + data[i]);
                        }
                    }
                }
            });

            if (infoRowData.get(i).isclicked) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            return row;
        }

        // Filter Class





    }*/
}
