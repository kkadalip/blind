/*
package com.blind.karl.blind.Apps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blind.karl.blind.AppsExtraActivity;
import com.blind.karl.blind.R;

import java.util.ArrayList;
import java.util.List;

public class AppsFragment1old extends Fragment {
    public static final String ARG_OBJECT = "object";

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;

    List<Button> buttonsList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.apps_buttons_1, container, false);

        Bundle args = getArguments();
        Log.d("log", "args are " + Integer.toString(args.getInt(ARG_OBJECT)));

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        btn1 = (Button) rootView.findViewById(R.id.btn1);
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

        setOnClickListenersForButtons(buttonsList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateButtons();
    }

    public void setOnClickListenersForButtons(List<Button> buttonsList){
        for (Button b : buttonsList) {
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //your action on long click
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

    public void updateButtons(){
        for (Button b : buttonsList) {
            //String idAsString = getResources().getResourceName(b.getId()); //com.blind.karl.blind:id/btn1
            String idAsString = getResources().getResourceEntryName(b.getId());
            Log.d("log","id as string is " + idAsString);
            String btn_package_generic = getButtonSelection(idAsString + "_package"); // btn1_package
            if(!btn_package_generic.isEmpty()){
                String btn_name_generic = getButtonSelection(idAsString + "_name"); // btn1_name
                b.setText(btn_name_generic);
            }
        }
        //myListener.packageForIntent = btn_package; // UPDATING CLASS CONSTRUCTOR PARAMETER
        //packageForIntent = btn_package; // FOR LISTENER
    }

    public String getButtonSelection(String key){
        SharedPreferences settings = getActivity().getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String result = settings.getString(key, ""); // default value None before, now empty string
        return result;
    }

}
*/
