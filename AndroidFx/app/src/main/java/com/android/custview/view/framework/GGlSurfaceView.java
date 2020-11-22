package com.android.custview.view.framework;

import android.content.Context;
import android.util.AttributeSet;

import com.android.custview.view.MyGLSurfaceView;
import com.android.custview.view.render.MyRender;

public class GGlSurfaceView extends MyGLSurfaceView {
    public GGlSurfaceView(Context context) {
        this(context,null);
    }

    public GGlSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GGlSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyRender(new MyRender());
    }
}
