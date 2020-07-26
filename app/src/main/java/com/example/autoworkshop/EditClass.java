package com.example.autoworkshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class EditClass extends ArrayAdapter<Cars>{

    private List<Cars> description;
    private Context context;
    EditText unit,qty,total;


    public EditClass(@NonNull Context context, List<Cars> description) {
        super(context,R.layout.listview_editerrors,description);
        this.description = description;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_editerrors,null);
        }
        TextView descript = (TextView) convertView.findViewById(R.id.descriptiontext);
        unit = (EditText) convertView.findViewById(R.id.UnitPrice) ;
         qty = (EditText) convertView.findViewById(R.id.Quantity);
        total = (EditText) convertView.findViewById(R.id.TotalPrice);

        Cars c = description.get(position);
        String name = c.getName();


        descript.setText(name);

        unit.setText("");
        qty.setText("");
        total.setText("");


        return convertView;
    }


    public int getTotal(){
        int number  =0;
        for(int i =0;i<description.size();i++){
            number+=Integer.valueOf(total.getText().toString());

        }
        return number;
    }
}
