package com.amravati.amravatifigtscovid19;

import android.app.Activity;
import android.app.AlertDialog;

public class loding {

    Activity activity;
    AlertDialog dialog;

    loding(Activity activity)
    {
        this.activity=activity;
    }

    public void StartLoding()
    {
        AlertDialog.Builder loging=new AlertDialog.Builder(activity);
        loging.setView(activity.getLayoutInflater().inflate(R.layout.loding,null));
        loging.setCancelable(false);
        dialog=loging.create();
        dialog.show();
    }

    public void stopLoding()
    {
        dialog.dismiss();
    }

}
