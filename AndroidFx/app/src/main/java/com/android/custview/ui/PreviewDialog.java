package com.android.custview.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.custview.R;
import com.android.zp.base.BaseDialog;

import java.io.IOException;

import androidx.annotation.NonNull;

public class PreviewDialog extends BaseDialog implements View.OnClickListener {

    public static final String TAG = "PreviewDialog";
    public PreviewDialog(@NonNull Context context) {
        super(context, R.style.bottomDialogStyle);
    }
    public Camera mCamera;
    private TextureView textureView;
    @Override
    public void dismiss() {
        super.dismiss();
        Log.i(TAG,"dismiss");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG,"onAttachedToWindow");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @NonNull
    @Override
    public Bundle onSaveInstanceState() {
        Log.i(TAG,"onSaveInstanceState");
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.i(TAG,"onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.i(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    public void cancel() {
        super.cancel();
        Log.i(TAG,"cancel");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(TAG,"onWindowFocusChanged: " + hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.preview_layout, null, false);
        setContentView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textureView = contentView.findViewById(R.id.textureVIew);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    mCamera = Camera.open(0);
                    mCamera.setDisplayOrientation(90);
                    mCamera.setPreviewTexture(surface);
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
