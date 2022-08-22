package com.android.custview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.android.custview.R;
import com.android.zp.base.KLog;

import java.util.List;

public class TagTextView extends AppCompatTextView {

    private Context mContext;

    public TagTextView(@NonNull Context context) {
        this(context, null);
        KLog.logE("TagTextView Construct init 333");
    }

    public TagTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        KLog.logE("TagTextView Construct init 222");
    }

    public TagTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        KLog.logE("TagTextView Construct init 111");
        mContext = context;
    }

    {
        KLog.logE("TagTextView Construct init 000");
    }

    public void setTagAndText(List<String> tags, int textColor, int bgColor, String text) {
        setTagAndText(tags, textColor, bgColor, text, 0, 0, Color.BLACK);
    }

    public void setTagAndText(List<String> tags, int textColor, int bgColor, String text, int startIndex, int endIndex, int titleColor) {
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder();
        StringBuffer sb = new StringBuffer();
        for (String tag : tags) {
            sb.append(tag);
        }
        SpannableString span = new SpannableString(sb);
        for (int index = 0; index < tags.size(); index++) {
            String str = tags.get(index);
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_text_tag, null);
            TextView tv_tag = view.findViewById(R.id.tv_tag);
            tv_tag.setTextColor(textColor);
            tv_tag.setBackgroundColor(bgColor);
            tv_tag.setText(str);
            Bitmap bitmap = convertViewToBitmap(view);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            bitmapDrawable.setBounds(0, 0, tv_tag.getWidth(), tv_tag.getHeight());
            ImageSpan imageSpan = new ImageSpan(bitmapDrawable, ImageSpan.ALIGN_BOTTOM);
            int start = getLastLength(tags, index);
            int end = start + str.length();
            span.setSpan(imageSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spanBuilder.append(span);
        span = new SpannableString(text);
        if (endIndex != 0) {
            span.setSpan(new ForegroundColorSpan(titleColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        spanBuilder.append(span);
        setText(spanBuilder);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private Bitmap convertViewToBitmap(View view) {
        view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    private int getLastLength(List<String> list, int maxLength) {
        int length = 0;
        for (int i = 0; i < maxLength; i++) {
            length += list.get(i).length();
        }
        return length;
    }
}
