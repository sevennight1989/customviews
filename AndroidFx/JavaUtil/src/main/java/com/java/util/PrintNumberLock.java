package com.java.util;


import com.android.zp.base.KLog;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNumberLock {
    private ReentrantLock lock = new ReentrantLock();
    private Condition even = lock.newCondition();
    private Condition odd = lock.newCondition();
    private boolean runOdd = true;
    private static boolean isStart = false;

    public static void startPrintNumberLock() {
        stopPrintNumberLock();
        isStart = true;
        PrintNumberLock lock = new PrintNumberLock();
        new Thread(lock::printEven).start();
        new Thread(lock::printOdd).start();
    }

    public static void stopPrintNumberLock() {
        isStart = false;
    }

    private void printOdd() {
        while (isStart) {
            lock.lock();
            try {
                while (!runOdd) {
                    odd.await();
                }
                KLog.logI("-> 1");
                Thread.sleep(500);
                runOdd = false;
                even.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();

            }
        }

    }

    private void printEven() {
        while (isStart) {
            lock.lock();
            try {
                while (runOdd) {
                    even.await();
                }
                KLog.logI("-> 2");
                Thread.sleep(500);
                runOdd = true;
                odd.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
