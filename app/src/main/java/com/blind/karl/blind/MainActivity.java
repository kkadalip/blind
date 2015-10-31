package com.blind.karl.blind;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this,
                new ComponentName(
                        "ee.ioc.phon.android.speak",
                        "ee.ioc.phon.android.speak.service.HttpRecognitionService"));
        sr.setRecognitionListener(new listener()); // this
    }

//    public static List<String> getHeardFromDirect(Bundle bundle){
//        List<String> results=new ArrayList<String>();
//        if ((bundle != null) && bundle.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
//            results=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//        }
//        return results;
//    }

    public void doSomething2(View view) {
        Log.d("tag", "do something 2 start");

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        /*SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(this,
                new ComponentName(
                        "ee.ioc.phon.android.speak",
                        "ee.ioc.phon.android.speak.service.HttpRecognitionService"));*/
        // ee.ioc.phon.android.speak.service.HttpRecognitionService (uses the grammar-supporting server)
        // ee.ioc.phon.android.speak.service.WebSocketRecognitionService (uses the continuous full-duplex server)
        sr.startListening(recognizerIntent);
        //startActivity(intent);
        Log.d("tag", "do something 2 end");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sr != null) {
            sr.destroy();
        }
    }

    class listener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("tag", "THIS IS THE BEGINNING");
            System.out.println("THIS IS THE BEGINNING");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
        }

        @Override
        public void onError(int error) {
        }

        @Override
        public void onResults(Bundle results) {
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.d("tag", "results " + results);
            Log.d("tag", "matches " + matches);
            //System.out.println("results " + results);
            //System.out.println("matches " + matches);
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void doSomething(View view){
        Log.d("tag", "do something start");
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        // http://stackoverflow.com/questions/2209513/how-to-start-activity-in-another-application

        Intent intent = new Intent();
        intent.setComponent(
                new ComponentName(
                        "ee.ioc.phon.android.speak",
                        "ee.ioc.phon.android.speak.activity.SpeechActionActivity"));
        startActivity(intent);
        Log.d("tag", "do something end");
    }

}
