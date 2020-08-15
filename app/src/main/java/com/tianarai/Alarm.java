package com.tianarai;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "my_channel";
    static final String FULL_SCREEN_ACTION = "full_screen_action";
    static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Alarm:onReceive 1", Toast.LENGTH_SHORT).show();

        //if (FullscreenActivity.FULL_SCREEN_ACTION.equals(intent.getAction()))
            FullscreenActivity.CreateFullScreenNotification(context);

        Toast.makeText(context, "Alarm:onReceive 2", Toast.LENGTH_SHORT).show();
    }


    public void setAlarm(Context context)
    {

        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, 0);
        int interval = 10000;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + interval, pendingIntent);
        }
        //Toast.makeText(context, "Alarm:setAlarm", Toast.LENGTH_SHORT).show();

    }

    public void setAlarm2(Context context) {
        Intent alarmIntent = new Intent(context, FullscreenActivity.class);
        context.startActivity(alarmIntent);
        //createNotificationChannel(context);
        //CreateFullScreenNotification(context);

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(android.R.drawable.btn_star);
        builder.setContentTitle("This is title of notification");
        builder.setContentText("This is a notification Text");
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        Intent intent = new Intent(context, Alarm.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 113,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        builder.setFullScreenIntent(pendingIntent, true);


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(115, builder.build());*/
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.am_alarm);
            String description = context.getString(R.string.am_alarm_warning);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
