package com.android.custview.live;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.custview.R;
import com.blankj.utilcode.util.SizeUtils;

public class BulletRecyclerView extends RecyclerView {

    private int maxHeight;

    public BulletRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public BulletRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BulletRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BulletRecyclerView);
        maxHeight = typedArray.getDimensionPixelSize(R.styleable.BulletRecyclerView_maxHeight, SizeUtils.dp2px(200));
        EdgeEffectFactory edgeEffectFactory = new EdgeEffectFactory(){
            @NonNull
            @Override
            protected EdgeEffect createEdgeEffect(@NonNull RecyclerView view, int direction) {
                return new EdgeEffect(context){
                    @Override
                    public boolean draw(Canvas canvas) {
                        return false;
                    }
                };
            }
        };
        setEdgeEffectFactory(edgeEffectFactory);
        typedArray.recycle();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return 0f;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightMeasureSpec);
    }
}
