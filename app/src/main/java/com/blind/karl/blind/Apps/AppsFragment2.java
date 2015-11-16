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
import android.widget.TextView;

import com.blind.karl.blind.AppsExtraActivity;
import com.blind.karl.blind.R;

import java.util.ArrayList;
import java.util.List;

public class AppsFragment2 extends Fragment {
    public static final String ARG_OBJECT = "object";

    Button btn9;
    Button btn10;
    Button btn11;
    Button btn12;
    Button btn13;
    Button btn14;
    Button btn15;
    Button btn16;

    List<Button> buttonsList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.apps_buttons_2, container, false);

        Bundle args = getArguments();
        Log.d("log", "args are " + Integer.toString(args.getInt(ARG_OBJECT))); // NULLPOINTER, argsi pealt

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        btn9 = (Button) rootView.findViewById(R.id.btn9);
        btn10 = (Button) rootView.findViewById(R.id.btn10);
        btn11 = (Button) rootView.findViewById(R.id.btn11);
        btn12 = (Button) rootView.findViewById(R.id.btn12);
        btn13 = (Button) rootView.findViewById(R.id.btn13);
        btn14 = (Button) rootView.findViewById(R.id.btn14);
        btn15 = (Button) rootView.findViewById(R.id.btn15);
        btn16 = (Button) rootView.findViewById(R.id.btn16);

        buttonsList = new ArrayList<>();

        buttonsList.add(btn9);
        buttonsList.add(btn10);
        buttonsList.add(btn11);
        buttonsList.add(btn12);
        buttonsList.add(btn13);
        buttonsList.add(btn14);
        buttonsList.add(btn15);
        buttonsList.add(btn16);

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
