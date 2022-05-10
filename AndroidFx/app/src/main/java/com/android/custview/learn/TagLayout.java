package com.android.custview.learn;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TagLayout extends ViewGroup {

    private List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int lineMaxHeight = 0;
        int lineWidthUsed = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量子view
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            //如果当前行最后的宽度不能放置下子view，换行
            if (specMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > specWidth) {
                //当前行使用的宽度重置
                lineWidthUsed = 0;
                //当前行使用的高度设置为之前使用的高度加上当前字view的高度
                heightUsed += lineMaxHeight;
//                lineMaxHeight = 0;
                //重新测量子view
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }
            //保存测量好的子view的上下左右
            childBound.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            //累加一行使用的宽度
            lineWidthUsed += child.getMeasuredWidth();
            //计算一行最大宽度
            widthUsed = Math.max(widthUsed,lineWidthUsed);
            //计算一行最大高度
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());

//            int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//            int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
//            int childWidthMode;
//            int childWidthSize;
//            LayoutParams layoutParams = child.getLayoutParams();
//            switch (layoutParams.width) {
//                case LayoutParams.MATCH_PARENT:
//                    switch (specWidthMode) {
//                        case MeasureSpec.EXACTLY:
//                        case MeasureSpec.AT_MOST:
//                            childWidthMode = MeasureSpec.EXACTLY;
//                            childWidthSize = specWidthSize - usedWidth;
//                            int childSpec = MeasureSpec.makeMeasureSpec(childWidthSize, childWidthMode);
//                            break;
//                        case MeasureSpec.UNSPECIFIED:
//                            childWidthMode = MeasureSpec.UNSPECIFIED;
//                            childWidthSize = 0;
//                            break;
//
//                    }
//                    break;
//
//            }

        }
        int width = widthUsed;
        int height = heightUsed + lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBounds = childrenBounds.get(i);
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }
}
