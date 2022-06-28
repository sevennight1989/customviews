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


    private float topFlip = 0;

    private float bottomFlip = 0;

    private float flipRotation = 0;

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    private static final float PADDING = CommonUtils.dp2Px(100);
    private static final float IMAGE_WIDTH = CommonUtils.dp2Px(200);
    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {

            camera.setLocation(0, 0, CommonUtils.getZForCamera());
//            camera.setLocation(0,0,-12);//-8 = -8 * 72
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.clipRect(0,PADDING + IMAGE_WIDTH / 2,getWidth(),getHeight());
        //绘制图片上半部分
        canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-PADDING - IMAGE_WIDTH / 2, -PADDING - IMAGE_WIDTH / 2);
        canvas.drawBitmap(CommonUtils.getAvatar(getContext(), R.drawable.avatar, (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();

        //这里倒叙执行
        //绘制图片下半部分
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(-IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(-PADDING - IMAGE_WIDTH / 2, -PADDING - IMAGE_WIDTH / 2);
        canvas.drawBitmap(CommonUtils.getAvatar(getContext(), R.drawable.avatar, (int) IMAGE_WIDTH), PADDING, PADDING, paint);

    }

}
