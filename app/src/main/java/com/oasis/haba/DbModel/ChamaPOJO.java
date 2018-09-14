package com.oasis.haba.DbModel;

import android.net.Uri;

public class ChamaPOJO {

    public String groupname;
    public String admin;
    public int paybillNumber;
    public String contributionAmount;
    public int contributionTimes;
    public String contributionPeriod;
    private String PictureLocation;





    public ChamaPOJO() {
    }

    // Constructor to call Group Items in the recycler view in the HomeFragment


    public ChamaPOJO(String groupname, int paybillNumber, String contributionAmount, String PictureLocation) {
        this.groupname = groupname;
        this.paybillNumber = paybillNumber;
        this.contributionAmount = contributionAmount;
        this.PictureLocation = PictureLocation;
    }

    public ChamaPOJO (String admin, String groupname, int paybillNumber, String contributionAmount, int contributionTimes, String contributionPeriod) {
        this.groupname = groupname;
        this.admin = admin;
        this.paybillNumber = paybillNumber;
        this.contributionAmount = contributionAmount;
        this.contributionTimes = contributionTimes;
        this.contributionPeriod = contributionPeriod;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getpaybillNumber() {
        return paybillNumber;
    }

    public void setpaybillNumber(int paybillNumber) {
        this.paybillNumber = paybillNumber;
    }

    public String getContributionAmount() {
        return contributionAmount;
    }

    public void setContributionAmount(String contributionAmount) {
        this.contributionAmount = contributionAmount;
    }

    public int getContributionTimes() {
        return contributionTimes;
    }

    public void setContributionTimes(int contributionTimes) {
        this.contributionTimes = contributionTimes;
    }

    public String getContributionPeriod() {
        return contributionPeriod;
    }

    public void setContributionPeriod(String contributionPeriod) {
        this.contributionPeriod = contributionPeriod;
    }

    public String getPictureLocation() {
        return PictureLocation;
    }

    public void setPictureLocation(String PictureLocation) {
        this.PictureLocation = PictureLocation;
    }
}
