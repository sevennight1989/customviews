package com.android.custview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.zp.base.KLog;

public class ExpandLinearLayout extends LinearLayout {

    private boolean isOpen = true;

    private int firstChildHeight = 0;

    private int allChildHeight = 0;

    private float animPercent = 1.0f;


    public ExpandLinearLayout(Context context) {
        this(context,null);
    }

    public ExpandLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExpandLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        setOrientation(VERTICAL);
        isOpen = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        allChildHeight = 0;
        firstChildHeight = 0;
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int index = 0; index < childCount; index++) {
                ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) getChildAt(index).getLayoutParams();
                View child = getChildAt(index);
                if (index == 0) {
                    firstChildHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin + getPaddingTop() + getPaddingBottom();
                }
                allChildHeight += child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if (index == childCount - 1) {
                    allChildHeight += getPaddingTop() + getPaddingBottom();
                }
            }
            setMeasuredDimension(widthMeasureSpec, (int) (firstChildHeight + ((allChildHeight - firstChildHeight) * animPercent)));
        }
    }

    public boolean toggle() {
        if(isOpen){
            closeAnim();
        }else {
            openAnim();
        }
        isOpen = !isOpen;
        KLog.logI("toggle: " + isOpen);
        return isOpen;
    }

    public void openAnim(){
        ValueAnimator animator  = ValueAnimator.ofFloat(0f,1.0f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animPercent = (float) animation.getAnimatedValue();
                KLog.logI("onAnimationUpdate : " + animPercent);
                requestLayout();
            }
        });
        animator.start();
    }

    public void closeAnim(){
        ValueAnimator animator  = ValueAnimator.ofFloat(1.0f,0f);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animPercent = (float) animation.getAnimatedValue();
                KLog.logI("onAnimationUpdate2 : " + animPercent);
                requestLayout();
            }
        });
        animator.start();
    }
}
