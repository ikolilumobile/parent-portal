package com.ikolilu.ikolilu.portal.storeService;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Genuis on 29/06/2018.
 */

public class BillAmountService {
    private String wardID;
    private String classID;
    private String term;
    private String schoolID;
    Context context;

    GeneralBillRequest generalBillRequest;

    void BillAmountService(String wardID, String classID, String term, String schoolID, Context mCtx){
        this.wardID = wardID;
        this.classID = classID;
        this.term = term;
        this.schoolID = schoolID;
        this.context = mCtx;

    }

    public ArrayList<String> loadBillAmount(String request){
        JSONArray ja = GeneralBillRequest.InvokeBillCaller(request, this.context);
        Log.d("BILL-INFORMATION", ja.toString());
        return null;
    }

}
