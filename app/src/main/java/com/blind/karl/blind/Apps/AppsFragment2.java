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
