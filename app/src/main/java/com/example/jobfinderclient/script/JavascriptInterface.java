package com.example.jobfinderclient.script;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.jobfinderclient.common.Constant;

public class JavascriptInterface {

    private Activity activity;

    public JavascriptInterface(Activity activity) {
        this.activity = activity;
    }

    @android.webkit.JavascriptInterface
    public void closePayActivity(){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constant.SHARED_ACCOUNT_SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("roleName","ROLE_VIPUSER");
        editor.apply();
        activity.finish();
    }
}
