package com.blind.karl.blind;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends Activity {


    // http://stackoverflow.com/questions/6165023/get-list-of-installed-android-applications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);
    }

    public void GetApps(View view) {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> installedApps = new ArrayList<ApplicationInfo>();

        List<String> installedAppsNames = new ArrayList<String>();

        final ListView listViewApps = (ListView) findViewById(R.id.listViewApps);
        final ArrayList<String> list = new ArrayList<String>();
        // This is the array adapter:
        // First param: context of activity
        // Second param: type of list view
        // Third param: your array
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1, //simple_expandable_list_item_1
                list);
        listViewApps.setAdapter(arrayAdapter);

        for(ApplicationInfo app : apps) {
            if((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1) {  // Updated system app, add
                installedApps.add(app);
                installedAppsNames.add(pm.getApplicationLabel(app).toString()); // real names
                list.add(pm.getApplicationLabel(app).toString());
                //Drawable icon = pm.getApplicationIcon(app); // real icons
            }else if((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {// System app, don't add
            }else{
                installedApps.add(app);
                installedAppsNames.add(pm.getApplicationLabel(app).toString());
                //listViewApps.add(pm.getApplicationLabel(app).toString());
                list.add(pm.getApplicationLabel(app).toString());
            }
        }
        Log.d("log", installedApps.toString());
        Log.d("log", installedAppsNames.toString());
    }
}