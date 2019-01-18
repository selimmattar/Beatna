package com.mporject.interns.beatna;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx)
    {
        prefs= PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserName(String username)
    {
        prefs.edit().putString("username", username).commit();
    }

    public String getUserName()
    {
        String username=prefs.getString("username","");
        return username;
    }

    public void setUniqueId(String uniqueId)
    {
        prefs.edit().putString("uniqueid", uniqueId).commit();
    }

    public String getUniqueId()
    {
        String uniqueId=prefs.getString("uniqueid","");
        return uniqueId;
    }
}
