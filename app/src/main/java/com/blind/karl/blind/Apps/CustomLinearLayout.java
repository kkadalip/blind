package com.blind.karl.blind.Apps;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.blind.karl.blind.R;

public class CustomLinearLayout extends LinearLayout {

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true; // Layout to consume all the touch events from its children
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("log","event is " + event.toString());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d("log", String.format("ACTION_DOWN | x:%s y:%s"));
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d("log", String.format("ACTION_MOVE | x:%s y:%s"));
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

/*    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater =
                (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.MyView, this, true);
    }*/
}
