package com.android.custview.learn;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class CameraView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Camera camera = new Camera();
    public CameraView(Context context) {
        this(context,null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            camera.rotateX(45);
            camera.setLocation(0,0,CommonUtils.getZForCamera());
//            camera.setLocation(0,0,-12);//-8 = -8 * 72
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.clipRect(0,150 + 600 / 2,getWidth(),getHeight());
        //绘制图片上半部分
        canvas.save();
        canvas.translate(150 + 600 / 2, 150 + 600 / 2);
        canvas.rotate(-30);
        canvas.clipRect(-600 ,-600 ,600 ,0);
        canvas.rotate(30);
        canvas.translate(-150 - 600 / 2, -150 - 600 / 2);
        canvas.drawBitmap(CommonUtils.getAvatar(getContext(), R.drawable.avatar,600),150,150,paint);
        canvas.restore();



        //这里倒叙执行
        //绘制图片下半部分
        canvas.translate(150 + 600 / 2, 150 + 600 / 2);
        canvas.rotate(-30);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-600,0,600 ,600);
        canvas.rotate(30);
        canvas.translate(-150 - 600 / 2, -150 - 600 / 2);
        canvas.drawBitmap(CommonUtils.getAvatar(getContext(), R.drawable.avatar,600),150,150,paint);





    }

}
