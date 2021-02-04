package com.android.custview.live;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.zp.base.KLog;
import com.blankj.utilcode.util.SizeUtils;

public class LiveCardView extends FrameLayout {

    public interface Listener {
        void onCall(int visible);
    }

    private Listener listener;

    private Context mContext;

    private int cardWidth = SizeUtils.dp2px(258);

    private int cardHeight = 0;

    public LiveCardView(@NonNull Context context) {
        this(context, null);
    }

    public LiveCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LiveCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void showCard() {
        removeAllViews();
        addLiveCard();
        startVisibleAnimate(new Runnable() {
            @Override
            public void run() {
                setVisibility(VISIBLE);
                if (listener != null) {
                    listener.onCall(VISIBLE);
                }
            }
        });
    }

    public void dismissCard() {
        if (getChildCount() > 0) {
            startGoneAnimate(new Runnable() {
                @Override
                public void run() {
                    removeLiveCard();
                    setVisibility(GONE);
                    if (listener != null) {
                        listener.onCall(GONE);
                    }
                }
            });
        }
    }

    public void setOnVisibilityListener(Listener listener) {
        this.listener = listener;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    private void addLiveCard() {
        View view = inflate(mContext, R.layout.layout_goods, null);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KLog.logI("送礼物");
            }
        });
        measureCardSize(view);
        addView(view);
    }

    private void measureCardSize(View cardView) {
        cardView.measure(
                MeasureSpec.makeMeasureSpec(cardWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        );
        cardWidth = cardView.getMeasuredWidth();
        cardHeight = cardView.getMeasuredHeight();
    }

    private void removeLiveCard() {
        removeAllViews();
    }

    private void startVisibleAnimate(Runnable startAction) {
        animate()
                .scaleX(1f)
                .scaleY(1f)
                .translationY(0f)
                .translationX(0f)
                .setDuration(300)
                .withStartAction(startAction)
                .start();
    }

    private void startGoneAnimate(Runnable endAction) {
        animate()
                .scaleX(0f)
                .scaleY(0f)
                .translationX(-cardWidth / 2.0f)
                .translationY(cardHeight / 2.0f)
                .setDuration(300)
                .withEndAction(endAction)
                .start();
    }

}
