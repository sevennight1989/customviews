package com.android.custview.jetpack;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.zp.base.KLog;

public class WorkerToYou extends Worker {
    public WorkerToYou(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String str = this.getInputData().getString("demo");
        KLog.logI("doWork: " + str);
        Data outputData = new Data.Builder()
                .putString("out", "WorkerToYou")
                .build();
        return Result.success(outputData);
    }
}
