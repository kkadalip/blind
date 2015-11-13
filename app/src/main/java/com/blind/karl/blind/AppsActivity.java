package com.blind.karl.blind;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    //Button btnLastPage;
    //Button btnNextPage;

    Button btnMainMenu;
    Button btnAllApps;

    Vibrator v;
    Button btnMic;

    //Intent customIntent;
    //String packageForIntent;

    Listener myListener;

    SpeechRecognizer sr;

    Button btnLocale;
    private Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps_main);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //TabHost tabHost = (TabHost) findViewById(R.id.tabhost);

        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("First Tab")
                .setIndicator(getString(R.string.tab1))
                .setContent(R.id.linearLayoutView1));

        tabHost.addTab(tabHost.newTabSpec("Second Tab")
                .setIndicator(getString(R.string.tab2))
                .setContent(R.id.linearLayoutView2));

        tabHost.addTab(tabHost.newTabSpec("Third Tab")
                .setIndicator(getString(R.string.tab3))
                .setContent(R.id.linearLayoutView3));

        // Center text vertically in tabs
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(rllp);
        }

/*        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");*/

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected


/*        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this, AppsActivity.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this, AppsActivity.class));

        tab3.setIndicator("Tab3");
        tab3.setContent(new Intent(this, AppsActivity.class));*/

        /** Add the tabs  to the TabHost to display. */

//        tabHost.setup();
//        tabHost.addTab(tab1);
//        tabHost.addTab(tab2);
//        tabHost.addTab(tab3);

/*        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        btnMic = (Button) findViewById(R.id.btnMic);
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE); //this.context.getSystemService(Context.VIBRATOR_SERVICE);

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

//        btnLastPage = (Button) findViewById(R.id.btnLastPage);
//        btnNextPage = (Button) findViewById(R.id.btnNextPage);

        //btnMainMenu = (Button) findViewById(R.id.btnMainMenu);
        //btnAllApps = (Button) findViewById(R.id.btnAllApps);

        //btnAllApps.setEnabled(false);

        //packageForIntent = "empty";

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
                    //startExtrasActivity(btn_id);
                    startExtrasActivityForResult(btn_id);
                    return true;
                }
            });
        }

        btnLocale = (Button) this.findViewById(R.id.btnLocale);
        //loadLocale();
    } // ONCREATE END

    @Override
    protected void onResume() {
        super.onResume();

        updateButtons(); // PIGEM recreate INTENT! TODO
    }

    // asendaks recreate(), kui for result töötaks
    public void updateButtons(){
        //btn1_package = getButtonSelection("btn1_package");
        //btn1_name = getButtonSelection("btn1_name");
        //btn1.setText(btn1_name);

        for (Button b : buttonsList) {
            //String idAsString = getResources().getResourceName(b.getId()); //com.blind.karl.blind:id/btn1
            String idAsString = getResources().getResourceEntryName(b.getId());
            Log.d("log","id as string is " + idAsString);
            String btn_package_generic = getButtonSelection(idAsString + "_package"); // btn1_package
            if(!btn_package_generic.isEmpty()){
                String btn_name_generic = getButtonSelection(idAsString + "_name"); // btn1_name
                b.setText(btn_name_generic);
            }
        }

        //myListener.packageForIntent = btn_package; // UPDATING CLASS CONSTRUCTOR PARAMETER
        //packageForIntent = btn_package; // FOR LISTENER
    }

    public void btnClickGeneric(View v){
        String btn_id = getResources().getResourceEntryName(v.getId()); // tag for extra parameters v.getTag().toString();
        Log.d("log", "id name is " + btn_id); // eg btn1, btn2

        String btn_package = getButtonSelection(btn_id + "_package");
        //if(!btn_package.equals("None")){
        if(!btn_package.isEmpty()){
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
        String result = settings.getString(key, ""); // default value None before, now empty string
        return result;
    }

/*    public void startExtrasActivity(String btn_id_as_extra){
        Intent intent = new Intent(this, AppsExtraActivity.class);

        intent.putExtra(EXTRA_MESSAGE, btn_id_as_extra);

        this.startActivity(intent);
    }*/

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
        Log.d("log", "onActivityResult");
        // Check which request we're responding to
        if (requestCode == START_EXTRAS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d("log","result OK");
                recreate(); // või updatebuttons
            }else if(resultCode == RESULT_CANCELED){
                Log.d("log","result CANCELED");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(sr!=null)
            {
                sr.destroy();
            }
        }
        catch (Exception e)
        {
            Log.e("error","Exception:"+e.toString());
        }
    }

    // language checker? http://stackoverflow.com/questions/10538791/how-to-set-the-language-in-speech-recognition-on-android
    public void btnMicClick(View v){
        Log.d("log", "mic button clicked");

        Locale current = Locale.getDefault();
        Log.d("log", "btn btnMicClick + locale clicked, current locale is " + current.toString());
        String currentLanguage = current.getLanguage();

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

        if(currentLanguage.equals("et")){ // EESTI KEEL:
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            Log.d("log", "language for MIC is et");
            String grammar_supporting_server = "ee.ioc.phon.android.speak.service.HttpRecognitionService";
            String continuous_full_duplex_server = "ee.ioc.phon.android.speak.service.WebSocketRecognitionService";
            sr = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName("ee.ioc.phon.android.speak", grammar_supporting_server));
            //recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        }else{ // if(currentLanguage.contains("en")){ // INGLISE KEEL:
            Log.d("log", "language for MIC is NOT et");
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US.toString());
            //recognizerIntent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
            sr = SpeechRecognizer.createSpeechRecognizer(this);
        }
        //sr.setRecognitionListener(new listener());
        myListener = new Listener(this, btnMic, buttonsList); //packageForIntent);
        sr.setRecognitionListener(myListener);
        sr.startListening(recognizerIntent);
    }

    public void btnMainMenuClick(View v){
        Intent intent = new Intent(this, MenuActivity.class);
        this.startActivity(intent);
    }

//    @Override
//    public void onBackPressed() {
//
//    }

    // LANGUAGE:
    public void btnClickLocale(View v){
        Locale current = Locale.getDefault();
        Log.d("log", "btn locale clicked, current locale is " + current.toString());
        String currentLanguage = current.getLanguage();
        if(currentLanguage.equals("et")){
            changeLang("en");
        }else if(currentLanguage.contains("en")){ // || currentLanguage.equals("en_US")){
            changeLang("et");
        }
        recreate();
    }

    public void changeLang(String lang)
    {
        if(lang.equalsIgnoreCase("")){
            return;
        }else{
            myLocale = new Locale(lang);
            saveLocale(lang);
            Locale.setDefault(myLocale);
            android.content.res.Configuration config = new android.content.res.Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

            //updateTexts();
        }
    }

    public void saveLocale(String lang)
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

/*    public void loadLocale()
    {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }*/
}
