package com.blind.karl.blind.Apps;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.blind.karl.blind.R;

// for advanced stuff http://stackoverflow.com/questions/9181529/detect-fling-gesture-over-clickable-items
// http://stackoverflow.com/questions/21953833/android-onintercepttouchevent-doesnt-get-action-when-have-child-view
public class CustomLinearLayout extends LinearLayout {

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true; // Layout to consume all the touch events from its children
    }

    boolean mIsBeingDragged = false;
    float mLastMotionY = 0;
    float mLastMotionX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("log","event is " + event.toString());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d("log", String.format("ACTION_DOWN | x:%s y:%s"));
                Log.d("log","action down");

                break;
            case MotionEvent.ACTION_MOVE:
                //Log.d("log", String.format("ACTION_MOVE | x:%s y:%s"));

                Log.d("log","action move");

/*                if (mIsBeingDragged) {
                    mLastMotionY = event.getY();
                    mLastMotionX = event.getX();
                    pullEvent();
                    return true;
                }
                else if (isReadyForPull()) {
                    final float y = event.getY(), x = event.getX();
                    final float diff, oppositeDiff, absDiff;
                    diff = y - mLastMotionY;
                    oppositeDiff = x - mLastMotionX;
                    absDiff = Math.abs(diff);
                    ViewConfiguration config = ViewConfiguration.get(getContext());

                    if (absDiff > config.getScaledTouchSlop() &&  absDiff >
                            Math.abs(oppositeDiff) && diff >= 1f) {
                        mLastMotionY = y;
                        mLastMotionX = x;
                        mIsBeingDragged = true;
                    }
                }
                break;*/

                break;
            case MotionEvent.ACTION_UP:

                Log.d("log","action up");

                break;
        }
        return true;
    }

/*    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater =
                (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.apps_main, this, true);
    }*/


}
