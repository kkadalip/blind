package com.blind.karl.blind.Apps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blind.karl.blind.AppsExtraActivity;
import com.blind.karl.blind.Listener;
import com.blind.karl.blind.MenuActivity;
import com.blind.karl.blind.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AppsActivity extends FragmentActivity {
    public final static String EXTRA_MESSAGE = "com.blind.karl.blind.MESSAGE";

    //LinearLayout llAppsMain;

    List<Button> buttonsList;

    Vibrator v;
    Button btnMic;

    Listener myListener;

    SpeechRecognizer sr;

    Button btnLocale;
    private Locale myLocale;

    AppsPagerAdapter myPagerAdapter;
    ViewPager myViewPager;

    ActionBar actionBar;

/*    private boolean isReadyForPull(){
        return mScrollView.getScrollY()==0;
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apps_main);

        //llAppsMain = (LinearLayout) findViewById(R.id.linearLayoutAppsMain);


        //llAppsMain.onInterceptTouchEvent(MotionEvent event)
/*


        llAppsMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // get pointer index from the event object
                //int pointerIndex = event.getActionIndex();

                // get pointer ID
                //int pointerId = event.getPointerId(pointerIndex);

                // get masked (not specific to a pointer) action
                int maskedAction = event.getActionMasked(); // or? final int action = event.getAction();

                Boolean mIsBeingDragged = false;
                float mLastMotionY = 0;
                float mLastMotionX = 0;
                Log.d("log","masked action is " + event.toString());
                switch (maskedAction) {
                    case MotionEvent.ACTION_MOVE: {
                        if (mIsBeingDragged) {
                            mLastMotionY = event.getY();
                            mLastMotionX = event.getX();
                            //pullEvent();
                            Log.d("log", "PULL EVENT");
                            return true;
                        }
*//*                        else if (isReadyForPull()) {
                            final float y = event.getY(), x = event.getX();
                            final float diff, oppositeDiff, absDiff;
                            diff = y - mLastMotionY;
                            oppositeDiff = x - mLastMotionX;
                            absDiff = Math.abs(diff);
                            ViewConfiguration config = ViewConfiguration.get(getContext());

                            if (absDiff > config.getScaledTouchSlop() &&  absDiff >
                                    Math.abs(oppositeDiff) && diff >= 1f) {
                                mLastMotionY = y;
                                mLastMotionX = x;
                                mIsBeingDragged = true;
                            }
                        }*//*
                        break;
                    }
                }
                return false;
            }
        });*/


        // ViewPager and its adapters use support library fragments, so use getSupportFragmentManager.
        myPagerAdapter = new AppsPagerAdapter(getSupportFragmentManager());
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        myViewPager.setAdapter(myPagerAdapter);
        //myViewPager.setCurrentItem(3); // ??

        actionBar = getActionBar();

        actionBar.setDisplayShowHomeEnabled(false);  // hides action bar icon
        actionBar.setDisplayShowTitleEnabled(false); // hides action bar title

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // When the tab is selected, switch to the corresponding page in the ViewPager.
                myViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // hide the given tab
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        myViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(getString(R.string.tab) + " " + (i + 1))
                            .setTabListener(tabListener));
        }

        btnMic = (Button) findViewById(R.id.btnMic);
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE); //this.context.getSystemService(Context.VIBRATOR_SERVICE);

        btnLocale = (Button) this.findViewById(R.id.btnLocale);
        //loadLocale();

/*        if(savedInstanceState != null){
            tabHost.setCurrentTab(savedInstanceState.getInt("tabState"));
        }*/

    } // ONCREATE END

    public void changeTab(int tabNumber){
        // tab.getPosition()
        myViewPager.setCurrentItem(tabNumber);
    }

/*    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        int selectedTab = tabHost.getCurrentTab();
        outState.putInt("tabState", selectedTab);
    }

    // Center text vertically in tabs
    public void centerTabhostText(){
        RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(rllp);
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void btnClickGeneric(View v){
        Log.d("log","BTN CLICK GENERIC CLICKED!");
        String btn_id = getResources().getResourceEntryName(v.getId()); // tag for extra parameters v.getTag().toString();
        Log.d("log", "id name is " + btn_id); // eg btn1, btn2

        String btn_package = getButtonSelection(btn_id + "_package");
        //if(!btn_package.equals("None")){
        if(!btn_package.isEmpty()){
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(btn_package);
            Log.d("log", "btn generic package is " + btn_package);
            if(doesPackageExist(btn_package)){
                this.startActivity(intent);
            }else{
                Log.e("error","package does not exist");
            }
        }else{
            Log.d("log", btn_id + " has no package assigned yet");
            //startExtrasActivity(btn_id);
            startExtrasActivityForResult(btn_id);
        }
    }

    // kõigi jaoks
    // TODO selliselt peaks kõik üle käima ja ära kontrollima, kui ei eksisteeri, siis eemalda (rakendamise käivitamisel)
    public boolean doesPackageExist(String targetPackage){
        List<ApplicationInfo> packages;
        PackageManager pm;
        pm = getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    // ainult ühe jaoks
//    public boolean doesPackageExist(String targetPackage){
//        PackageManager pm=getPackageManager();
//        try {
//            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//        return true;
//    }

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