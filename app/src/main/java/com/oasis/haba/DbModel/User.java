package com.oasis.haba.DbModel;

import android.net.Uri;

public class User {
    public static String TABLE_NAME = "UserNumber";
    public static String USER_NUMBER = "PhoneNumber";
    public static final String COLUMN_KEY = "key";

    public String userID;
    public String userName;
    public String emailaddress;
    public String phoneNumber;
    private Uri imageLocation;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String emailaddress, String userName,String userID) {

        this.emailaddress = emailaddress;
        this.userName = userName;
        this.userID = userID;

    }

    //Store user phone number in local storage
    public static final String CREATE_TABLE =
            " CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_KEY +  " VARCHAR(100) PRIMARY KEY,"
                    + USER_NUMBER +  " VARCHAR(10) "
                    + ")";

}
