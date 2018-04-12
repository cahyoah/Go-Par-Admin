package com.example.asus.go_paradmin.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.go_paradmin.R;


/**
 * Created by adhit on 03/01/2018.
 */

public class ShowAlert {
    public static ProgressDialog dialog;
//    public static void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
//        TSnackbar snackbar = TSnackbar.make(coordinatorLayout, message, TSnackbar.LENGTH_LONG);
//        snackbar.setActionTextColor(Color.WHITE);
//        View snackbarView = snackbar.getView();
//        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
//        textView.setTextColor(Color.WHITE);
//        snackbar.show();
//    }
    public static void showProgresDialog(Context context){
        dialog= new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.text_loading));
        dialog.setCancelable(false);
        dialog.show();
    }
    public static void closeProgresDialog(){
        dialog.dismiss();
    }

    public static void showToast(Context context, String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
