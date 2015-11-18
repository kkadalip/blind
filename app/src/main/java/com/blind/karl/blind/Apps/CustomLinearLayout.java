package com.blind.karl.blind.Apps;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blind.karl.blind.R;

// for advanced stuff http://stackoverflow.com/questions/9181529/detect-fling-gesture-over-clickable-items
// http://stackoverflow.com/questions/21953833/android-onintercepttouchevent-doesnt-get-action-when-have-child-view
// NB http://neevek.net/posts/2013/10/13/implementing-onInterceptTouchEvent-and-onTouchEvent-for-ViewGroup.html current solution inspiration
public class CustomLinearLayout extends LinearLayout {

    //ViewPager vp;

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    //private GestureDetector gestureDetector;
    //View.OnTouchListener gestureListener;

    //CustomLinearLayout ll;

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);



//        init();

/*        // Gesture detection
        gestureDetector = new GestureDetector(new MyGestureDetector()); // new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };*/

        //ll = (CustomLinearLayout) findViewById(R.id.linearLayoutAppsMain);
    }


/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;

            case MotionEvent.ACTION_MOVE:
                return true; // Layout to consume all the touch events from its children

            case MotionEvent.ACTION_UP:
                return false;
        }

        return true;
    }*/

    boolean mIsBeingDragged = false;

    float mStartX = 0;
    float mStartY = 0;

    float mLastX = 0;
    float mLastY = 0;

    float mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                Log.d(getClass().toString(),"onInterceptTouchEvent ACTION DOWN");
                mLastX = event.getX();
                mLastY = event.getY();
                mStartX = mLastX;
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.d(getClass().toString(),"onInterceptTouchEvent ACTION CANCEL");
                break;

            case MotionEvent.ACTION_UP:
                Log.d(getClass().toString(),"onInterceptTouchEvent ACTION UP");
                mIsBeingDragged = false;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d(getClass().toString(),"onInterceptTouchEvent ACTION MOVE");

                float x = event.getX();
                float y = event.getY();

                //float xDelta = Math.abs(x - mLastX);
                //float yDelta = Math.abs(y - mLastY);

                float xDeltaTotal = x - mStartX;
                //float yDeltaTotal = y - mStartY;

                if (Math.abs(xDeltaTotal) > mTouchSlop) { //xDelta > yDelta &&
                    mIsBeingDragged = true;
                    mStartX = x;
                    return true;
                }
                break;
        }

        return false;
    }
    boolean hasAlreadyChangedPage = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;
                hasAlreadyChangedPage = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();

                //float xDelta = Math.abs(x - mLastX);
                //float yDelta = Math.abs(y - mLastY);

                float xDeltaTotal = x - mStartX;

                if (!mIsBeingDragged && Math.abs(xDeltaTotal) > mTouchSlop) { // yDelta > xDelta
                    mIsBeingDragged = true;
                    mStartX = x;
                    xDeltaTotal = 0;
                }

/*                if (xDeltaTotal > 0){
                    xDeltaTotal = 0;
                }*/

                if (mIsBeingDragged) {
                    //scrollBy();
                    //scrollTo((int)xDeltaTotal, 0); // NOT THE CORRECT THING, moves while linearlayout

                    // vp.getScrollX()

                    ViewPager vp = (ViewPager) findViewById(R.id.myViewPager);
                    //vp.scrollTo((int) -xDeltaTotal, 0);

                    Log.d("log", "x delta total is " + Float.toString(xDeltaTotal));
                    if(xDeltaTotal < -300 && mStartX > x){
                        if(hasAlreadyChangedPage == false){
                            Log.d("log", "SWIPING RIGHT");
                            int currentPage = vp.getCurrentItem();
                            vp.setCurrentItem(currentPage + 1, true);
                            mIsBeingDragged = false;
                            xDeltaTotal = 0;
                            //break; // necessary?
                            hasAlreadyChangedPage = true;
                        }

                    }

                    if(xDeltaTotal > 300 && mStartX < x){ // Swiping finger left to right, screen has to go left
                        if(hasAlreadyChangedPage == false){
                            Log.d("log", "SWIPING LEFT");
                            int currentPage = vp.getCurrentItem();
                            vp.setCurrentItem(currentPage - 1, true);
                            mIsBeingDragged = false;
                            xDeltaTotal = 0;
                            //break; // necessary?
                            hasAlreadyChangedPage = true;
                        }

                    }

                }

                mLastX = x;
                mLastY = y;
                break;
        }

        return true;
    }

/*    @Override
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

*//*
                if (mIsBeingDragged) {
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
*//*


                break;
            case MotionEvent.ACTION_UP:

                Log.d("log","action up");

                break;
        }
        return true;
    }*/

/*    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflater =
                (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.apps_main, this, true);
    }*/


// http://twigstechtips.blogspot.com.ee/2013/02/android-detecting-gesture-swipes-and.html
    // http://stackoverflow.com/questions/937313/android-basic-gesture-detection
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("log", "left swipe");
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("log", "right swipe");
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

}

