package com.blind.karl.blind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppsExtraActivity extends Activity {

    // http://stackoverflow.com/questions/6165023/get-list-of-installed-android-applications
    Button btnShortcut;
    String appPackageName; // shortcut

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_extra);

        btnShortcut = (Button) this.findViewById(R.id.btnShortcut);
    }

    public void GetApps(View view) {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        final List<ApplicationInfo> installedApps = new ArrayList<>();
        final List<String> installedAppsPackages = new ArrayList<>();

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
        // setOnItemSelectedListener
        //listViewApps.setClickable(true);
        listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listViewApps.getItemAtPosition(position);
                String realName = o.toString();
                btnShortcut.setText(realName);

                appPackageName = installedApps.get(position).processName.toString();
                Log.d("log", "app appPackage is " + appPackageName);

                setButtonSelection("btn1_package", appPackageName);
                setButtonSelection("btn1_name", realName);
            }
        });

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

    public void setButtonSelection(String key, String value){
        SharedPreferences settings = getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value); // Key, Value, to get it eg String language = prefs.getString(langPref, "");
        editor.commit();
    }

    public void Shortcut(View v){
        //startNewActivity(this, appPackageName);
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(appPackageName);
        this.startActivity(intent);
    }

    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}