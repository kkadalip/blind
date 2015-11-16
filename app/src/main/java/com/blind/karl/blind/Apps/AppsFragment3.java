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

public class AppsFragment3 extends Fragment {
    public static final String ARG_OBJECT = "object";

    Button btn17;
    Button btn18;
    Button btn19;
    Button btn20;
    Button btn21;
    Button btn22;
    Button btn23;
    Button btn24;

    List<Button> buttonsList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.apps_buttons_3, container, false);

        Bundle args = getArguments();
        Log.d("log", "args are " + Integer.toString(args.getInt(ARG_OBJECT)));

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));

        btn17 = (Button) rootView.findViewById(R.id.btn17);
        btn18 = (Button) rootView.findViewById(R.id.btn18);
        btn19 = (Button) rootView.findViewById(R.id.btn19);
        btn20 = (Button) rootView.findViewById(R.id.btn20);
        btn21 = (Button) rootView.findViewById(R.id.btn21);
        btn22 = (Button) rootView.findViewById(R.id.btn22);
        btn23 = (Button) rootView.findViewById(R.id.btn23);
        btn24 = (Button) rootView.findViewById(R.id.btn24);

        buttonsList = new ArrayList<>();

        buttonsList.add(btn17);
        buttonsList.add(btn18);
        buttonsList.add(btn19);
        buttonsList.add(btn20);
        buttonsList.add(btn21);
        buttonsList.add(btn22);
        buttonsList.add(btn23);
        buttonsList.add(btn24);

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
