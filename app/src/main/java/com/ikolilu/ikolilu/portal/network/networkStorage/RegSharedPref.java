package com.ikolilu.ikolilu.portal.network.networkStorage;

import android.content.Context;
import android.content.SharedPreferences;


public class RegSharedPref extends SharedPreferenceConfig {

    private String REGSUCCESSKEY = "regSuccessKey";
    private Context context;

    public RegSharedPref(Context context) {
        super(context);
        this.context = context;
    }

    public void setRegSuccessKey(boolean regSuccess){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(REGSUCCESSKEY, regSuccess);
        editor.commit();
    }

    public boolean getRegSuccessKey(){
        boolean regSuccess = false;
        regSuccess = sharedPreferences.getBoolean(REGSUCCESSKEY, false);
        return regSuccess;
    }
}
