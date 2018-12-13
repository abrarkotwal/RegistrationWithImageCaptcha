package com.abrarkotwal.socialapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import timber.log.Timber;


public class UiUtils {

    //Email Validation pattern
    public static final String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";

    public static void handleThrowable(Throwable throwable,Context context) {
        Timber.e(throwable, throwable.toString());
        Log.d("Abrar", String.valueOf(throwable.getLocalizedMessage()));

        if (throwable instanceof IOException) {
            //internetConnectionErrorAlert(context,(dialog, which) -> dialog.dismiss());
        }
        else if (throwable instanceof HttpException){
            Log.d("Abrar","Null Response");
        }
    }

    public static ProgressDialog showProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
        return progressDialog;
    }

    private static void internetConnectionErrorAlert(Context context, DialogInterface.OnClickListener onClickTryAgainButton) {
        String message = "Please Turn On Your Internet? Or Try Again!!!";
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Network Error")
                .setMessage(message)
                .setPositiveButton("Cancel", onClickTryAgainButton)
                .setNegativeButton("Turn Internet On",(dialog, which) -> turnOnInternet(context))
                .show();
    }

    private static void turnOnInternet(Context context){
        Intent i = new Intent(Settings.ACTION_SETTINGS);
        ((Activity) context).startActivityForResult(i, 0);
    }

    private static void timeoutErrorAlert(Context context, DialogInterface.OnClickListener onClickTryAgainButton) {
        String message = "Network time out error?";
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Network Error")
                .setMessage(message)
                .setPositiveButton("Try after sometime", onClickTryAgainButton)
                .show();
    }
}
