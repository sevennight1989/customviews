package com.android.custview.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.custview.R;
import com.android.zp.base.KLog;

import java.util.Random;

public class CommuteDialog extends Dialog {
    private AppCompatTextView title;
    private int id = 0;
    public CommuteDialog(@NonNull Context context,CommuteDialog old) {
        super(context, R.style.show_dialog);
        KLog.logI("old is null " + (old == null));
        id = new Random().nextInt(1000);
        if (old != null) {
            KLog.logI("old id : " + old.id + "  new id : " + id + "  old is showing: " + old.isShowing());
            if (old.isShowing()) {
                old.dismiss();
            }
        }
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                KLog.logI("old onDismiss " + id);{
                }
            }
        });
        show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.getDecorView().setPadding(0, 0, 0, 0);
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        setContentView(R.layout.commute_dialog_ly);
        title = findViewById(R.id.dialog_title);
        title.setText(id+"");
    }
}
