package com.android.custview.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.LocaleList;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.custview.R;
import com.com.android.custview.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

public class LanguageTestActivity extends BaseActivity implements View.OnClickListener {

    private TextView display_text;
    private Handler mWorkHandler;
    private Button switch_cn;
    @Override
    public int getLayout() {
        return R.layout.activity_lauguage;
    }

    @Override
    public void initView() {
        display_text = findViewById(R.id.display_text);
        switch_cn = findViewById(R.id.switch_cn);
        switch_cn.setOnClickListener(this);
        hook(this,switch_cn);
    }

    @Override
    public void initData() {
        HandlerThread handlerThread = new HandlerThread("HandleThread1");
        handlerThread.start();
        mWorkHandler = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                KLog.logI("handleMessage : " + msg.what +"   " + msg.arg1 + " " + Thread.currentThread().getName());
            }
        };
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_en:
                switchEn();
                updateLocationString();
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = 10;
                mWorkHandler.sendMessage(msg);
                break;

            case R.id.switch_cn:
                switchCn();
                updateLocationString();
                new LrTask().execute("Tom","Peter");
                break;

            case R.id.toMain:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void updateLocationString(){
        display_text.setText(getResources().getString(R.string.display_text));
    }

    private void switchEn() {
        switchLocate(Locale.ENGLISH);
    }

    private void switchCn() {
        switchLocate(Locale.SIMPLIFIED_CHINESE);
    }

    private void switchLocate(Locale locale) {
        KLog.logI("switchLocate:" + locale.getDisplayName());
        Resources resources = getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(doAttachBaseContext(newBase));
    }

    public Context doAttachBaseContext(@NonNull final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, Locale.ENGLISH);
        } else {
            return context;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewTreeObserver observer = display_text.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                display_text.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = display_text.getMeasuredWidth();
                int height = display_text.getMeasuredHeight();
                KLog.logI("Width: " + width + "   Height: " + height);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResources(Context context, Locale locale) {
        if (null == locale) {
            return context;
        }
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLocales(new LocaleList(locale));
        KLog.logI("updateResources ");
        return context.createConfigurationContext(configuration);
    }


    private class LrTask extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            int len = strings.length;
            for(int i = 0; i < len;i ++){
                KLog.logI("doInBackground param: " + strings[i] + "   " + Thread.currentThread().getName());
                publishProgress(i);
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            KLog.logI("onPreExecute "+ "   " + Thread.currentThread().getName());
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            KLog.logI("onPostExecute: " + aBoolean+ "   " + Thread.currentThread().getName());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int len = values.length;
            for(int i = 0 ; i < len;i ++){
                KLog.logI("onProgressUpdate: " + values[i] + "   " + Thread.currentThread().getName());
            }
        }

        @Override
        protected void onCancelled(Boolean aBoolean) {
            KLog.logI("onCancelled: " + aBoolean);
        }

        @Override
        protected void onCancelled() {
            KLog.logI("onCancelled");
        }
    }

    @SuppressLint({"DiscouragedPrivateApi", "PrivateApi"})
    public static void hook(Context context,final View view){
        try {
            Method method = View.class.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object mListenerInfo = method.invoke(view);
            Class<?> listenerInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field field = listenerInfoClz.getDeclaredField("mOnClickListener");
            final View.OnClickListener onClickListenerInstance = (View.OnClickListener) field.get(mListenerInfo);

            Object proxyOnClickListener = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    KLog.logI("动态代理 点击事件被hook 到了");
                    return method.invoke(onClickListenerInstance,args);
                }
            });
            field.set(mListenerInfo,proxyOnClickListener);

//            field.set(mListenerInfo,new ProxyOnClickListener(onClickListenerInstance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private static class ProxyOnClickListener implements View.OnClickListener {

        View.OnClickListener oriLis;

        public ProxyOnClickListener(View.OnClickListener oriLis) {
            this.oriLis = oriLis;
        }

        @Override
        public void onClick(View v) {
            KLog.logI("静态代理 ProxyOnClickListener 点击事件被hook 到了);");
            if (oriLis != null) {
                oriLis.onClick(v);
            }
        }
    }

}
