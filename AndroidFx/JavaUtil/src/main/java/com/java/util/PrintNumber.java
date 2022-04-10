package com.java.util;

import com.android.zp.base.KLog;

import androidx.annotation.NonNull;

public class PrintNumber {

    private static final int THREAD_COUNT = 2;
    private static final Object LOCK = new Object();
    private static int value = 0;
    private static boolean isStart = false;

    public static void startPrintThread() {
        stopPrintThread();
        isStart = true;
        new PrintThread("Thread-01", 0).start();
        new PrintThread("Thread-02", 1).start();
    }

    public static void stopPrintThread() {
        isStart = false;
    }

    static class PrintThread extends Thread {

        int threadNo;

        public PrintThread(@NonNull String name, int threadNo) {
            super(name);
            this.threadNo = threadNo;
        }

        @Override
        public void run() {
            while (isStart) {
                try {
                    synchronized(LOCK) {
                        while ((value % THREAD_COUNT) != threadNo) {
                            LOCK.wait();
                        }
                        Thread.sleep(1000);
                        KLog.logI(Thread.currentThread().getName() + " : " + (value % THREAD_COUNT + 1));
                        value++;
                        if (value == 10) {
                            value = 0;
                        }
                        LOCK.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
