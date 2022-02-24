package com.example.jsnevent.model;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
//    String UserName,UserPassword,UserName;
    private static final String IS_LOGGED_IN = "logged in" ;


    public SharedPreference(Context context) {
        this.context = context;
       /* preferences = context.getSharedPreferences("MyPreference",0);
        editor = preferences.edit();
        editor.apply();*/
    }

    public void setLoggedIn(boolean state) {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGGED_IN, state);
        editor.apply();
    }


    /**
     *
     * @return true if user logged in else false
     */
    public boolean isLoggedIn() {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public void  setUserEmail(String email)
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userEmail", email);
        editor.apply();
    }

    /**
     *
     * @return email
     */
    public String getUserEmail()
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getString("userEmail","");
    }

    public void  setUserMobileNO(String mobileNo)
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userMobileNo", mobileNo);
        editor.apply();
    }

    /**
     *
     * @return email
     */
    public String getUserMobileNO()
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getString("userMobileNo","");
    }


    public void  setUserName(String name)
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", name);
        editor.apply();
    }

    /**
     *
     * @return userCode
     */
    public String getUseName()
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getString("userName","");
    }

    public void  setPassword(String password)
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userPassword", password);
        editor.apply();
    }

    /**
     *
     * @return userCode
     */
    public String getPassword()
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getString("userPassword","");
    }

    public void  setUserResetPassword(String resetPassword)
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userResetPassword", resetPassword);
        editor.apply();
    }

    /**
     *
     * @return email
     */
    public String getUserResetPassword()
    {
        SharedPreferences preferences = context.getApplicationContext()
                .getSharedPreferences("JSN", Context.MODE_PRIVATE);
        return preferences.getString("userResetPassword","");
    }


}
