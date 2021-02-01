package com.example.notes.model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfoProvider {
    private static final String KEY_LOGIN = "login";

    private final SharedPreferences sharedPreferences;

    public UserInfoProvider(Context ctx) {
        sharedPreferences = ctx.getSharedPreferences("UserInfo", 0);
    }

    public String getLogin() {
        return sharedPreferences.getString(KEY_LOGIN, "");
    }

    public void setLogin(String login) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, login);
        editor.commit();
    }
}
