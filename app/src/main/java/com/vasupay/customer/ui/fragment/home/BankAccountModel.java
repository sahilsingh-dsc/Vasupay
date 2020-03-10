package com.vasupay.customer.ui.fragment.home;

public class BankAccountModel {
    private String ba_id;
    private String ba_name;
    private String ba_number;
    private String ba_ifsc;

    public BankAccountModel() {
    }

    public BankAccountModel(String ba_id, String ba_name, String ba_number, String ba_ifsc) {
        this.ba_id = ba_id;
        this.ba_name = ba_name;
        this.ba_number = ba_number;
        this.ba_ifsc = ba_ifsc;
    }

    public String getBa_id() {
        return ba_id;
    }

    public void setBa_id(String ba_id) {
        this.ba_id = ba_id;
    }

    public String getBa_name() {
        return ba_name;
    }

    public void setBa_name(String ba_name) {
        this.ba_name = ba_name;
    }

    public String getBa_number() {
        return ba_number;
    }

    public void setBa_number(String ba_number) {
        this.ba_number = ba_number;
    }

    public String getBa_ifsc() {
        return ba_ifsc;
    }

    public void setBa_ifsc(String ba_ifsc) {
        this.ba_ifsc = ba_ifsc;
    }
}
