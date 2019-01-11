package com.ikolilu.ikolilu.portal.network.networkStorage;

import android.content.Context;
import android.content.SharedPreferences;


public class AuthSharedPref extends SharedPreferenceConfig {

    private String LOGIN_HOLDER_STRING = "is_login";

    private Context context;

    private String USERNAME  = "userName";
    private String USEREMAIL = "userEmail";
    private String USERPHONE = "userPhone";
    private String USERISACTIVE = "userIsActive";

    public AuthSharedPref(Context context) {
        super(context);
        this.context = context;
    }

    public void setLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LOGIN_HOLDER_STRING, status);
        editor.commit();
    }

    public void setUserName(String userName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, userName);
        editor.commit();
    }

    public void setUserEmail(String userEmail){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USEREMAIL, userEmail);
        editor.commit();
    }

    public void setUserPhone(String userPhone){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERPHONE, userPhone);
        editor.commit();
    }

    public void setUserIsActive(boolean userIsActive){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(USERISACTIVE, userIsActive);
        editor.commit();
    }

    public boolean getLoginStatus(){
        boolean status = false;
        status = sharedPreferences.getBoolean(LOGIN_HOLDER_STRING, status);
        return status;
    }

    public String getUserName(){
        String username = null;
        username = sharedPreferences.getString(USERNAME, username);
        return username;
    }

    public String getUserEmail(){
        String useremail = null;
        useremail = sharedPreferences.getString(USEREMAIL, useremail);
        return useremail;
    }

    public String getUserPhone(){
        String userphone = null;
        userphone = sharedPreferences.getString(USERPHONE, userphone);
        return userphone;
    }

    public boolean getUserIsActive(){
        boolean userIsActive = false;
        userIsActive = sharedPreferences.getBoolean(USERISACTIVE, false);
        return userIsActive;
    }
}
