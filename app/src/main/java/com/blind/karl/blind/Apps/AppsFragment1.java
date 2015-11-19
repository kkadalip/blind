package com.blind.karl.blind.Apps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.blind.karl.blind.AppsExtraActivity;
import com.blind.karl.blind.R;

import java.util.ArrayList;
import java.util.List;

// http://stackoverflow.com/questions/12659747/call-an-activity-method-from-a-fragment
public class AppsFragment1 extends Fragment {
    public static final String ARG_OBJECT = "object";

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;

    public List<Button> buttonsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.apps_buttons_1, container, false);

        Bundle args = getArguments();
        Log.d("log", "args are " + Integer.toString(args.getInt(ARG_OBJECT)));

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        // getView().findViewById(R.id.your_view);
        btn1 = (Button) rootView.findViewById(R.id.btn1); // getView() works as well
        btn2 = (Button) rootView.findViewById(R.id.btn2);
        btn3 = (Button) rootView.findViewById(R.id.btn3);
        btn4 = (Button) rootView.findViewById(R.id.btn4);
        btn5 = (Button) rootView.findViewById(R.id.btn5);
        btn6 = (Button) rootView.findViewById(R.id.btn6);
        btn7 = (Button) rootView.findViewById(R.id.btn7);
        btn8 = (Button) rootView.findViewById(R.id.btn8);

        buttonsList = new ArrayList<>();

        buttonsList.add(btn1);
        buttonsList.add(btn2);
        buttonsList.add(btn3);
        buttonsList.add(btn4);
        buttonsList.add(btn5);
        buttonsList.add(btn6);
        buttonsList.add(btn7);
        buttonsList.add(btn8);

        // Gesture detection
/*        gestureDetector = new GestureDetector(getActivity(), new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };*/

        //imageView.setOnClickListener(SelectFilterActivity.this);
        //btn1.setOnTouchListener(gestureListener);
//        btn2.setOnTouchListener(gestureListener);
//        btn3.setOnTouchListener(gestureListener);
//        btn4.setOnTouchListener(gestureListener);
//        btn5.setOnTouchListener(gestureListener);
//        btn6.setOnTouchListener(gestureListener);
//        btn7.setOnTouchListener(gestureListener);
//        btn8.setOnTouchListener(gestureListener);

/*
        btn1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Log.d("log","HAHA YOU CLICKED BTN 1");
            }
        });
*/

        setOnClickListenersForButtons(buttonsList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppsActivity)getActivity()).updateButtons(getContext(), buttonsList);
        //updateButtons();
    }

    public void setOnClickListenersForButtons(List<Button> buttonsList){
        for (Button b : buttonsList) {
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String btn_id = getResources().getResourceEntryName(v.getId());
                    //startExtrasActivity(btn_id);
                    startExtrasActivityForResult(btn_id);
                    return true;
                }
            });
        }
    }

    public final static String EXTRA_MESSAGE = "com.blind.karl.blind.MESSAGE";
    static final int START_EXTRAS_REQUEST = 1;  // The request code
    // http://developer.android.com/training/basics/intents/result.html
    // http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
    public void startExtrasActivityForResult(String btn_id_as_extra){
        Intent intent = new Intent(getActivity(), AppsExtraActivity.class);
        //intent.setType()
        intent.putExtra(EXTRA_MESSAGE, btn_id_as_extra);
        //this.startActivity(intent);
        this.startActivityForResult(intent, START_EXTRAS_REQUEST);
    }

/*    public void updateButtons(){
        for (Button b : buttonsList) {
            String idAsString = getResources().getResourceEntryName(b.getId()); // .getResourceName(b.getId()); would get com.blind.karl.blind:id/btn1 not btn1
            Log.d("log","id as string is " + idAsString);
            String btn_package_generic = getButtonSelection(idAsString + "_package"); // btn1_package
            if(!btn_package_generic.isEmpty()){
                String btn_name_generic = getButtonSelection(idAsString + "_name"); // btn1_name
                b.setText(btn_name_generic);
            }
        }
        //myListener.packageForIntent = btn_package; // UPDATING CLASS CONSTRUCTOR PARAMETER
        //packageForIntent = btn_package; // FOR LISTENER
    }*/

/*
    public String getButtonSelection(String key){
        SharedPreferences settings = getActivity().getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String result = settings.getString(key, ""); // default value None before, now empty string
        return result;
    }
*/

//    private static final int SWIPE_MIN_DISTANCE = 120;
//    private static final int SWIPE_MAX_OFF_PATH = 250;
//    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
//    private GestureDetector gestureDetector;
//    View.OnTouchListener gestureListener;

/*    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("log","FLING!");
            try {
                int currentTab = getActivity().getActionBar().getSelectedNavigationIndex(); // first tab is 0, second 1 ...
                Log.d("log", "current tab is " + Integer.toString(currentTab));

                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("log", "right swipe");
                    if(currentTab < 2){
                        getActivity().getActionBar().setSelectedNavigationItem(currentTab + 1);
                    }
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("log", "left swipe");
                    if(currentTab > 0){
                        getActivity().getActionBar().setSelectedNavigationItem(currentTab - 1);
                    }
                    //AppsActivity.this.changeTab(2);

                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("log","down meh");
            return true;
        }

    }*/
}
