package com.android.custview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.android.custview.R;


public class CustomView01 extends View {

    private Bitmap mImage;

    private int mImageScale;

    private String mTitle;

    private int mTextColor;

    private int mTextSize;

    private Rect rect;

    private Rect mTextBound;

    private Paint mPaint;

    private int mWidth;

    private int mHeight;

    public static final int IMAGE_SCALE_FITXY = 0;

    public static final int IMAGE_SCALE_CENTER = 1;

    public CustomView01(Context context) {
        this(context, null);
    }

    public CustomView01(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView01, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView01_image:
                    mImage = getBitmap(context, a.getResourceId(attr, 0));
                    Log.d("PengLog", "mImage width:" + mImage.getWidth());
                    break;
                case R.styleable.CustomView01_imageScaleType:
                    mImageScale = a.getInt(attr, 0);
                case R.styleable.CustomView01_titleText:
                    mTitle = a.getString(attr);
                    break;
                case R.styleable.CustomView01_titleTextColor:
                    mTextColor = a.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomView01_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;

            }
        }
        a.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
            if (specMode == MeasureSpec.AT_MOST) {
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(specSize, desire);
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desire, specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private Bitmap getBitmap(Context context, int vectorDrawableId) {
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(4);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.top = getPaddingTop();
        rect.right = mWidth - getPaddingRight();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        if (mTextBound.width() > mWidth) {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, mWidth - getPaddingLeft() - getPaddingRight()
                    , TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        if (mImageScale == IMAGE_SCALE_FITXY) {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else if (mImageScale == IMAGE_SCALE_CENTER) {
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;
            canvas.drawBitmap(mImage, null, rect, mPaint);
        }

    }
}
