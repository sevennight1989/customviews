package com.android.custview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static Bitmap getBitmap(Context context, int vectorDrawableId) {
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

    public static void blur(Context context, Bitmap src, float radius){
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs,src);
        Allocation output = Allocation.createTyped(rs,input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(src);
    }

    /**
     * 设置圆角
     *
     * @param srcBitmap
     * @param recycleSrc 是否回收原图
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap srcBitmap, float radius, boolean recycleSrc) {
        Bitmap output = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, srcBitmap.getWidth(), srcBitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        Canvas canvas = new Canvas(output);
        canvas.save();
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcBitmap, rect, rect, paint);
        canvas.restore();
        //回收之前的Bitmap
//        if (recycleSrc && srcBitmap != null && !srcBitmap.equals(output) && !srcBitmap.isRecycled()) {
//            GlideBitmapPool.putBitmap(srcBitmap);
//        }
        return output;
    }

    /**
     * 保存bitmap到本地
     * @param bitmap
     * @param filePath
     */
    private void saveBitmap(Bitmap bitmap, String filePath){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据指定的宽度比例值和高度比例值进行缩放
     *
     * @param srcBitmap
     * @param scaleWidth
     * @param scaleHeight
     * @param recycleSrc 是否回收Bitmap
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap srcBitmap, float scaleWidth, float scaleHeight, boolean recycleSrc) {
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
        if (bitmap != null) {
            /**回收*/
//            if (recycleSrc && srcBitmap != null && !srcBitmap.equals(bitmap) && !srcBitmap.isRecycled()) {
//                GlideBitmapPool.putBitmap(srcBitmap);
//            }
            return bitmap;
        } else {
            return srcBitmap;
        }
    }
}
