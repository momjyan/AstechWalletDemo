package com.example.sean.registrationactivity_lesson15.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.util.Log;

/**
 * Created by Sean on 8/17/2017.
 */

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "androidLogin";
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }
    public void SetLogin(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.commit();
        Log.d(TAG,"User login session modified");
    }
    public boolean IsLoggedIn(){
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false);
    }
}
