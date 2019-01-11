package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 10/04/2018.
 */

public class AccountHistory {
    private int id;
    private String date;
    private String term;
    private String acaYear;
    private String desc;
    private String trans;
    private String amount;
    private String cur;
    private String debit;
    private String credit;

    public AccountHistory(int id, String date, String term, String acaYear, String desc, String trans, String amount, String cur, String debit, String credit) {
        this.id = id;
        this.date = date;
        this.term = term;
        this.acaYear = acaYear;
        this.desc = desc;
        this.trans = trans;
        this.amount = amount;
        this.cur = cur;
        this.debit = debit;
        this.credit = credit;
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

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getAcaYear() {
        return acaYear;
    }

    public void setAcaYear(String acaYear) {
        this.acaYear = acaYear;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
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
}
