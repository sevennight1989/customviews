package com.android.custview.learn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.utils.CommonUtils;

import androidx.annotation.Nullable;

public class ImageTextView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private TextPaint textPaint = new TextPaint();
    private StaticLayout staticLayout;

    private Bitmap bitmap;
    private boolean usCommon = false;

    private float[] cutWidth = new float[1];

    private static final String TEXT =
            "AudioRecoder 采集的 PCM 音频放入音频队列中，子线程音频编码循环不断从队列中取数据进行编码，最后将编码数据写入媒体文件。FFmpeg 两种采样格式由于新的 FFmpeg 版本不再支持对 AV_SAMPLE_FMTAudioRecoder 采集的 PCM "
                    + "音频放入音频队列中，子线程音频编码循环不断从队列中取数据进行编码，最后将编码数据写入媒体文件。FFmpeg 两种采样格式由于新的 FFmpeg 版本不再支持对 AV_SAMPLE_FMT_S16 采样格式的音频数据进行编码，需要利用swr_convert 将格式转换为 AV_SAMPLE_FMT_FLTP ，而 AV_SAMPLE_FMT_S16"
                    + " 就是 AudioRecorder 的 AudioFormat.ENCODING_PCM_16BITAudioRecoder 采集的 PCM 音频放入音频队列中，子线程音频编码循环不断从队列中取数据进行编码，最后将编码数据写入媒体文件。FFmpeg 两种采样格式由于新的 FFmpeg 版本不再支持对 AV_SAMPLE_FMT";

    public ImageTextView(Context context) {
        this(context, null);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        {
            bitmap = getAvatar((int) CommonUtils.dp2Px(80));
            textPaint.setTextSize(CommonUtils.dp2Px(20));
            paint.setTextSize(CommonUtils.dp2Px(20));

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        staticLayout = new StaticLayout(TEXT, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (usCommon) {
            staticLayout.draw(canvas);
        } else {
            canvas.drawBitmap(bitmap, getWidth() - CommonUtils.dp2Px(80), 100, paint);
            int index = paint.breakText(TEXT, true, getWidth(), cutWidth);
            canvas.drawText(TEXT, 0, index, 0, 50, paint);
            int oldIndex = index;

            index = paint.breakText(TEXT, oldIndex, TEXT.length(), true, getWidth() - CommonUtils.dp2Px(80), cutWidth);
            canvas.drawText(TEXT, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing(), paint);
            oldIndex += index;

            index = paint.breakText(TEXT, oldIndex, TEXT.length(), true, getWidth() - CommonUtils.dp2Px(80), cutWidth);
            canvas.drawText(TEXT, oldIndex, oldIndex + index, 0, 50 + paint.getFontSpacing() * 2, paint);
        }

    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar, options);
    }
}
