package com.blind.karl.blind.Apps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blind.karl.blind.R;

public class AppsFragment3 extends Fragment {
    public static final String ARG_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.apps_buttons_3, container, false);

        Bundle args = getArguments();
        Log.d("log", "args are " + Integer.toString(args.getInt(ARG_OBJECT)));

//        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
//                Integer.toString(args.getInt(ARG_OBJECT)));
        return rootView;
    }
}
