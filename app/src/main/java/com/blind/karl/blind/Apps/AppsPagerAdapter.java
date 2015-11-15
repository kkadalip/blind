package com.blind.karl.blind.Apps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AppsPagerAdapter extends FragmentPagerAdapter {
    public AppsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
/*        Fragment fragment1 = new AppsFragment1();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(AppsFragment1.ARG_OBJECT, i + 1);
        fragment1.setArguments(args);
        return fragment1;*/

        Bundle args = new Bundle();
        args.putInt(AppsFragment1.ARG_OBJECT, position + 1);

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AppsFragment1();
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
        return "OBJECT " + (position + 1);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
