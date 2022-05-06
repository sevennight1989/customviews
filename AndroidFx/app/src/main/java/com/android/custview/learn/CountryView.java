package com.android.custview.learn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class CountryView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private String province = "北京";
    public CountryView(Context context) {
        this(context,null);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }

    public CountryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            paint.setColor(Color.RED);
            paint.setTextSize(CommonUtils.dp2Px(50));
            paint.setTextAlign(Paint.Align.CENTER);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(province,getWidth()/2,getHeight()/2,paint);
    }
}
