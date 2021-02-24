package com.android.custview.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.custview.BuildConfig;
import com.android.custview.R;
import com.android.custview.fgstack.FragmentStackActivity;
import com.android.custview.inf.AnyCallback;
import com.android.custview.jetpack.UserDatabase;
import com.android.custview.jetpack.activity.JetPackMainActivity;
import com.android.custview.jetpack.bean.ItemBean;
import com.android.custview.jetpack.bean.ItemDao;
import com.android.custview.live.LiveMainActivity;
import com.android.custview.project.TestCase;
import com.android.zp.base.KLog;
import com.android.custview.adapter.MainAdapter;
import com.android.custview.bean.Person;
import com.android.custview.view.InitApplication;
import com.android.custview.widget.SpacesItemDecoration;
import com.android.zp.base.BaseActivity;
import com.android.zp.base.LR;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;

public class MainActivity extends BaseActivity {

    private RecyclerView mRv;
    private MainAdapter mMainAdapter;

    private String[] items = {"自定义View1", "进度条变色", "自定义音量条", "自定义ViewGroup", "自定义拖拽"
            , "ListView侧滑", "自定义跑马灯", "卡片框架", "自定义上滑", "JetPacket系列", "通知测试", "GLSurfaceView使用"
            , "Excel解析", "RecycleView案例", "LargeImageView展示", "插件主界面", "换肤", "Fragment任务栈"
            , "直播主页"};

    private boolean autoScroll = false;

    @Override
    public int getLayout() {
        return LR.layout.activity_main;
    }

    @Override
    public void initView() {
        mRv = findViewById(R.id.rv);
    }

    @Override
    public boolean showActionBar() {
        return !TextUtils.equals(BuildConfig.appName, "CommonTest");
    }

    @SuppressLint("SetWorldReadable")
    @Override
    public void initData() {
        setAnyCallBack(new AnyCallback() {
            @Override
            public void onCallObject(Object obj) {

            }

            @Override
            public void onCallBundle(Bundle bundle) {

            }

            @Override
            public void onCallString(String str) {
                super.onCallString(str);
                KLog.logI("onCallString: " + str);
            }
        });
        String path = getExternalFilesDir(null).getPath() + "/1607996925081.wav";
        KLog.logI(path);
        try {
            getDuration(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createFile();
        readFile();
        getLifecycle().addObserver(new MyObserver());
        mMainAdapter = new MainAdapter();
//        String[] items = getResources().getStringArray(R.array.main_items);
        mMainAdapter.setData(items);
        mMainAdapter.setOnClickListener(new MainAdapter.OnClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent();
                KLog.logI("点击了 " + pos);
                switch (pos) {
                    case 0:
                        TestCase.getInstance().sendAccStatus(true);
                        intent.setClass(MainActivity.this, CustView01Activity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, ProgressBarActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, CustomVolumActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, CustViewGroupActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, DraggerActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, ListActivity.class);
                        Person person = new Person();
                        person.age = 20;
                        person.money = 5000.00;
                        person.name = "Tom";
                        intent.putExtra("type", "person");
                        intent.putExtra("person", person);
                        break;
                    case 6:
                        intent.setClass(MainActivity.this, MarqueeActivity.class);
                        break;
                    case 7:
                        intent.setClass(MainActivity.this, CardViewActivity.class);
                        break;
                    case 8:
                        intent.setClass(MainActivity.this, ScrollActivity.class);
                        break;
                    case 9:
                        intent.setClass(MainActivity.this, JetPackMainActivity.class);
                        break;
                    case 10:
                        intent.setClass(MainActivity.this, NotificationActivity.class);
                        break;
                    case 11:
                        intent.setClass(MainActivity.this, GLSurfaceViewActivity.class);
                        break;
                    case 12:
                        intent.setClass(MainActivity.this, ExcelActivity.class);
                        break;
                    case 13:
                        intent.setClass(MainActivity.this, RecycleViewActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        break;
                    case 14:
                        intent.setClass(MainActivity.this, LargeImageViewActivity.class);
                        break;
                    case 15:
                        intent.setClass(MainActivity.this, PluginMainActivity.class);
                        break;
                    case 16:
                        intent.setClass(MainActivity.this, ChangeSkinActivity.class);
                        break;
                    case 17:
                        intent.setClass(MainActivity.this, FragmentStackActivity.class);
                        break;
                    case 18:
                        intent.setClass(MainActivity.this, LiveMainActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
        if (autoScroll) {
            mRv.addItemDecoration(new SpacesItemDecoration(20));
            mRv.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRv.setLayoutManager(new MyLayoutManager(this));
        }
        mRv.setAdapter(mMainAdapter);
        OneTimeWorkRequest worker = new OneTimeWorkRequest.Builder(ListWorker.class).build();
        WorkManager.getInstance(this).beginWith(worker).enqueue();
        KLog.logI(formatTbt(jsonStr));
        KLog.logI("Has location permission : " + hasLocationPermission(this));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    KLog.logI("onScrollStateChanged");
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        if (autoScroll) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mRv.getLayoutManager() instanceof LinearLayoutManager) {
                                ((LinearLayoutManager) (mRv.getLayoutManager())).smoothScrollToPosition(mRv, null, items.length - 1);
                            }
                        }
                    }, 500);
                }
            }).start();
        }
    }

    private static class MyLayoutManager extends FlexboxLayoutManager {
        @Override
        public boolean canScrollVertically() {
            return true;
        }

        MyLayoutManager(Context context) {
            this(context, FlexDirection.ROW);
        }

        MyLayoutManager(Context context, int flexDirection) {
            this(context, flexDirection, FlexWrap.WRAP);
        }

        MyLayoutManager(Context context, int flexDirection, int flexWrap) {
            super(context, flexDirection, flexWrap);
        }
    }

    File dataFile = new File("/sdcard/pateo_nav_license2.txt");

    private void createFile() {

        KLog.logI("dataFile exist " + dataFile.exists());
        try {
            if (!dataFile.exists()) {
                boolean ret = dataFile.createNewFile();
                if (ret) {
                    dataFile.setReadable(true, false);
          /*      dataFile.setWritable(true, false);
                dataFile.setExecutable(true,false);*/
                }
            } else {
                boolean ret = dataFile.delete();
                if (!ret) {
                    return;
                }
                ret = dataFile.createNewFile();
                if (!ret) {
                    return;
                }
                dataFile.setReadable(true, false);
              /*  dataFile.setWritable(true, false);
                dataFile.setExecutable(true,false);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        mAnyCallback.onCallString(dataFile.getAbsolutePath());
        KLog.logI("dataFile exist " + dataFile.exists());
        if (!dataFile.exists()) {
            return;
        }
        BufferedReader buffReader = null;
        InputStream is = null;
        try {
            is = new FileInputStream(dataFile);
            buffReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = buffReader.readLine()) != null) {
                if (!TextUtils.isEmpty(line)) {
                    KLog.logI(line);
                }
            }
        } catch (Exception e) {
            KLog.logE(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (buffReader != null) {
                    buffReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            int width = mRv.getMeasuredWidth();
            int height = mRv.getMeasuredHeight();
            KLog.logI("Width: " + width + "   Height: " + height);
        }
    }

    public class MyObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume() {
            KLog.logI("MyObserver onResume");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause() {
            KLog.logI("MyObserver onPause");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KLog.logI("MainActivity onResume");
    }

    public static class ListWorker extends Worker {

        public ListWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }

        @NonNull
        @Override
        public Result doWork() {
            ItemDao itemDao = UserDatabase.getInstance(InitApplication.getContext()).getItemDao();
            itemDao.deleteAll();
            List<ItemBean> list = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                list.add(new ItemBean("哈哈哈" + i, i % 9));
            }
            itemDao.insert(list);

            return Result.success();
        }
    }

    public void getDuration(String audioPath) throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(audioPath);
        mediaPlayer.prepare();
        long duration = mediaPlayer.getDuration(); //时长
        KLog.logI("duration: " + duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(minutes);
    }

    String jsonStr = "{\n" +
            "\t\"nextTurnbyturn\": 1,\n" +
            "\t\"nextroadName\": \"Jalan tanpa nama\",\n" +
            "\t\"nextDistance\": \"1.8 kM\",\n" +
            "\t\"naviFinish\": \"START\"\n" +
            "}";

    private String formatTbt(String source) {
        try {
            JSONObject jo = new JSONObject(source);
            String nextDistance = jo.optString("nextDistance");
            String formatNextDistance = formatDistance(nextDistance);
            KLog.logI("formatNextDistance: " + formatNextDistance);
            jo.put("nextDistance", formatNextDistance);
            return jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String formatDistance(String sourceDistance) {
        if (TextUtils.isEmpty(sourceDistance)) {
            return "-1";
        }
        String[] arr = sourceDistance.split(" ");
        if (arr.length == 2) {
            String value0 = arr[0];
            String value1 = arr[1];
            if ("m".equalsIgnoreCase(value1) || "米".equals(value1)) {
                return value0;
            } else if ("km".equalsIgnoreCase(value1) || "公里".equals(value1)) {
                return (int) (Float.parseFloat(value0) * 1000) + "";
            }
        }
        return "-1";
    }

    public boolean hasLocationPermission(Context context) {
        int hasPermission = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (hasPermission == PackageManager.PERMISSION_GRANTED);
    }

    private AnyCallback mAnyCallback;

    public void setAnyCallBack(AnyCallback anyCallBack) {
        mAnyCallback = anyCallBack;
    }
}
