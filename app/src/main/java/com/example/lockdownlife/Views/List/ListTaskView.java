package com.example.lockdownlife.Views.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


// creates a listtasks view with scrolling disabled
public class ListTaskView extends ListView {

    public ListTaskView(Context ctx) {
        super(ctx);
    }
    public ListTaskView(Context ctx, AttributeSet attr) {
        super(ctx, attr);
    }
    public ListTaskView(Context ctx, AttributeSet attr, int style) {
        super(ctx, attr, style);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}