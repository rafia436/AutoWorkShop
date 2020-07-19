package com.example.autoworkshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestClass extends RecyclerView.Adapter<RequestClass.ViewHolder> {
    private Context mContext;
    private ArrayList<RequestFields> arrayList;

    public RequestClass(Context mContext, ArrayList<RequestFields> arrayList){
        this.mContext = mContext;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_lists,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img1;
        private TextView carname,carnumber;
        View mview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
            img1 = (ImageView) mview.findViewById(R.id.carimage);
            carname = (TextView) mview.findViewById(R.id.c_name);
            carnumber = (TextView) mview.findViewById(R.id.c_number);
        }
    }
}
