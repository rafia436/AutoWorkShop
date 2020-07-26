package com.example.autoworkshop;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Cars implements Parcelable {
    String name;
    boolean isselected=false;

    String unit,price,qty;


    public Cars(String name, boolean isselected){
        super();
        this.name = name;
        this.isselected = isselected;

    }

    public Cars() {

    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    protected Cars(Parcel parcel){
        name = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);

    }

    public static final Creator<Cars> CREATOR = new Creator<Cars>() {
        @Override
        public Cars createFromParcel(Parcel parcel) {
            return new Cars(parcel);
        }

        @Override
        public Cars[] newArray(int i) {
            return new Cars[i];
        }
    };
}
public class CarAdapter extends ArrayAdapter<Cars> {

    private List<Cars> carsList;
    private Context context;
    private ArrayList<Cars> arrayList;
    public CarAdapter(@NonNull List<Cars> carsList, Context context) {
        super(context, R.layout.listview_errors, carsList);
        this.carsList = carsList;
        this.context = context;
        this.arrayList = new ArrayList<Cars>();
        this.arrayList.addAll(carsList);
    }

    @Override
    public int getCount() {
        return carsList.size();
    }

    @Override
    public Cars getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class CarHolder{
        public CheckBox checkBox;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_errors,null);


        }
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.bodycheckbox);

        checkBox.setOnCheckedChangeListener((ErrorList) context);
        Cars c = carsList.get(position);
        String name = c.getName();


        checkBox.setText(name);
       checkBox.setChecked(c.isselected);
        checkBox.setTag(c);
        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        carsList.clear();
        if (charText.length() == 0) {
            carsList.addAll(arrayList);
        } else {
            for (Cars wp : arrayList) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    carsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
