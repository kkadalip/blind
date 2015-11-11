package com.blind.karl.blind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.blind.karl.blind.MESSAGE";

    public class CustomButton extends Button {

        public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        // http://stackoverflow.com/questions/26889410/extending-button-class
        // lisan siia igale buttonile package ja name? vb ebavajalik
    }

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;

    String btn1_package;
    String btn1_name;

    String btn3_package;
    String btn3_name;

    List<Button> buttonsList;

    Button btnLastPage;
    Button btnNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);

/*        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);

        buttonsList = new ArrayList<>();

        buttonsList.add(btn1);
        buttonsList.add(btn2);
        buttonsList.add(btn3);
        buttonsList.add(btn4);
        buttonsList.add(btn5);
        buttonsList.add(btn6);
        buttonsList.add(btn7);
        buttonsList.add(btn8);

        btnLastPage = (Button) findViewById(R.id.btnLastPage);
        btnNextPage = (Button) findViewById(R.id.btnNextPage);

        btnLastPage.setEnabled(false);
        btnNextPage.setEnabled(false);

/*        btn3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //your action on long click
                startExtrasActivity("btn3");
                return true;
            }
        });*/
        for (Button b : buttonsList) {
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //your action on long click
                    String btn_id = getResources().getResourceEntryName(v.getId());
                    startExtrasActivity(btn_id);
                    return true;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateButtons();
    }


    public void updateButtons(){
        //btn1_package = getButtonSelection("btn1_package");
        //btn1_name = getButtonSelection("btn1_name");
        //btn1.setText(btn1_name);

        for (Button b : buttonsList) {
            //String idAsString = getResources().getResourceName(b.getId()); //com.blind.karl.blind:id/btn1
            String idAsString = getResources().getResourceEntryName(b.getId());
            Log.d("log","id as string is " + idAsString);
            String btn_package_generic = getButtonSelection(idAsString + "_package"); // btn1_package
            String btn_name_generic = getButtonSelection(idAsString + "_name"); // btn1_name
            b.setText(btn_name_generic);
        }
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
            //startExtrasActivity(btn_id);

            startExtrasActivityForResult(btn_id);
        }
    }

/*    public void btn1Click(View v){
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(btn1_package);
        Log.d("log","btn1 package is " + btn1_package);
        this.startActivity(intent);
    }*/

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

    static final int START_EXTRAS_REQUEST = 1;  // The request code
    // http://developer.android.com/training/basics/intents/result.html
    // http://stackoverflow.com/questions/10407159/how-to-manage-startactivityforresult-on-android
    public void startExtrasActivityForResult(String btn_id_as_extra){
        Intent intent = new Intent(this, AppsExtraActivity.class);
        //intent.setType()
        intent.putExtra(EXTRA_MESSAGE, btn_id_as_extra);
        //this.startActivity(intent);
        this.startActivityForResult(intent, START_EXTRAS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("log","onActivityResult");
        // Check which request we're responding to
        if (requestCode == START_EXTRAS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d("log","result OK");
            }else if(resultCode == RESULT_CANCELED){
                Log.d("log","result CANCELED");
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//
//    }
}
