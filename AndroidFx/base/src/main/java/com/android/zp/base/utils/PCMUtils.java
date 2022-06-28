package com.android.zp.base.utils;

import android.content.Context;

import com.android.zp.base.KLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * PCM 音频工具类
 */
public class PCMUtils {

    /**
     * 音频增益
     * @param context
     */
    public static void gainPcm(Context context) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            String pcmSourcePath = context.getExternalCacheDir().getPath() + "/1.pcm";
            String pcmOutPath = context.getExternalCacheDir().getPath() + "/2.pcm";
            KLog.logI("pcmSourcePath : " + pcmSourcePath);
            File file = new File(pcmSourcePath);
            if (!file.exists()) {
                KLog.logE("file not exist");
                return;
            }
            File outFile = new File(pcmOutPath);
            if (outFile.exists()) {
                boolean ret = outFile.delete();
                KLog.logI("delete file : " + ret);
            }
            boolean ret = outFile.createNewFile();
            KLog.logI("create file : " + ret);

            fis = new FileInputStream(file);
            fos = new FileOutputStream(outFile);
            byte[] bytes = new byte[1600];
            while (fis.read(bytes) != -1) {
                short[] in = toShortArray(bytes, bytes.length);
                applyFactor(in, in.length, 80, 10);
                byte[] out = shortToBytes(in, in.length);
                fos.write(out);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static short SHORT_MAX = (short) 0x7F00;
    private static short SHORT_MIN = (short) -0x7F00;

    private static void applyFactor(short[] pcmData, int length, int numerator, int denominator) {
        int adjustValue = 0;
        for (int i = 0; i < length; i++) {
            adjustValue = ((int) pcmData[i] * numerator) / denominator;
            if (adjustValue < SHORT_MIN) {
                adjustValue = SHORT_MIN;
            }
            if (adjustValue > SHORT_MAX) {
                adjustValue = SHORT_MAX;
            }
            pcmData[i] = (short) adjustValue;
        }
    }

    public static short[] toShortArray(byte[] src, int length) {
        int count = length >> 1;
        short[] dest = new short[count];
        for (int i = 0; i < count; i++) {
            dest[i] = (short) (src[2 * i] & 0xff | src[i * 2 + 1] << 8);
        }
        return dest;
    }

    private static byte[] shortToBytes(short[] shorts, int i) {
        if (shorts == null) {
            return null;
        }
        byte[] bytes = new byte[shorts.length * 2];
        ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(shorts);

        byte[] retByte = new byte[i * 2];
        System.arraycopy(bytes, 0, retByte, 0, i * 2);
        return retByte;
    }
}
