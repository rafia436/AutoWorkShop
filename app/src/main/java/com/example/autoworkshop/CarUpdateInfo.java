package com.example.autoworkshop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarUpdateInfo extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_CAR_REQUEST = 2;
    TextView Cname,CID,FormLink;
    FirebaseFirestore fstore;
    Task<DocumentSnapshot> db;
    String ID, img2;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;
    imageList imgs;
    ImageView img1;

    TextView amount;
    ImageView carimage;
    ImageButton carButton;
    DocumentReference documentReference;
    Button submit;
    Context mContext;
   CollectionReference collectionReference;
   EditText carname,carnumber;
   Uri filepath,carimagepath;
   StorageReference storageReference;
   ImageButton choose;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_carinfo);

        Bundle bundle = getIntent().getExtras();
        carname = (EditText) findViewById(R.id.EditCarName);
        carnumber = (EditText) findViewById(R.id.EditCarNumber);
        submit = (Button) findViewById(R.id.submit);
        FormLink = (TextView) findViewById(R.id.Link);
        choose = (ImageButton) findViewById(R.id.Choose);
        carButton=(ImageButton) findViewById(R.id.ChooseCar);
        carimage = (ImageView) findViewById(R.id.Carimage);

//        Cname.setText(bundle.getString("Name"));


        ID = bundle.getString("ID");
        storageReference = FirebaseStorage.getInstance().getReference();

       // imgs = new imageList();
        fstore = FirebaseFirestore.getInstance();

   collectionReference = fstore.collection("users").document(ID).collection("Registeration Cards");
   collectionReference.document("Request").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
       @Override
       public void onSuccess(DocumentSnapshot documentSnapshot) {
           Toast.makeText(CarUpdateInfo.this,collectionReference.getId() +" information ",Toast.LENGTH_SHORT).show();
           //img1 = documentSnapshot.get("Image");
           Toast.makeText(CarUpdateInfo.this,documentSnapshot.getData() +" images ",Toast.LENGTH_SHORT).show();

           //Uri image = (Uri) documentSnapshot.get("Image");

           //Picasso.with(mContext).load("content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2F1wNM3RRbQ8%2BuVFzEeiyMww_thumb_93.jpg").into(img1);
          // ArrayList<String> image = (ArrayList<String>) documentSnapshot.get("Image");
          // Picasso.with(mContext).load(image.get(0)).into(img1);


       }
   });

      submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              String name = carname.getText().toString();
              String number = carnumber.getText().toString();



                Boolean status = true;
              if(name.isEmpty()){
                  carname.setError("Car Name Required");
                  carname.requestFocus();
                  return;
              }

              if(number.isEmpty()){
                  carnumber.setError("Car Number Required");
                  carnumber.requestFocus();
                  return;
              }

              if(filepath == null){
                  FormLink.setError("Image Required");
                  FormLink.requestFocus();
                  return;


              }
                if(carimagepath == null){
                    return;

                }


              Map<String, Object> CarInfo = new HashMap<>();
              CarInfo.put("CarName", name);
              CarInfo.put("CarNumber" , number);
              CarInfo.put("RequestStatus", status);

              // CarInfo.put("Inspection Form", )
              DocumentReference documentReference = collectionReference.document("Request");
              documentReference.update(CarInfo);
              Toast.makeText(CarUpdateInfo.this," Success ",Toast.LENGTH_SHORT).show();

              Intent intent = new Intent(getApplicationContext(),ErrorList.class);
              startActivity(intent);




          }
      });

      choose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              ShowFileChooser();
          }
      });

      carButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              CarImageChooser();
          }
      });
    }

    private void CarImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_CAR_REQUEST);
    }

    private void ShowFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filepath = data.getData();


            if(filepath!=null){
                FormLink.setError(null);


                final StorageReference ref = storageReference.child("uploads");

                String filename = getFilename(filepath);
                Toast.makeText(CarUpdateInfo.this,filename + " is the text", Toast.LENGTH_SHORT).show();

                FormLink.setText(filename);

                ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(CarUpdateInfo.this,uri + " is the link", Toast.LENGTH_SHORT).show();
                                Uri downloaduri = uri;
                                String download = downloaduri.toString();
                                saveUploadedToFireStore(download);
                            }
                        });

                    }
                });
            }
        }
        else if(requestCode==PICK_CAR_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            carimagepath = data.getData();
            if(carimagepath!=null){
                Picasso.with(this)
                        .load(carimagepath)
                        .into(carimage);
                final StorageReference reference = storageReference.child("uploads");
                reference.putFile(carimagepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadURI = uri;
                                String imageURI = downloadURI.toString();
                                saveCarImage(imageURI);

                            }
                        });
                    }
                });
            }

        }
    }

    private void saveUploadedToFireStore(String download){
        Map<String , Object> Form = new HashMap<>();
        Form.put("InspectionForm", download);
        DocumentReference docref = collectionReference.document("Request");
        docref.update(Form);

    }
    private void saveCarImage(String imageURI){
        Map<String, Object> Car = new HashMap<>();
        Car.put("CarImage", imageURI);
        DocumentReference documentReference1 = collectionReference.document("Request");
        documentReference1.update(Car);
    }

    public String getFilename(Uri uri){
        String result = null;
        if(uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor!=null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if(result==null){
            result = uri.getPath();
            int cut = result.lastIndexOf("/");
            if(cut!=-1){
                result = result.substring(cut+1);
            }
        }
        return result;
    }

}
