package com.oasis.haba.DbModel;

public class ChamaPOJO {

    public String name ;
    private double Expenses;
    private double amountContributed;
    private double loans;

    public ChamaPOJO(String name, double expenses, double amountContributed, double loans) {
        this.name = name;
        Expenses = expenses;
        this.amountContributed = amountContributed;
        this.loans = loans;
    }

    public String getExpenses() {

        return Double.toString(Expenses);
    }

    public void setExpenses(double expenses) {
        Expenses = expenses;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public double getAmountContributed() {
        return amountContributed;
    }

    public void setAmountContributed(double amountContributed) {
        this.amountContributed = amountContributed;
    }

    public double getLoans() {
        return loans;
    }

    public void setLoans(double loans) {
        this.loans = loans;
    }
}
