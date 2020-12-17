package com.android.custview.ui;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;

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

import com.android.custview.R;
import com.android.custview.jetpack.UserDatabase;
import com.android.custview.jetpack.activity.JetPackMainActivity;
import com.android.custview.jetpack.bean.ItemBean;
import com.android.custview.jetpack.bean.ItemDao;
import com.android.custview.utils.KLog;
import com.android.custview.adapter.MainAdapter;
import com.android.custview.bean.Person;
import com.android.custview.view.InitApplication;
import com.android.custview.widget.SpacesItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private RecyclerView mRv;
    private MainAdapter mMainAdapter;

//    private String[] items = {"自定义View1", "进度条变色", "自定义音量条", "自定义ViewGroup", "自定义拖拽"
//            , "ListView侧滑", "自定义跑马灯", "卡片框架","自定义上滑","JetPacket系列"};


    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mRv = findViewById(R.id.rv);
    }

    @Override
    public boolean showActionBar() {
        return true;
    }

    @Override
    public void initData() {
        String path =  getExternalFilesDir(null).getPath()+"/1607996925081.wav";
        KLog.logI(path);
        try {
            getDuration(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLifecycle().addObserver(new MyObserver());
        mMainAdapter = new MainAdapter(this);
        String[] items = getResources().getStringArray(R.array.main_items);
        mMainAdapter.setData(items);
        mMainAdapter.setOnClickListener(new MainAdapter.OnClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent();
                KLog.logI("点击了 " + pos);
                switch (pos) {
                    case 0:
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
                        intent.setClass(MainActivity.this,RecycleViewActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });
        mRv.addItemDecoration(new SpacesItemDecoration(20));
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mMainAdapter);
        OneTimeWorkRequest worker = new OneTimeWorkRequest.Builder(ListWorker.class).build();
        WorkManager.getInstance(this).beginWith(worker).enqueue();

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
}
