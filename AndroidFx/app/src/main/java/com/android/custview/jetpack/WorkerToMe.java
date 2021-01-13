package com.android.custview.jetpack;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.zp.base.KLog;

public class WorkerToMe extends Worker {
    private static final String PROGRESS = "PROGRESS";
    private static final long DELAY = 1000L;
    public WorkerToMe(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        setProgressAsync(new Data.Builder().putInt(PROGRESS, 10).build());
    }

    @NonNull
    @Override
    public Result doWork() {
        String str = this.getInputData().getString("demo");
        KLog.logI("doWork: " + str);
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException exception) {
        }
        setProgressAsync(new Data.Builder().putInt(PROGRESS, 50).build());
//        Data outputData = new Data.Builder()
//                .putString("out", "WorkerToMe")
//                .build();
        return Result.success();
    }
}
