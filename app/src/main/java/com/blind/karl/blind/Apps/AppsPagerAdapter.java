package com.blind.karl.blind.Apps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blind.karl.blind.R;

public class AppsPagerAdapter extends FragmentPagerAdapter {
    Context myContext;

    public AppsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        myContext = context;
    }

/*    public AppsPagerAdapter(FragmentManager fm) {
        super(fm);
    }*/

    @Override
    public Fragment getItem(int position) {

        Bundle args = new Bundle();
        args.putInt(AppsFragment1.ARG_OBJECT, position + 1);

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AppsFragment1();
                //fragment = new AppsActivity.AppsFragment1();
                break;
            case 1:
                fragment = new AppsFragment2();
                break;
            case 2:
                fragment = new AppsFragment3();
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return "OBJECT " + (position + 1);
        return  (myContext.getString(R.string.tab) + " " + (position + 1));
    }

    @Override
    public int getCount() {
        return 3; // List<Fragment> fragments; return this.fragments.size();
    }
}
