package com.android.zp.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.android.zp.base.KLog;
import com.android.zp.base.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class CommonUtils {

    public static float dp2Px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Context context, int resId, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public static float getZForCamera() {
        return -6 * Resources.getSystem().getDisplayMetrics().density;
    }

    public static List<String> getProvinces(Context context) {
        List<String> list = new ArrayList<>();
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = context.getAssets().open("city.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferReader = new BufferedReader(inputStreamReader)) {
            String jsonLine;
            while ((jsonLine = bufferReader.readLine()) != null) {
                stringBuilder.append(jsonLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } /*finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (bufferReader != null) {
                    bufferReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        try {
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String label = jsonObject.optString("label");
                list.add(label);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        KLog.logI("getProvinces = " + list);
        return list;
    }

    private void nio(String filePath) {
        FileChannel channel;
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            channel = file.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer);

//            byteBuffer.limit(byteBuffer.position());
//            byteBuffer.position(0);
            byteBuffer.flip();
            Charset.defaultCharset().decode(byteBuffer);
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
