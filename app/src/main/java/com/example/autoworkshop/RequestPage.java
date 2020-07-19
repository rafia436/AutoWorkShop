package com.example.autoworkshop;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RequestPage extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageButton mSelectBtn;
    private Uri imageUri;
    ImageButton registerbtn;
    DatabaseReference refs;
    FirebaseAuth firebaseAuth;
    private List<Uri> Image;
    RecyclerView listview;
    FirebaseFirestore firestore;
    Uri url ;
    Uri ImageUri;
    String User_id;
    private List<String> photos;
    String docID;
    ArrayList<Uri> ImageUriArrayList;
    ArrayList<Uri> ImageUriDownloadList;
    UserInfo profile;
    private CollectionReference collectionReference;
    private StorageTask mUploadTask;
    ImageUpload adapter;
    private int uploads = 0;

    String displayname;
    String phonenumber;
    ImageView img1, img2;
    DatabaseReference databaseReference;
    Integer key;
  FirebaseAuth.AuthStateListener mAuth;

  DatabaseReference users;
    FirebaseUser userID;
    DocumentReference db;
    ArrayList<String> imageDUrlString;
    ArrayList<String> imageUriList;
    Query query;
    Map<String, Object> imageData;
    // private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;
    String Database_Path = "All_Image_Uploads_Database";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity);
        mSelectBtn = (ImageButton) findViewById(R.id.Upload);
        registerbtn = (ImageButton) findViewById(R.id.registerbutton);
        mStorage = FirebaseStorage.getInstance().getReference("uploads");
        Image = new ArrayList<>();
        ImageUriDownloadList = new ArrayList<>();

        listview = (RecyclerView) findViewById(R.id.Listview) ;
        refs = FirebaseDatabase.getInstance().getReference("uploads");
        firebaseAuth = FirebaseAuth.getInstance();
       databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
       User user = new User();
        displayname = user.getUserName();
        phonenumber = user.getPhonenum();

        listview.setLayoutManager(new LinearLayoutManager(this));
        listview.setHasFixedSize(true);




        firestore = FirebaseFirestore.getInstance();
        userID  = firebaseAuth.getCurrentUser();

       db = firestore.collection("users").document(userID.getUid());


       db.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {
               String name = documentSnapshot.getString("Name");
               String phone = documentSnapshot.getString("Phone");
               Toast.makeText(RequestPage.this,name +" logged in ",Toast.LENGTH_SHORT).show();

           }
       });

        mSelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();


            }
        });


    }

                private void openFileChooser () {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
                }

                @Override
                protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

                        //imageUri = data.getClipData().getItemAt(0).getUri();
                        //ImageView imageView = findViewById(R.id.imageView);
                        ArrayList<imageList> bitmaps = new ArrayList<>();

                        ClipData clipData = data.getClipData();
                       if(clipData!=null){
                           ImageUriArrayList = new ArrayList<>();
                           for(int i = 0; i < clipData.getItemCount();i++) {
                               ImageUri = clipData.getItemAt(i).getUri();

                            ImageUriArrayList.add(ImageUri);
                              String Images = ImageUriArrayList.toString();
                               Toast.makeText(RequestPage.this,Images+" displayed",Toast.LENGTH_SHORT).show();
                               StorageReference ref = mStorage.child("uploads");
                               final StorageReference storageReference = ref.child(ImageUri.getLastPathSegment());



                               storageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                     // Task<Uri> image = taskSnapshot.getStorage().getDownloadUrl();
                                      /* storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                           @Override
                                           public void onSuccess(Uri uri) {

                                               ImageUriDownloadList.add(uri);

                                              imageDUrlString = new ArrayList<>();
                                               for (int i = 0; i < ImageUriDownloadList.size(); i++) {
                                                   imageDUrlString.add(ImageUriDownloadList.get(i).toString());
                                               }
                                               imageData = new HashMap<>();
                                               imageData.put("Image" + key, imageDUrlString);
                                               collectionReference.add(imageData);

                                               //collectionReference.add(imageData);
                                               Toast
                                                       .makeText(RequestPage.this,
                                                               ImageUriDownloadList + "Image Uploaded!!",
                                                               Toast.LENGTH_SHORT)
                                                       .show();







                                           }
                                       }); */


                                       Toast
                                               .makeText(RequestPage.this,
                                                       "Image Uploaded!!",
                                                       Toast.LENGTH_SHORT)
                                               .show();

                                   }
                               });

                              key = i;


                          }


                           adapter = new ImageUpload(RequestPage.this,ImageUriArrayList);
                           listview.setAdapter(adapter);
                           imageData = new HashMap<>();

                           imageData.put("Image", FieldValue.arrayUnion(ImageUriArrayList.get(0).toString(),ImageUriArrayList.get(1).toString()));
                           collectionReference = db.collection("Registeration Cards");
                           DocumentReference documentReference = collectionReference.document("Request");
                           documentReference.set(imageData);


                    }
                    }
                }
            }










