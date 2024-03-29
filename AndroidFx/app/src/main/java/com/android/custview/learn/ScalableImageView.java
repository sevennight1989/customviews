package com.android.custview.learn;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import com.android.custview.R;
import com.android.zp.base.KLog;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class ScalableImageView extends View implements Runnable {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;
    private static final float IMAGE_WIDTH = CommonUtils.dp2Px(200);
    private static final float OVER_SCALE_FACTOR = 1.5f;
    private float offsetX;
    private float offsetY;
    private float originOffsetX;
    private float originOffsetY;
    private float smallScale;
    private float bigScale;
    private boolean isBig = false;
    private final GestureDetectorCompat detector;
    private float currentScale;   //0 ~1f
    private ObjectAnimator scaleAnimator;
    private OverScroller scroller;
    private MyScaleListener myScaleListener;
    private ScaleGestureDetector scaleDetector;

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.RED);
        bitmap = CommonUtils.getAvatar(context, R.drawable.avatar, (int) IMAGE_WIDTH);

        detector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {//MotionEvent.ACTION_DOWN
                KLog.logI("GestureDetector onDown");
                return true;//返回true后面才能收到事件
            }

            @Override
            public void onShowPress(MotionEvent e) {//预按下
                KLog.logI("GestureDetector onShowPress");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {//单击抬手
                KLog.logI("GestureDetector onSingleTapUp");
                return false;//返回值不用
            }

            @Override
            public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {//onMove()
                if (isBig) {
                    offsetX -= distanceX;
                    offsetX = Math.min((bitmap.getWidth() * bigScale - getWidth()) / 2f, offsetX);
                    offsetX = Math.max(-(bitmap.getWidth() * bigScale - getWidth()) / 2f, offsetX);
                    offsetY -= distanceY;
                    offsetY = Math.min((bitmap.getHeight() * bigScale - getHeight()) / 2f, offsetY);
                    offsetY = Math.max(-(bitmap.getHeight() * bigScale - getHeight()) / 2f, offsetY);
                    invalidate();
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {//长按
                KLog.logI("GestureDetector onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {//惯性滑动
                if (isBig) {
                    KLog.logI("onFling");
                    scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                            -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                            (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                            -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                            (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);
//                    for (int i = 10; i < 100; i += 10) {
//                        postDelayed(() -> refresh(), i);
//                    }
                    postOnAnimation(ScalableImageView.this);
                }

                return false;
            }
        });
        //双击
        detector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {//双击不会回调，超过双击判断事件
                KLog.logI("GestureDetector onSingleTapConfirmed");
                return false;//返回值不用
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {//双击
                KLog.logI("GestureDetector onDoubleTap");
                isBig = !isBig;
                if (isBig) {
                    offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f) * bigScale / smallScale;
                    offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f) * bigScale / smallScale;
                    getScaleAnimator().start();
                } else {
                    getScaleAnimator().reverse();
                }
                return false;//返回值不用
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {//
                KLog.logI("GestureDetector onDoubleTapEvent");
                return false;//返回值不用
            }
        });
        // detector.setIsLongpressEnabled(false);//关闭长按
        scroller = new OverScroller(context);
        myScaleListener = new MyScaleListener();
        scaleDetector = new ScaleGestureDetector(context, myScaleListener);
    }

/*    private void refresh() {
        scroller.computeScrollOffset();
        offsetX = scroller.getCurrX();
        offsetY = scroller.getCurrY();
        invalidate();
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        if (!scaleDetector.isInProgress()) {
            result = detector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = (getWidth() - bitmap.getWidth()) / 2.0f;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2.0f;

        if (bitmap.getWidth() / (float) (bitmap.getHeight()) > getWidth() / (float) getHeight()) {
            smallScale = getWidth() / (float) bitmap.getWidth();
            bigScale = getHeight() / (float) bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = getHeight() / (float) bitmap.getHeight();
            bigScale = getWidth() / (float) bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (currentScale - smallScale) / (bigScale / smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0, 1);
//            scaleAnimator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    offsetX = 0;
//                    offsetY = 0;
//                }
//            });
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    private float getCurrentScale() {
        return currentScale;
    }

    private void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    @Override
    public void run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.getCurrX();
            offsetY = scroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }

    private class MyScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = initialScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
