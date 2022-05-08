package com.android.custview.learn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class DrawableView extends View {

    private Drawable colorDrawable;
    private Drawable convertedDrawable;
    private Drawable meshDrawable;

    public DrawableView(Context context) {
        this(context,null);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            colorDrawable = new ColorDrawable(Color.RED);
            Bitmap bitmap = CommonUtils.getAvatar(getContext(), R.drawable.avatar, (int) CommonUtils.dp2Px(100));
            convertedDrawable  = new BitmapDrawable(getResources(),bitmap);
            meshDrawable = new MeshDrawable();

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        colorDrawable.setBounds(100,100,getWidth(),getHeight());
//        colorDrawable.draw(canvas);
//        convertedDrawable.setBounds(100,100,getWidth(),getHeight());
//        convertedDrawable.draw(canvas);
        meshDrawable.setBounds(100,100,getWidth(),getHeight());
        meshDrawable.draw(canvas);

    }
}
