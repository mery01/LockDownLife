package com.example.lockdownlife.Adapter.Calendar;

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


public class EventAdapter extends CursorAdapter {

    private TextView tv_cal_title, tv_cal_start_date_time, tv_cal_end_date_time, tv_cal_repeat;
    private ImageView iv_cal_notifications , random_image;
    private ColorGenerator color_generator = ColorGenerator.DEFAULT;
    private TextDrawable text_drawable;

    public EventAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.calendar_content, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        tv_cal_title = view.findViewById(R.id.tv_cal_title);
        tv_cal_start_date_time = view.findViewById(R.id.tv_cal_start_date_time);
        tv_cal_end_date_time = view.findViewById(R.id.tv_cal_end_date_time);
        tv_cal_repeat = view.findViewById(R.id.tv_repeat);
        iv_cal_notifications = view.findViewById(R.id.iv_cal_notifications);
        random_image = view.findViewById(R.id.iv_cal_random);

        int title_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_TITLE_EVENT);
        int date_start__column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_START_DATE_EVENT);
        int date_end__column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_END_DATE_EVENT);
        int time_start_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_START_TIME_EVENT);
        int time_end_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_END_TIME_EVENT);
        int repeat_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_REPEAT_EVENT);
        int repeat_interval_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_REPEAT_INTERVAL_EVENT);
        int interval_type_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_INTERVAL_TYPE_EVENT);
        int notifications_column = cursor.getColumnIndex(DataBaseContract.EventEntry.KEY_NOTIFICATIONS_EVENT);

        String title = cursor.getString(title_column);
        String start_date = cursor.getString(date_start__column);
        String start_time = cursor.getString(time_start_column);
        String end_date = cursor.getString(date_end__column);
        String end_time = cursor.getString(time_end_column);
        String repeat = cursor.getString(repeat_column);
        String repeat_interval = cursor.getString(repeat_interval_column);
        String interval_type = cursor.getString(interval_type_column);
        String notifications = cursor.getString(notifications_column);

        setReminderTitle(title);

        if (start_date != null && start_time!=null && end_date!=null && end_time!=null){
            String start_date_time = "Inicio: "+ start_date + " " + start_time;
            String end_date_time = "Fin: " + end_date + " " + end_time;
            setReminderDateTime(start_date_time, end_date_time);

        }else{
            tv_cal_start_date_time.setText("Error con la fecha de inicio");
            tv_cal_end_date_time.setText("Error con la fecha de fin");
        }

        if(repeat != null){
            setReminderRepeatInfo(repeat, repeat_interval, interval_type);
        }else{
            tv_cal_repeat.setText("Error en la repetición");
        }

        if (notifications != null){
            setActiveImage(notifications);
        }else{
            iv_cal_notifications.setImageResource(R.drawable.ic_notifications_off);
        }
    }

    public void setReminderTitle(String title) {

        tv_cal_title.setText(title);

        int color = color_generator.getRandomColor();

        // Create a circular icon consisting of  a random background colour and first letter of title
        text_drawable = TextDrawable.builder()
                .buildRect("", color);
        random_image.setImageDrawable(text_drawable);
    }

    // Set date and time views
    public void setReminderDateTime(String startdatetime,String enddatetime ) {
        tv_cal_start_date_time.setText(startdatetime);
        tv_cal_end_date_time.setText(enddatetime);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeat_interval, String interval_type) {
        if(repeat.equals("true")){
            tv_cal_repeat.setText("Repetición cada " + repeat_interval + " " + interval_type + "(s)");
        }else if (repeat.equals("false")) {
            tv_cal_repeat.setText("No hay repetición ");
        }
    }

    // Set active image as on or off
    public void setActiveImage(String notifications){
        if(notifications.equals("true")){
            iv_cal_notifications.setImageResource(R.drawable.ic_notifications_active);
        }else if (notifications.equals("false")) {
            iv_cal_notifications.setImageResource(R.drawable.ic_notifications_off);
        }

    }
}
