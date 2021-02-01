package com.android.custview.fgstack.fg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.custview.R;
import com.android.zp.base.BaseFragment;
import com.android.zp.base.KLog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;

public class Fg04 extends BaseFragment implements View.OnClickListener {

    private Button start02;
    private Button pop;
    private Button writeButton;
    private Button readButton;
    private TextView read_text;
    private String mPath;
    private ExecutorService mCachedPool;
    private static final int MSG_SHOW = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_SHOW:
                    String showText = msg.obj.toString();
                    read_text.setText(showText);
                    break;
            }
        }
    };

    @Override
    protected void onDataReceive(Bundle bundle) {
        if (bundle != null) {
            String value = bundle.getString("key", "");
            KLog.logE("Fg04 onDataReceive: " + value);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg04, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        read_text = view.findViewById(R.id.read_text);
        start02 = view.findViewById(R.id.start02);
        pop = view.findViewById(R.id.pop);
        readButton = view.findViewById(R.id.read_file);
        writeButton = view.findViewById(R.id.write_file);
        start02.setOnClickListener(this);
        pop.setOnClickListener(this);
        readButton.setOnClickListener(this);
        writeButton.setOnClickListener(this);
        mCachedPool = Executors.newCachedThreadPool();
        mPath = getContext().getExternalFilesDir(null).getPath() + File.separator + "11.txt";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start02:
                Bundle bundle = new Bundle();
                bundle.putString("key", "update to 02");
                add(Fg02.class, bundle);
                break;

            case R.id.pop:
                finish();
                break;

            case R.id.write_file:
                mCachedPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("Name", "Tom");
                        map.put("Age", "20");
                        map.put("Province", "江苏");
                        writeFile(map);
                    }
                });
                break;

            case R.id.read_file:
                mCachedPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        readFile();
                    }
                });

                break;
        }
    }

    private void writeFile(Map<String, String> map) {
        try {
            if (!TextUtils.isEmpty(mPath)) {
                KLog.logE("write path: " + mPath);
                File file = new File(mPath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                Sink fileSink = Okio.sink(file);
                BufferedSink bufferedSink = Okio.buffer(fileSink);
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    bufferedSink.writeUtf8(entry.getKey());
                    bufferedSink.writeUtf8("=");
                    bufferedSink.writeUtf8(entry.getValue());
                    bufferedSink.writeUtf8("\n");
                }
                bufferedSink.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        File file = new File(mPath);
        if (!file.exists()) {
            KLog.logE(mPath + " not exist");
        }
        StringBuffer sb = new StringBuffer();
        try {
            BufferedSource source = Okio.buffer(Okio.source(file));
            while (!source.exhausted()) {
                String line = source.readUtf8LineStrict(1024L);
                sb.append(line);
                sb.append("\n");
                KLog.logI(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg = mHandler.obtainMessage();
        msg.what = MSG_SHOW;
        msg.obj = sb.toString();
        mHandler.sendMessageDelayed(msg, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCachedPool.shutdown();
    }
}
