package com.blind.karl.blind;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class AppsActivity extends Activity {

    Button btn1;

    String btn1_package;
    String btn1_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);

        btn1 = (Button) findViewById(R.id.btn1);

        btn1_package = getButtonSelection("btn1_package");
        btn1_name = getButtonSelection("btn1_name");

        btn1.setText(btn1_name);

/*        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    public void btn1Click(View v){
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(btn1_package);
        Log.d("tag","btn1 package is " + btn1_package);
        this.startActivity(intent);
    }

    public String getButtonSelection(String key){
        SharedPreferences settings = getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String result = settings.getString(key, "None");
        return result;
    }

    public void btn2Click(View v){
         Intent intent = new Intent(this, AppsExtraActivity.class);
        this.startActivity(intent);
    }
}
