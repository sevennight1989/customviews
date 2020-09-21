package com.android.custview.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.android.custview.R;
import com.com.android.custview.KLog;
import com.android.custview.jetpack.WorkerToMe;
import com.android.custview.jetpack.WorkerToYou;

import java.util.ArrayList;
import java.util.List;

public class JetPackActivity extends BaseActivity {
    OneTimeWorkRequest oneTimeWorkRequest;
    OneTimeWorkRequest oneTimeWorkRequest2;

    @Override
    public int getLayout() {
        return R.layout.activity_jet_pack;
    }

    @Override
    public void initView() {

    }
    private static final String PROGRESS = "PROGRESS";
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.oneTimeWorkRequest:
                Constraints myConstraints = new Constraints.Builder()
                        //手机设备空闲的时候使用
//                        .setRequiresDeviceIdle(true)
                        //手机接通电源的时候使用
                        .setRequiresCharging(true)
                        //网络连接的时候执行任务
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        //电池电量低于临界水平不可用
//                        .setRequiresBatteryNotLow(true)
                        //可用存储不能低于临界值
//                        .setRequiresStorageNotLow(true)
                        .build();
                Data data = new Data.Builder().putString("demo", "helloworld").build();
                Data data2 = new Data.Builder().putString("demo", "helloworld2").build();
                oneTimeWorkRequest = new OneTimeWorkRequest.Builder(WorkerToYou.class)
                        .setConstraints(myConstraints)
//                        .addTag("cleanup")
                        .setInputData(data)
                        .build();
                oneTimeWorkRequest2 = new OneTimeWorkRequest.Builder(WorkerToMe.class)
                        .setConstraints(myConstraints)
//                        .addTag("cleanup")
//                        .setInitialDelay(1,TimeUnit.SECONDS)
                        .setInputData(data2)
                        .build();
                List<OneTimeWorkRequest> list = new ArrayList<>();
                list.add(oneTimeWorkRequest);
                list.add(oneTimeWorkRequest2);

                LifecycleOwner lifecycleOwner = new LifecycleOwner() {
                    @NonNull
                    @Override
                    public Lifecycle getLifecycle() {
                        return new Lifecycle() {
                            @Override
                            public void addObserver(@NonNull LifecycleObserver observer) {
                                KLog.logD("addObserver");
                            }

                            @Override
                            public void removeObserver(@NonNull LifecycleObserver observer) {
                                KLog.logD("removeObserver");
                            }

                            @NonNull
                            @Override
                            public State getCurrentState() {
                                KLog.logD("getCurrentState");
                                return State.CREATED;
                            }
                        };
                    }
                };

                WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new androidx.lifecycle.Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        String out = workInfo.getOutputData().getString("out");
                        KLog.logD("onchange " + workInfo.getState() + "  " + out);
                    }
                });

                WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest2.getId()).observe(this, new  androidx.lifecycle.Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Data progress = workInfo.getProgress();
                            int value = progress.getInt(PROGRESS, 0);
//                        String out = workInfo.getOutputData().getString("out");
                            KLog.logD("onchange  value: " + value + "  " + workInfo.getState());
                        }
                    }
                });
                WorkManager.getInstance(this).beginWith(oneTimeWorkRequest2).enqueue();
//                WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).then(oneTimeWorkRequest2).enqueue();
                break;
        }
    }

    @Override
    public void initData() {
    }
}
