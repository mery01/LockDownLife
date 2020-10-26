package com.example.lockdownlife.Services.Sleep;

import android.app.AlarmManager;
import android.content.Context;


public class AlarmManagerProvider {

    private static final String TAG = AlarmManagerProvider.class.getSimpleName();
    private static AlarmManager alarm_manager;

    public static synchronized void injectAlarmManager(AlarmManager alarmManager) {
        if (alarm_manager != null) {
            throw new IllegalStateException("Alarm Manager Already Set");
        }
        alarm_manager = alarmManager;
    }

    static synchronized AlarmManager getAlarmManager(Context context) {
        if (alarm_manager == null) {
            alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return alarm_manager;
    }
}

