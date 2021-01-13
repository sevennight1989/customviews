package com.android.custview.ui;


import android.util.Log;
import android.view.View;

import com.android.custview.R;
import com.android.zp.base.QuitReset;
import com.android.custview.utils.ExcelUtils;
import com.android.zp.base.KLog;
import com.android.zp.base.BaseActivity;
import com.blankj.utilcode.util.SPUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelActivity extends BaseActivity {

    @QuitReset(value = false)
    public boolean isAlive = false;

    @QuitReset(state = 2)
    public int login_state = 0;


    private String ALIVE_KEY = "isAlive";
    private String LOGIN_KEY = "login_state";

    private String excelName = "Categories_filled_20201222.xlsx";
    private static Map<String, String> map = new HashMap<>();
    String path = "";

    @Override
    public int getLayout() {
        return R.layout.activity_excel;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        path = getExternalFilesDir(null).getPath();
        boolean t_isAlive = SPUtils.getInstance().getBoolean(ALIVE_KEY, false);
        KLog.logI("isAlive:" + t_isAlive);
        int t_login_state = SPUtils.getInstance().getInt(LOGIN_KEY, 0);
        KLog.logI("login_state:" + t_login_state);
        //模拟修改状态值
        isAlive = true;
        login_state = 1;
        SPUtils.getInstance().put(ALIVE_KEY, isAlive);
        SPUtils.getInstance().put(LOGIN_KEY, login_state);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.parse_excel:
                map.clear();

                KLog.logE("path: " + path);
                File file = new File(path + "/" + excelName);
                KLog.logE("Exist" + file.exists());
                if (file.exists()) {
                    if (ExcelUtils.checkIfExcelFile(file)) {
                        try {
                            ExcelUtils.readExcel(file, map);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                FileOutputStream fos = null;
                String dataFilePath = path + "/data.txt";
                File dataFile = new File(dataFilePath);
                try {
                    if (dataFile.exists()) {
                        dataFile.delete();
                    } else {
                        dataFile.createNewFile();
                    }
                    // Context.MODE_PRIVATE私有权限，Context.MODE_APPEND追加写入到已有内容的后面
                    fos = new FileOutputStream(dataFile);
                    for (String key : map.keySet()) {
                        String str = key + "===" + map.get(key);
                        KLog.logE(str);
                        fos.write(str.getBytes());
                        fos.write("\r\n".getBytes());//写入换行
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                readTxtFile(dataFilePath);
                break;
        }
    }

    public static String readTxtFile(String strFilePath) {
        String path = strFilePath;
        List newList = new ArrayList<String>();
        File file = new File(path);
        try {
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    newList.add(line + "\n");
                    KLog.logI(line);
                }
                instream.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return strFilePath;
    }

}
