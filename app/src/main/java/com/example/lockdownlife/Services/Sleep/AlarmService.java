package com.example.lockdownlife.Services.Sleep;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.example.lockdownlife.DataBase.DataBaseContract;
import com.example.lockdownlife.R;
import com.example.lockdownlife.Views.Sleep.AddAlarm;


public class AlarmService extends IntentService {
    private static final String TAG = AlarmService.class.getSimpleName();

    private NotificationManager notification;

    private static final int NOTIFICATION_ID = 42;

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {

        Intent intent = new Intent(context, AlarmService.class);
        Log.d("AQUIIII","DENTRO DE REMINDER ALARM SERVICE ON PENDING INTENT");
        intent.setData(uri);
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public AlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d("AQUIIII","DENTRO DE REMINDER ALARM SERVICE ON HANDLE INTENT");

        // crea canal de notificaciones
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "com.uc3m.it.helloallarmappmov.notify_001");
        Uri uri = intent.getData();

        Log.d("AQUIIII","ANTES INTENT");

        //Display a notification to view the task details
        Intent a_intent = new Intent(this, AddAlarm.class);
        a_intent.setData(uri);

        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, a_intent, 0);
        builder.setContentIntent(pending_intent);

        Log.d("AQUIIII","DESPUES PENDING INTENT");

        //Grab the task description
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String alarm_title = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                Log.d("AQUIIII","DENTRO IF");
                alarm_title = DataBaseContract.getColumnString(cursor, DataBaseContract.AlarmEntry.KEY_TITLE_ALARM);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Log.d("AQUIIII","ANTES NOTIFICATION");

        // Primero ponemos los atributos de la notificacion
        builder.setSmallIcon(R.drawable.ic_notifications_active);
        builder.setLights(Color.MAGENTA, 1, 0);
        builder.setContentTitle("ALARMA");
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentText(alarm_title);
        builder.setContentIntent(pending_intent);


        notification =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channel_id = "YOUR_CHANNEL_ID";
            NotificationChannel notfication_ch = new NotificationChannel(channel_id,
                    "Canal de LockDownLife",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notification.createNotificationChannel(notfication_ch);
            builder.setChannelId(channel_id);
        }

        Log.d("AQUIII","ANTES NOTIFY");

        notification.notify(0, builder.build());

    }
}
