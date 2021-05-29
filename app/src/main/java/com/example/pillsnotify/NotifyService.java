package com.example.pillsnotify;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.HashMap;
import java.util.LinkedList;

public class NotifyService extends Service {
    private int NOTIFY_ID = 101;
    long time = 1000;
    private static String CHANNEL_ID = "Cat channel";
    LinkedList<HashMap<String, Object>> list = new LinkedList<>();
    SQLiteDatabase sdb;
    MyOpenHelper myOpenHelper;

    public NotifyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myOpenHelper = new MyOpenHelper(getApplicationContext());
        sdb = myOpenHelper.getWritableDatabase();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel nChannel = new NotificationChannel(CHANNEL_ID, "Пора принять лекарство!", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(nChannel);
        Intent clickIntent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent doneIntent = new Intent(this, GoodJob.class);
        PendingIntent donePend = PendingIntent.getActivity(this, 0, doneIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Прими лекарство")
                .setContentText("ВРемя принять *Название*")
                .setSmallIcon(R.drawable.icon)
                .setChannelId(CHANNEL_ID)
                .addAction(R.drawable.ic_launcher_background, "Выполнено", donePend)
                .setContentIntent(pIntent)
                .setAutoCancel(true);


//        Intent aintent = new Intent(getApplicationContext(), NotifyService.class);
//        PendingIntent alarmPend = PendingIntent.getService(getApplicationContext(), 0, aintent, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);

        String[] keyQuery = {MyOpenHelper.COLUMN_TITLE, MyOpenHelper.COLUMN_START, MyOpenHelper.COLUMN_INTERVAL, MyOpenHelper.COLUMN_AMOUNT_TIME};
        Cursor cursor = sdb.query(MyOpenHelper.TABLE_NAME, keyQuery, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String tittle = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_TITLE));
                String amount = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_AMOUNT_TIME));

                nBuilder.setContentTitle("Прими " + tittle);
                nBuilder.setContentText("Дозировка: " + amount);
                Notification notification = nBuilder.build();
//                alarmManager.set(AlarmManager.RTC, time+1000, alarmPend);
                notificationManager.notify(NOTIFY_ID++, notification);
            } while (cursor.moveToNext());
        }
        cursor.close();
        myOpenHelper.close();
        sdb.close();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}