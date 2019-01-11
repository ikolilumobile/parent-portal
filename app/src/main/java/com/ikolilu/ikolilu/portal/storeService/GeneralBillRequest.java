package com.ikolilu.ikolilu.portal.storeService;

import android.content.Context;

import com.ikolilu.ikolilu.portal.network.APIRequest;

import org.json.JSONArray;

/**
 * Created by Genuis on 29/06/2018.
 */

public class GeneralBillRequest {
    public static JSONArray InvokeBillCaller(String stringRequest, Context mCtx){
        JSONArray object = null;

        // Static API Request Function/Routine
        object = APIRequest.runBillPayment(stringRequest, mCtx);
        return object;
    }

}
