package com.blind.karl.blind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AppsExtraActivity extends Activity {

    // http://stackoverflow.com/questions/6165023/get-list-of-installed-android-applications
    //Button btnShortcut;
    String appPackageName; // shortcut

    String extraMessage;

    //List<ApplicationInfo> apps;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_extra);

        //btnShortcut = (Button) this.findViewById(R.id.btnShortcut);

        Intent intent = getIntent();
        extraMessage = intent.getStringExtra(AppsActivity.EXTRA_MESSAGE);
        Log.d("log","message from AppsActivity is " + extraMessage);

        new GetAppsAsync().execute();
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
                //btnShortcut.setText(realName);

                appPackageName = installedApps.get(position).processName.toString();
                Log.d("log", "app appPackage is " + appPackageName);

                setButtonSelection(extraMessage+"_package", appPackageName);
                setButtonSelection(extraMessage+"_name", realName);
                //setButtonSelection("btn1_package", appPackageName);
                //setButtonSelection("btn1_name", realName);
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

    // http://stackoverflow.com/questions/25647881/android-asynctask-example-and-explanation/25647882#25647882
    // Esimene parameeter tuleb executest, läheb doInBackgroundi nt params[0]
    // Teine parameeter läheb doInBackGroundi publishProgress(i)-st onProgressUpdate'i
    // Kolmas parameeter läheb doInBackground returnist onPostExecute'i
    private class GetAppsAsync extends AsyncTask<String, Integer, List<ApplicationInfo>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Do something like display a progress bar
        }

        // This is run in a background thread
        @Override
        protected List<ApplicationInfo> doInBackground(String... params) {
            pm = getPackageManager();
            List<ApplicationInfo> apps = pm.getInstalledApplications(0);
            return apps; //"this string is passed to onPostExecute";
        } // executega kaasas, progressupdate return, onpostexecute parameeter

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(List<ApplicationInfo> apps) {
            super.onPostExecute(apps); //from DoInBackground

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
                    AppsExtraActivity.this, // http://stackoverflow.com/questions/16920942/getting-context-in-asynctask
                    android.R.layout.simple_list_item_1, //simple_expandable_list_item_1
                    installedAppsNames);//list);
            listViewApps.setAdapter(arrayAdapter);
            // setOnItemSelectedListener
            //listViewApps.setClickable(true);

            listViewApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = listViewApps.getItemAtPosition(position);
                    String realName = o.toString();
                    Log.d("log", "real name is " + realName);
                    //btnShortcut.setText(realName);

                    appPackageName = installedApps.get(position).processName.toString();
                    Log.d("log", "app appPackage is " + appPackageName);

                    setButtonSelection(extraMessage + "_package", appPackageName);
                    setButtonSelection(extraMessage + "_name", realName);
                    //setButtonSelection("btn1_package", appPackageName);
                    //setButtonSelection("btn1_name", realName);

                    //Intent intent = new Intent(AppsExtraActivity.this, AppsActivity.class);
                    //AppsExtraActivity.this.startActivity(intent);

                    //AppsExtraActivity.this.finishActivity();

                    Intent returnIntent = new Intent(AppsExtraActivity.this, AppsActivity.class);
                    //returnIntent.putExtra("result",result);
                    setResult(Activity.RESULT_OK, returnIntent);
                    AppsExtraActivity.this.finish();
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

            //Collections.sort(list);
            //Collections.sort(installedAppsNames);

            // Do things like hide the progress bar or change a TextView
        }
    } // end Async

    public void btnCancelClick (View v){
        Log.d("log", "cancel button clicked");
        Intent returnIntent = new Intent(this, AppsActivity.class);
        setResult(Activity.RESULT_CANCELED,returnIntent);
        this.finish();
    }

    public void btnDeleteClick (View v){
        Log.d("log", "delete button clicked");
        SharedPreferences settings = getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(extraMessage+"_package");
        editor.remove(extraMessage+"_name");
        editor.commit();

        this.finish();
    }

    public class App {
        private String realName;
        private String launchPackageName;
    }

}