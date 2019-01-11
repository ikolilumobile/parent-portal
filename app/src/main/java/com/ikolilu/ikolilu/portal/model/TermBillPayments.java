package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 10/04/2018.
 */

public class TermBillPayments {
    private int id;
    private String date;
    private String billItem;
    private String currency;
    private String debit;
    private String credit;
    private String pay;

    public TermBillPayments(int id, String date, String billItem, String currency, String debit, String credit, String pay) {
        this.id = id;
        this.date = date;
        this.billItem = billItem;
        this.currency = currency;
        this.debit = debit;
        this.credit = credit;
        this.pay = pay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBillItem() {
        return billItem;
    }

    public void setBillItem(String billItem) {
        this.billItem = billItem;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
