package com.example.lockdownlife.Services.Calendar;

import android.app.AlarmManager;
import android.content.Context;


public class EventManagerProvider {

    private static final String TAG = EventManagerProvider.class.getSimpleName();
    private static AlarmManager alarm_manager;

    public static synchronized void injectEventManager(AlarmManager alarmManager) {
        if (alarm_manager != null) {
            throw new IllegalStateException("Alarm Manager Already Set");
        }
        alarm_manager = alarmManager;
    }

    static synchronized AlarmManager getEventManager(Context context) {
        if (alarm_manager == null) {
            alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        }
        return alarm_manager;
    }
}
