package com.example.androidprojetapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private SharedPreferences prefs;

    public SessionManager(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setKey(String key,String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String getKey(String keyName) {
        String key = prefs.getString(keyName,"");
        return key;
    }

}
