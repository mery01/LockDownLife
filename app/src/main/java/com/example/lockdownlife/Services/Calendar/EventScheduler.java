package com.example.lockdownlife.Services.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;


public class EventScheduler {


    public void setEvent(Context ctx, long alarm_time, Uri uri) {

        AlarmManager alarm_manager = EventManagerProvider.getEventManager(ctx);

        PendingIntent pending_intent =
            EventService.getReminderPendingIntent(ctx, uri);


        if (Build.VERSION.SDK_INT >= 23) {

            alarm_manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);

        } else if (Build.VERSION.SDK_INT >= 19) {

            alarm_manager.setExact(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);

        } else {

            alarm_manager.set(AlarmManager.RTC_WAKEUP, alarm_time, pending_intent);

        }
    }

    public void setRepeatEvent(Context ctx, long alarm_time, Uri uri, long repeat_time) {

        AlarmManager alarm_manger = EventManagerProvider.getEventManager(ctx);

        PendingIntent pending_intent =
                EventService.getReminderPendingIntent(ctx, uri);

        alarm_manger.setRepeating(AlarmManager.RTC_WAKEUP, alarm_time, repeat_time, pending_intent);


    }

    public void cancelEvent(Context ctx, Uri uri) {

        AlarmManager alarm_manager = EventManagerProvider.getEventManager(ctx);

        PendingIntent pending_intent =
                EventService.getReminderPendingIntent(ctx, uri);

        alarm_manager.cancel(pending_intent);

    }

}