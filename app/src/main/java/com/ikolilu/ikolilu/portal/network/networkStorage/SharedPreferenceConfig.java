package com.ikolilu.ikolilu.portal.network.networkStorage;

import android.content.Context;
import android.content.SharedPreferences;

import com.ikolilu.ikolilu.portal.R;

public class SharedPreferenceConfig {

    private Context context;
    public SharedPreferences sharedPreferences;


    private String CONFIRM_HOLDER_STRING = "is_confirm";


    public SharedPreferenceConfig(Context context ){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.IkoliluPrefs), Context.MODE_PRIVATE);
    }

}
