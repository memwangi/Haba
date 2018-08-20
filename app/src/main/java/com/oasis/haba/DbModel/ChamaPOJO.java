package com.oasis.haba.DbModel;

import android.net.Uri;

public class ChamaPOJO {

    public String groupname;
    public String admin;
    public int paybillNumber;
    public int contributionAmount;
    public int contributionTimes;
    public String contributionPeriod;
    public Uri groupPictureUri;


    public ChamaPOJO() {
    }

    // Constructor to call Group Items in the recycler view in the HomeFragment


    public ChamaPOJO(String groupname, Uri groupPictureUri) {
        this.groupname = groupname;
        this.groupPictureUri = groupPictureUri;
    }

    public ChamaPOJO (String admin, String groupname, int paybillNumber, int contributionAmount, int contributionTimes, String contributionPeriod) {
        this.groupname = groupname;
        this.admin = admin;
        this.paybillNumber = paybillNumber;
        this.contributionAmount = contributionAmount;
        this.contributionTimes = contributionTimes;
        this.contributionPeriod = contributionPeriod;
    }


}
