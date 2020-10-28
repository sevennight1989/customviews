package com.android.custview.ui;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.android.custview.R;

public class NotificationActivity extends BaseActivity {

    private static final String URL_1 = "www.qq.com";
    private static final String URL_2 = "www.baidu.com";

    @Override
    public int getLayout() {
        return R.layout.activity_notification;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_qq:
                createLinkNotification(URL_1, "QQ");
                break;

            case R.id.send_baidu:
                createLinkNotification(URL_2, "Baidu");
                break;
        }
    }

    private void createLinkNotification(String url, String content) {
        Intent intent = new Intent(this, NotificationReceiveActivity.class);
        intent.putExtra("url", url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) (Math.random() * 100), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String channelId = createLinkNotificationChannel(NotificationManager.IMPORTANCE_HIGH);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("解析成功")
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            int notifyId = (int) System.currentTimeMillis();
            manager.notify(notifyId, notification.build());
        }
    }

    private String createLinkNotificationChannel(int level) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ONE_ID = getPackageName();
            String CHANNEL_ONE_NAME = "Channel One";
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, level);
            manager.createNotificationChannel(channel);
            return CHANNEL_ONE_ID;
        } else {
            return null;
        }
    }
}
