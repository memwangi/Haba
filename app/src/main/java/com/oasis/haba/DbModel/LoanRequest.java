package com.oasis.haba.DbModel;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

public class LoanRequest {

    private String amountRequested;
    private String  userNumber;
    Long creationDate;

//Constructor


    public LoanRequest(String amountRequested, String userNumber) {
        this.amountRequested = amountRequested;
        this.userNumber = userNumber;
        this.creationDate = getCreationDate();
    }

    public String getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(String amountRequested) {
        this.amountRequested = amountRequested;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public java.util.Map<String, String> getCreationDate() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public Long getCreationDateLong() {
        return creationDate;
    }
    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

}
