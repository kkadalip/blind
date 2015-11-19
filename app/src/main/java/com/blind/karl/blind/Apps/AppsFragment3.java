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
    String LOG_TAG = "[AppsFragment2]";
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
        Log.d(LOG_TAG, "[onCreateView] args are " + Integer.toString(args.getInt(ARG_OBJECT)));

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

        ((AppsActivity)getActivity()).addButtonsToList(buttonsList);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppsActivity)getActivity()).updateButtons(buttonsList);
    }

    public void setOnClickListenersForButtons(List<Button> buttonsList){
        for (Button b : buttonsList) {
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    String btn_id = getResources().getResourceEntryName(v.getId());
                    ((AppsActivity)getActivity()).startExtrasActivityForResult(btn_id);
                    return true;
                }
            });
        }
    }

}
