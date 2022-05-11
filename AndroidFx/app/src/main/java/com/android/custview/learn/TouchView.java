package com.android.custview.learn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TouchView extends View {

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            performClick();
        }

//        event.getAction();
//        event.getActionMasked()
//        event.getActionIndex()
//        MotionEvent.ACTION_DOWN;
//        MotionEvent.ACTION_UP;
//        MotionEvent.ACTION_MOVE;
//        MotionEvent.ACTION_CANCEL;
//        MotionEvent.ACTION_POINTER_DOWN;
//        MotionEvent.ACTION_POINTER_UP;

        return true;//返回true消费事件，后续的控件和父控件都收不到
        //等同于直接return true
//        if(event.getActionMasked() == MotionEvent.ACTION_DOWN){
//            return true;
//        }else {
//            return false;
//        }

    }
}
