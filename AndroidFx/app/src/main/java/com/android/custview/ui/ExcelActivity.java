package com.android.custview.ui;


import android.util.Log;
import android.view.View;

import com.android.custview.R;
import com.android.custview.utils.ExcelUtils;
import com.android.custview.utils.KLog;

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

    private String excelName = "POI_catgory_list_edited.xlsx";
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
