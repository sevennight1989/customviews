package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class Learn02View extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Learn02View(Context context) {
        this(context,null);
    }

    public Learn02View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Learn02View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.clipRect(100,100,300,300);
        canvas.drawBitmap(CommonUtils.getAvatar(getContext(), R.drawable.avatar,400),0,0,paint);

    }
}
