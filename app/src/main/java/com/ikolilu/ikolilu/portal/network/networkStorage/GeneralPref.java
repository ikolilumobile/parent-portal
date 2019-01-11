package com.ikolilu.ikolilu.portal.network.networkStorage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Genuis on 02/05/2018.
 */

public class GeneralPref {
    Context context;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "IkoliluPrefs" ;
    SharedPreferences.Editor e;

    public GeneralPref(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        e = sharedPreferences.edit();
    }

    public String getUserEmail(){
        String email = sharedPreferences.getString("userEmail", "");
        return email;
    }

    public void setSchoolNameSet(Set<String> schoolName){
        e.putStringSet("schoolNameSet", schoolName);
        e.commit();
    }

    public void reUpdateSchoolNameSet(Set<String> schoolName){
        sharedPreferences.getStringSet("schoolNameSet", null).clear();
        e.putStringSet("schoolNameSet", schoolName);
        e.commit();
    }

    public void setSchoolIdSet(Set<String> schoolId){
        e.putStringSet("schoolIdSet", schoolId);
        e.commit();
    }

    public void reUpdateSchoolIdSet(Set<String> schoolId){
        sharedPreferences.getStringSet("schoolIdSet", null).clear();
        e.putStringSet("schoolIdSet", schoolId);
        e.commit();
    }

    public Set<String> getSchoolNameSet(){
        Set<String> names = sharedPreferences.getStringSet("schoolNameSet", null);
        return names;
    }

    public Set<String> getSchoolIdSet(){
        Set<String> sid = sharedPreferences.getStringSet("schoolIdSet", null);
        return sid;
    }

    public void setSelectedWardItems( String key, String name) {
        e.putString(key, name);
        e.commit();
    }

    public String getSelectedWardItems( String key ){
        String skey = sharedPreferences.getString(key, null);
        return skey;
    }


}
