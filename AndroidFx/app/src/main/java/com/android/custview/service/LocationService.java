package com.android.custview.service;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import com.android.custview.R;
import com.android.custview.ui.MainActivity;
import com.android.zp.base.KLog;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationService extends Service implements LocationListener {
    private static final String TAG = "LocationService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.logI("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.logI("onStartCommand");
        String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
        String CHANNEL_ONE_NAME = "CHANNEL_ONE_NAME";
        NotificationChannel notificationChannel;
//进行8.0的判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
            Intent newIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, newIntent, 0);
            Notification notification = new Notification.Builder(this, CHANNEL_ONE_ID).setChannelId(CHANNEL_ONE_ID)
                    .setTicker("Nature")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("这是一个测试标题")
                    .setContentIntent(pendingIntent)
                    .setContentText("这是一个测试内容")
                    .build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            startForeground(1, notification);
            startLocation();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public static void startLocationService(Context context) {
        KLog.logI("startLocationService");
        Intent intent = new Intent(context, LocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        }
    }

    public static void stopLocationService(Context context) {
        KLog.logI("stopLocationService");
        Intent intent = new Intent(context, LocationService.class);
        context.stopService(intent);
    }

    private LocationManager mLocationManager;
    private final GSVCallback gsvCallback = new GSVCallback();

    private void startLocation() {
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            KLog.logE("get location permission failed");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        mLocationManager.registerGnssStatusCallback(gsvCallback);
        KLog.logI("startLocation success");
    }

    private static final class GSVCallback extends GnssStatus.Callback {
        @Override
        public void onStarted() {
            super.onStarted();
            KLog.logI("GSVCallback onStarted");
        }

        @Override
        public void onStopped() {
            super.onStopped();
            KLog.logI("GSVCallback onStopped");
        }

        @Override
        public void onFirstFix(int ttffMillis) {
            super.onFirstFix(ttffMillis);
            KLog.logI("GSVCallback onFirstFix");
        }

        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {
            super.onSatelliteStatusChanged(status);
            KLog.logI("GSVCallback onSatelliteStatusChanged");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
        mLocationManager.unregisterGnssStatusCallback(gsvCallback);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null || (location.getLatitude() == 0 && location.getLongitude() == 0)) {
            KLog.logI("onLocationChanged: location=null or lat=0 or lon=0");
            return;
        }
        KLog.logI("onLocationChanged: location lat=" + location.getLatitude()
                + ", lon = " + location.getLongitude()
                + ", acc = " + location.getAccuracy()
                + ", bear = " + location.getBearing()
                + ", s = " + location.getSpeed()
                + ", t = " + location.getTime()
        );
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
