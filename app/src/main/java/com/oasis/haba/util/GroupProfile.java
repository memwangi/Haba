package com.oasis.haba.util;

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;

public class GroupProfile {

    private static final String TAG = "GROUPPROFILE(HOME)";
private Uri groupPictureUrl;
private String groupKey;
private String groupName;
private Double loanDue;
private Double MyshareValue;
private Double GrossGroupValue;


    public GroupProfile(String groupName, Double loanDue, Double myshareValue, Double grossGroupValue) {
        this.groupName = groupName;
        this.loanDue = loanDue;
        MyshareValue = myshareValue;
        GrossGroupValue = grossGroupValue;
    }


    public Uri getGroupPictureUrl() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference refstorage = storage.getReference().child(getGroupID());

        refstorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                groupPictureUrl = uri;
                Log.i(TAG,"Found Group Picture Url");
            }
        });
        return groupPictureUrl;
    }

    public String getGroupID() {



        return groupKey;
    }

    public String getGroupName() {
        return groupName;
    }

    public Double getLoanDue() {
        return loanDue;
    }

    public Double getMyshareValue() {
        return MyshareValue;
    }

    public Double getGrossGroupValue() {
        return GrossGroupValue;
    }
}


