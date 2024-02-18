package com.app.repositories;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    Context context;
    ProgressDialog  progressDialog ;
    public Utils(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }
    public void showDialogBox(String title, String message){

        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void cancelDialogBox(){
        progressDialog.dismiss();
    }
}
