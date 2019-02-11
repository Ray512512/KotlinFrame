package com.ray.frame.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;

import com.ray.frame.R;


public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        this.getWindow().setGravity(Gravity.CENTER);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.setCanceledOnTouchOutside(true);
    }
}
