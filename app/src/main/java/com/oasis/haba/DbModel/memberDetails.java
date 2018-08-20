package com.oasis.haba.DbModel;

import android.net.Uri;



public class memberDetails {

    //This class provides all information required to create a member display profile that will be
    //used in the chama recycler view and will contain a profile picture with a phone number

    public Uri pictureUri;
    public String phoneNumber;

    public memberDetails() {
    }

    public memberDetails(Uri pictureUri, String phoneNumber) {
        this.pictureUri = pictureUri;
        this.phoneNumber = phoneNumber;
    }

    public Uri getPictureUri() {

        return pictureUri;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
