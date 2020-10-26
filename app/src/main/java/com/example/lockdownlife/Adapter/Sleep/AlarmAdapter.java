package com.example.lockdownlife.Adapter.Sleep;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.lockdownlife.DataBase.DataBaseContract;
import com.example.lockdownlife.R;


public class AlarmAdapter extends CursorAdapter {

    private TextView tv_title, tv_date_time, tv_repeat;
    private ImageView iv_notifications , random_image;
    private ColorGenerator color_generator = ColorGenerator.DEFAULT;
    private TextDrawable text_drawable;

    public AlarmAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.sleep_content, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        tv_title = view.findViewById(R.id.tv_title);
        tv_date_time = view.findViewById(R.id.tv_date_time);
        tv_repeat = view.findViewById(R.id.tv_repeat);
        iv_notifications = view.findViewById(R.id.iv_notifications);
        random_image = view.findViewById(R.id.iv_random);

        int title_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_TITLE_ALARM);
        int date_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_DATE_ALARM);
        int time_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_TIME_ALARM);
        int repeat_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_REPEAT_ALARM);
        int repeat_interval_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_REPEAT_INTERVAL_ALARM);
        int interval_type_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_INTERVAL_TYPE_ALARM);
        int notifications_column = cursor.getColumnIndex(DataBaseContract.AlarmEntry.KEY_NOTIFICATIONS_ALARM);

        String title = cursor.getString(title_column);
        String date = cursor.getString(date_column);
        String time = cursor.getString(time_column);
        String repeat = cursor.getString(repeat_column);
        String repeat_interval = cursor.getString(repeat_interval_column);
        String interval_type = cursor.getString(interval_type_column);
        String notifications = cursor.getString(notifications_column);


        setReminderTitle(title);

        if (date != null){
            String date_time = date + " " + time;
            setReminderDateTime(date_time);
        }else{
            tv_date_time.setText("Error con la fecha");
        }

        if(repeat != null){
            setReminderRepeatInfo(repeat, repeat_interval, interval_type);
        }else{
            tv_repeat.setText("Error en la repetición");
        }

        if (notifications != null){
            setActiveImage(notifications);
        }else{
            iv_notifications.setImageResource(R.drawable.ic_notifications_off);
        }
    }

    public void setReminderTitle(String title) {

        tv_title.setText(title);

        int color = color_generator.getRandomColor();

        // Create a circular icon consisting of  a random background colour
        text_drawable = TextDrawable.builder()
                .buildRound("", color);
        random_image.setImageDrawable(text_drawable);
    }

    // Set date and time views
    public void setReminderDateTime(String datetime) {
        tv_date_time.setText(datetime);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeat_interval, String interval_type) {
        if(repeat.equals("true")){
            tv_repeat.setText("Repetición cada " + repeat_interval + " " + interval_type + "(s)");
        }else if (repeat.equals("false")) {
            tv_repeat.setText("No hay repetición ");
        }
    }

    // Set active image as on or off
    public void setActiveImage(String notifications){
        if(notifications.equals("true")){
            iv_notifications.setImageResource(R.drawable.ic_notifications_active);
        }else if (notifications.equals("false")) {
            iv_notifications.setImageResource(R.drawable.ic_notifications_off);
        }

    }
}
