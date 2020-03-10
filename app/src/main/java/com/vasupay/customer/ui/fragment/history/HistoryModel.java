package com.vasupay.customer.ui.fragment.history;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class HistoryModel {
    @ServerTimestamp
    private Date history_timestamp;
    private String history_date;
    private String history_id;
    private String history_acc_holder_name;
    private String history_acc_no;
    private String history_ifsc;
    private String history_amount;
    private String history_billto;
    private String history_mobile;
    private String history_email;

    public HistoryModel() {
    }

    public HistoryModel(Date history_timestamp, String history_date, String history_id, String history_acc_holder_name, String history_acc_no, String history_ifsc, String history_amount, String history_billto, String history_mobile, String history_email) {
        this.history_timestamp = history_timestamp;
        this.history_date = history_date;
        this.history_id = history_id;
        this.history_acc_holder_name = history_acc_holder_name;
        this.history_acc_no = history_acc_no;
        this.history_ifsc = history_ifsc;
        this.history_amount = history_amount;
        this.history_billto = history_billto;
        this.history_mobile = history_mobile;
        this.history_email = history_email;
    }

    public Date getHistory_timestamp() {
        return history_timestamp;
    }

    public void setHistory_timestamp(Date history_timestamp) {
        this.history_timestamp = history_timestamp;
    }

    public String getHistory_date() {
        return history_date;
    }

    public void setHistory_date(String history_date) {
        this.history_date = history_date;
    }

    public String getHistory_id() {
        return history_id;
    }

    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

    public String getHistory_acc_holder_name() {
        return history_acc_holder_name;
    }

    public void setHistory_acc_holder_name(String history_acc_holder_name) {
        this.history_acc_holder_name = history_acc_holder_name;
    }

    public String getHistory_acc_no() {
        return history_acc_no;
    }

    public void setHistory_acc_no(String history_acc_no) {
        this.history_acc_no = history_acc_no;
    }

    public String getHistory_ifsc() {
        return history_ifsc;
    }

    public void setHistory_ifsc(String history_ifsc) {
        this.history_ifsc = history_ifsc;
    }

    public String getHistory_amount() {
        return history_amount;
    }

    public void setHistory_amount(String history_amount) {
        this.history_amount = history_amount;
    }

    public String getHistory_billto() {
        return history_billto;
    }

    public void setHistory_billto(String history_billto) {
        this.history_billto = history_billto;
    }

    public String getHistory_mobile() {
        return history_mobile;
    }

    public void setHistory_mobile(String history_mobile) {
        this.history_mobile = history_mobile;
    }

    public String getHistory_email() {
        return history_email;
    }

    public void setHistory_email(String history_email) {
        this.history_email = history_email;
    }
}
