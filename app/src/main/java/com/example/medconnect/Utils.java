package com.example.medconnect;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Utils {
    public void hideKeyboard(View view, Activity activity){
        try{
            InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}