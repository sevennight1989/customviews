package com.android.custview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.android.custview.R;
import com.android.zp.base.KLog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class GalleryImageView extends AppCompatImageView {

    private int width = 0;
    private int height = 0;
    private Paint mPaint;
    private Bitmap bitmap_src;
    private Bitmap bitmap_dest;

    public GalleryImageView(@NonNull Context context) {
        this(context, null);
    }

    public GalleryImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        KLog.logI("width: " + width + "  height: " + height);
    }

    public void setSourceBitmap(Bitmap bitmap) {
        bitmap_src = bitmap;
        bitmap_dest = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bg_gallery);
        postInvalidate();
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap_src == null) {
            KLog.logE("bitmap_src is null");
            return;
        }
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);

        KLog.logI("bitmap_src " + bitmap_src.getWidth() + "   " + bitmap_src.getHeight());
        Rect srcRect = new Rect(0, 0, bitmap_src.getWidth(), bitmap_src.getHeight());
        Rect dstRect = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap_src, srcRect, dstRect, mPaint);

        int bitmapWidth = bitmap_dest.getWidth();
        int bitmapHeight = bitmap_dest.getHeight();
        KLog.logI("bitmap_dest " + bitmap_dest.getWidth() + "   " + bitmap_dest.getHeight());
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        Rect srcRect2 = new Rect(0, 0, bitmapWidth, bitmapHeight);
        Rect dstRect2 = new Rect(0, 0, width, height);
        canvas.drawBitmap(bitmap_dest, srcRect2, dstRect2, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);

    }

}
