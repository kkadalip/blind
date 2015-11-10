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
    public final static String EXTRA_MESSAGE = "com.blind.karl.blind.MESSAGE";

    Button btn1;
    String btn1_package;
    String btn1_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);

/*        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateButtons();
    }


    public void updateButtons(){
        btn1 = (Button) findViewById(R.id.btn1);

        btn1_package = getButtonSelection("btn1_package");
        btn1_name = getButtonSelection("btn1_name");

        btn1.setText(btn1_name);
    }

    public void btnClickGeneric(View v){
        String btn_id = getResources().getResourceEntryName(v.getId()); // tag for extra parameters v.getTag().toString();
        Log.d("log", "id name is " + btn_id); // eg btn1, btn2

        String btn_package = getButtonSelection(btn_id + "_package");
        if(!btn_package.equals("None")){
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(btn_package);
            Log.d("log","btn generic package is " + btn_package);
            this.startActivity(intent);
        }else{
            Log.d("log", btn_id + " has no package assigned yet");
            startExtrasActivity(btn_id);
        }
    }

    public void btn1Click(View v){
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(btn1_package);
        Log.d("log","btn1 package is " + btn1_package);
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

    public void startExtrasActivity(String btn_id_as_extra){
        Intent intent = new Intent(this, AppsExtraActivity.class);

        intent.putExtra(EXTRA_MESSAGE, btn_id_as_extra);

        this.startActivity(intent);
    }
}
