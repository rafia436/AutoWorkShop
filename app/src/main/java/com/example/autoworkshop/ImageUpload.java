package com.example.autoworkshop;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageUpload extends RecyclerView.Adapter<ImageUpload.ImageViewHolder> {

    private Context mContext;
    private ArrayList<Uri> mUploads;

    public ImageUpload(Context mContext, ArrayList<Uri> mUploads) {
        this.mContext=mContext;
        this.mUploads = mUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
       // final ImageUpload uploadCurrent = mUploads.get(position);
        //imageList uploadCurrent = mUploads.get(position);
       // byte[] imageAsBytes = android.util.Base64.decode(uploadCurrent.getImageURL().getBytes(), Base64.DEFAULT);
       // holder.img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length));


        Picasso.with(mContext)
                .load(mUploads.get(position))
                .into(holder.img);


       /* Picasso.with(mContext)
                .load(uploadCurrent.getImageURL())
                .placeholder(R.mipmap.file_icon)
                .fit()
                .centerCrop()
                .into(holder.img); */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,CarUpdateInfo.class);
                // intent.putExtra()
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        View mview;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            mview=itemView;
            img = (ImageView) mview.findViewById(R.id.card_icon);
        }
    }



}
