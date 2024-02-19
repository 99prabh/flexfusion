package com.app.flexfusion.repositories;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static String ADMIN_UID = "kcTU2NsxlZfqeT9A7NE5zwVK7dH3";
    public static boolean isAdmin = false;
    Context context;
    ProgressDialog progressDialog;

    public Utils(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context);
    }

    public void showDialogBox(String title, String message) {

        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void cancelDialogBox() {
        progressDialog.dismiss();
    }
}
