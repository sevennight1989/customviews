package com.java.util;

import com.android.zp.base.KLog;

import java.util.concurrent.Semaphore;

public class PrintNumberSemaphore {

    private static boolean isStart = false;

    private final Semaphore semaphoreOld = new Semaphore(1);

    private final Semaphore semaphoreEven = new Semaphore(0);

    private void printOdd() {
        while (isStart) {
            try {
                semaphoreOld.acquire();
                KLog.logI("1");
                Thread.sleep(500);
                semaphoreEven.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printEven() {
        while (isStart) {
            try {
                semaphoreEven.acquire();
                KLog.logI("2");
                Thread.sleep(500);
                semaphoreOld.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startPrintNumberSemaphore() {
        stopPrintNumberSemaphore();
        isStart = true;
        PrintNumberSemaphore lock = new PrintNumberSemaphore();
        new Thread(lock::printOdd).start();

        new Thread(lock::printEven).start();
    }

    public static void stopPrintNumberSemaphore() {
        isStart = false;
    }

}
