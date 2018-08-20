package com.oasis.haba.DbModel;

public class LNMExpress {
    //Lipa na Mpesa Express, an object contaaining details of any payment made to the group
    private String BUSINESS_SHORT_CODE;
    private String PASS_KEY;
    private String AMOUNT;
    private String PARTY_A;
    private String PARTY_B;
    private String PHONE_NUMBER;
    private String CALLBACK_URL;
    private String ACCOUNT_DIFFERENCE;
    private String TRANSACTION_DESCRIPTION;

    public LNMExpress(String BUSINESS_SHORT_CODE, String PASS_KEY, String AMOUNT, String PARTY_A, String PARTY_B, String PHONE_NUMBER,
                      String CALLBACK_URL, String ACCOUNT_DIFFERENCE, String TRANSACTION_DESCRIPTION) {
        this.BUSINESS_SHORT_CODE = BUSINESS_SHORT_CODE;
        this.PASS_KEY = PASS_KEY;
        this.AMOUNT = AMOUNT;
        this.PARTY_A = PARTY_A;
        this.PARTY_B = PARTY_B;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.CALLBACK_URL = CALLBACK_URL;
        this.ACCOUNT_DIFFERENCE = ACCOUNT_DIFFERENCE;
        this.TRANSACTION_DESCRIPTION = TRANSACTION_DESCRIPTION;
    }
}
