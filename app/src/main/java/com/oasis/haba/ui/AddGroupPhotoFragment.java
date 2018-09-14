package com.oasis.haba.ui;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fxn.pix.Pix;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.oasis.haba.Database.DatabaseHelper;
import com.oasis.haba.R;
import com.oasis.haba.util.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddGroupPhotoFragment extends Fragment {

    private ImageView groupPhoto;
    private Button Btndone;
    private ConstraintLayout changePhoto;
    private ArrayList<String> image;
    FirebaseStorage mInstance;
    StorageReference dbRef;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    DatabaseHelper helper;
    private static Uri imagelocation;
    String groupKey = FormCreateChama.getUserGroupId();

    public AddGroupPhotoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_groupphoto, container, false);

        helper = new DatabaseHelper(getActivity());
        groupPhoto = view.findViewById(R.id.imageIcon);
        Btndone = view.findViewById(R.id.btnCreate);
        changePhoto = view.findViewById(R.id.changeGPhoto);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mInstance = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();

        dbRef = mInstance.getReference();

        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();

            }
        });

        Btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirect to Chama Layout

                Intent intent = new Intent(getActivity(), Chama.class);
                startActivity(intent);
            }
        });


        return view;
    }

    public void selectimage() {

        //Select Group Photo
        Pix.start(this, 2);
    }

    //After Photo is selected
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {

            Toast.makeText(getActivity(), "Image selected", Toast.LENGTH_SHORT).show();
            image = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            uploadPicture();

        } else {
            Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    //Now Upload the picture to firebase because you are a bad ass
    public void uploadPicture() {
        //Now lets make the beast beautiful

        String key = FormCreateChama.getGroupName();
        Toast.makeText(getActivity(),"Key Fetched",Toast.LENGTH_SHORT).show();
        Uri imageUri = Uri.fromFile(new File(image.get(0)));
        //Then upload profile picture

        final StorageReference groupPic = dbRef.child("GroupPictures").child(key);
        groupPic.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // Get a URL to the uploaded content
                     groupPic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             Uri downloadUri = uri;
                             String downloadUrl = downloadUri.toString();

                             setImagelocation(downloadUrl);
                         }
                     });

                        //Change app icon
                        groupPhoto.setImageResource(R.drawable.success);
                        Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_LONG).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(getActivity(), "Image not Uploaded", Toast.LENGTH_LONG).show();
                    }
                });


    }

    public String getGroupKey(){
        //First get the group key,from the users number
        String userID = mAuth.getCurrentUser().getUid();
        Map userMap = helper.getNumber();
        Object number = (String) userMap.get(userID);
        //Confirm we have the number
        Toast.makeText(getActivity(),"Number Acquire",Toast.LENGTH_SHORT).show();
       final String[] key = new String[1];
        DatabaseReference groupKey = databaseReference.child("Users").child(String.valueOf(number)).child("AdminOfGroup");
        groupKey.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 key[0] = String.valueOf(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return key[0];
    }

    public void setImagelocation(String url){
        //Add the image location to group profile
        DatabaseReference groupPic = databaseReference.child("Chamas").child(groupKey);
        Map<String,Object> picture = new HashMap<>();
        picture.put("PictureLocation",url);
        groupPic.updateChildren(picture).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"Picture Added",Toast.LENGTH_SHORT).show();
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),"Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


    }



}
