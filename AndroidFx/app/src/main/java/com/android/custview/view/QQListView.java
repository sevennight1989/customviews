package com.android.custview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.android.custview.R;
import com.com.android.custview.KLog;

public class QQListView extends ListView {

    private int touchSlop;
    private boolean isSliding;
    private int xDown;
    private int yDown;
    private int xMove;
    private int yMove;
    private LayoutInflater mInflater;
    private PopupWindow mPopupWindow;
    private int mPopupWindowWidth;
    private int mPopupWindowHeight;
    private Button mDelBt;
    private DelButtonClickListener mListener;
    private View mCurrentView;
    private int mCurrentViewPos;

    public interface DelButtonClickListener {
        void clickHappend(int pos);
    }

    public void setDelButtonClickListener(DelButtonClickListener mListener) {
        this.mListener = mListener;
    }

    public QQListView(Context context) {
        this(context, null);
    }

    public QQListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        View view = mInflater.inflate(R.layout.delete_button, null);
        mDelBt = view.findViewById(R.id.id_item_btn);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.getContentView().measure(0, 0);
        mPopupWindowHeight = mPopupWindow.getContentView().getMeasuredHeight();
        mPopupWindowWidth = mPopupWindow.getContentView().getMeasuredWidth();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                if (mPopupWindow.isShowing()) {
                    dismissPopupWindow();
                    return false;
                }
                mCurrentViewPos = pointToPosition(xDown, yDown);
                View view = getChildAt(mCurrentViewPos - getFirstVisiblePosition());
                mCurrentView = view;
                mDelBt.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.clickHappend(mCurrentViewPos);
                            mPopupWindow.dismiss();
                        }
                    }
                });
                break;

            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;
                int dy = yMove - yDown;
                if (xMove < xDown && Math.abs(dx) > touchSlop && Math.abs(dy) < touchSlop) {
                    isSliding = true;
                }
                break;

        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (isSliding) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    int[] location = new int[2];
                    mCurrentView.getLocationOnScreen(location);
                    mPopupWindow.setAnimationStyle(R.style.pop_animation);
                    mPopupWindow.update();
                    mPopupWindow.showAtLocation(mCurrentView, Gravity.LEFT | Gravity.TOP
                            , location[0] + mCurrentView.getWidth(), location[1] + mCurrentView.getHeight() / 2 - mPopupWindowHeight / 2);

                case MotionEvent.ACTION_UP:
                    isSliding = false;
                    break;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void dismissPopupWindow() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
