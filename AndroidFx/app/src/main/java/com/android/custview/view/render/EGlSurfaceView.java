package com.android.custview.view.render;

import android.content.Context;
import android.util.AttributeSet;

public class EGlSurfaceView extends BaseSurfaceView {

    public EGlSurfaceView(Context context) {
        this(context, null);
    }

    public EGlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EGlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setRender(new MyRender());
        setRenderMode(BaseSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
