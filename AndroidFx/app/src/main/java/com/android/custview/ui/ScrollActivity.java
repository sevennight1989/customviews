package com.android.custview.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.custview.R;
import com.com.android.custview.KLog;

public class ScrollActivity extends AppCompatActivity {

    private RelativeLayout scroll;
    private int maxheight = 900;

    private int curHeight = maxheight;
    RelativeLayout.LayoutParams lp;
    float mLastY = 0;
    float mLastuPY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        scroll = findViewById(R.id.scroll);
        scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastuPY = mLastY = event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:

                        float y = event.getRawY();
                        float diff = y - mLastY;
                        if (diff > 0) {
                            if (lp.height > 0) {
                                lp.height -= diff;
                                scroll.setLayoutParams(lp);
                            }
                        } else {
                            if (lp.height < maxheight) {
                                lp.height -= diff;
                                scroll.setLayoutParams(lp);
                            } else {
                                return v.onTouchEvent(event);
                            }
                        }
                        mLastY = y;
                        break;

                    case MotionEvent.ACTION_UP:
                        int ch = lp.height;
                        float curdiff = event.getRawY() - mLastuPY;
                        KLog.logD("ch : " + ch);
                        //下滑
                        if (curdiff > 0) {
                            if (ch < maxheight / 4 * 3) {
                                ValueAnimator anim = ValueAnimator.ofFloat(ch, 0);
                                anim.setDuration(500);
                                anim.start();
                                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float currentValue = (float) animation.getAnimatedValue();
                                        KLog.logD("currentValue: " + currentValue);
                                        lp.height = (int) currentValue;
                                        KLog.logD("lp.height: " + lp.height);
                                        scroll.setLayoutParams(lp);
                                    }
                                });
//
                            }
                        }

                        mLastuPY = event.getRawY();
                        break;
                }
                return true;
            }
        });
        lp = (RelativeLayout.LayoutParams) scroll.getLayoutParams();
        curHeight = lp.height;


    }

}
