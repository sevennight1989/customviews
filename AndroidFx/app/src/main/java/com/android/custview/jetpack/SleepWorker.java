//package com.android.custview.jetpack;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//
//import androidx.annotation.NonNull;
//import androidx.work.ListenableWorker;
//import androidx.work.WorkerParameters;
//
//import com.google.common.util.concurrent.ListenableFuture;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Executor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//
//public class SleepWorker extends ListenableWorker {
//    private final ResolvableFuture<Result> mResult;
//    private final Handler mHandler;
//    private final Object mLock;
//    private Runnable mRunnable;
//    public SleepWorker(
//            @NonNull Context context,
//            @NonNull WorkerParameters workerParameters) {
//        super(context, workerParameters);
//        mHandler = new Handler(Looper.getMainLooper());
//        mResult = new ResolvableFuture<>();
//        mLock = new Object();
//    }
//
//    @NonNull
//    @Override
//    public ListenableFuture<Result> startWork() {
//        mRunnable = new Runnable() {
//            @Override
//            public void run() {
//                synchronized (mLock) {
//                    mResult.set(Result.success());
//                }
//            }
//        };
//
//        mHandler.postDelayed(mRunnable, 1000L);
//        return mResult;
//    }
//
//    @Override
//    public void onStopped() {
//        super.onStopped();
//        if (mRunnable != null) {
//            mHandler.removeCallbacks(mRunnable);
//        }
//        synchronized (mLock) {
//            if (!mResult.isDone()) {
//                mResult.set(Result.failure());
//            }
//        }
//    }
//
//    private static class ResolvableFuture<Result> implements ListenableFuture{
//
//        @Override
//        public void addListener(Runnable listener, Executor executor) {
//
//        }
//
//        @Override
//        public boolean cancel(boolean mayInterruptIfRunning) {
//            return false;
//        }
//
//        @Override
//        public boolean isCancelled() {
//            return false;
//        }
//
//        @Override
//        public boolean isDone() {
//            return false;
//        }
//
//        @Override
//        public Object get() throws ExecutionException, InterruptedException {
//            return null;
//        }
//
//        @Override
//        public Object get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
//            return null;
//        }
//    }
//}
