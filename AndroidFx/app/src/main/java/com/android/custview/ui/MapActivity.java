package com.android.custview.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.custview.R;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.KLog;

import androidx.annotation.NonNull;

public class MapActivity extends BaseActivity {

    private WebView mWebView;

    private static final int MSG_SHOW_MARKER = 1;

    private final Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_SHOW_MARKER:
                    showMarker(101.34286972590898, 0.44669692868046623);
                    break;
            }
        }
    };

    private void showMarker(double lng, double lat) {
        mWebView.loadUrl("javascript:showMarker(" + lng + "," + lat + ")");
    }

    @Override
    public int getLayout() {
        return R.layout.activity_map;
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void initView() {
        mWebView = findViewById(R.id.web);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setBlockNetworkImage(false);//显示网络图像
        webSettings.setBlockNetworkLoads(false);
        webSettings.setLoadsImagesAutomatically(true);//显示网络图像
        webSettings.setSupportZoom(false);//设置是否支持变焦
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        String dir = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.setScrollbarFadingEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                // 这里是处理是否同意定位权限，可以在这里写一个 AlertDialog 来模仿浏览器弹出来的定位权限申请。
                callback.invoke(origin, true, false);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                KLog.logI("onProgressChanged: " + newProgress);
            }

            public boolean onConsoleMessage(ConsoleMessage cm) {
                KLog.logI(cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                KLog.logI("onPageFinished");
                Message message = Message.obtain();
                message.what = MSG_SHOW_MARKER;
                mMainHandler.sendMessageDelayed(message, 1000);

            }
        });
        mWebView.addJavascriptInterface(new MapInterface(), "mapInterface");
        mWebView.loadUrl("file:android_asset/MapWeb.html");
    }

    public static class MapInterface {
        @JavascriptInterface
        public void javaMethod(String p) {
            KLog.logI("JSHook.JavaMethod() called! + " + p);
        }

        @JavascriptInterface
        public void showAndroid() {
            KLog.logI("showAndroid");
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }

}