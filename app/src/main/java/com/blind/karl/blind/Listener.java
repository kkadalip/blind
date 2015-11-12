package com.blind.karl.blind;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Listener implements RecognitionListener {
    Vibrator v;
    Context context;
    Button btnMic;

    List<Button> buttonsList;
    //String packageForIntent;

    public Listener(Context c, Button mic, List<Button> bl) { //String s) { //  List<Button> buttonsList
        context = c;
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE); //this.context.getSystemService(Context.VIBRATOR_SERVICE);

        btnMic = mic;

        //packageForIntent = s;

        buttonsList = bl;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        btnMic.setEnabled(false); //.setBackgroundColor(Color.RED);
        v.vibrate(100);
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
        btnMic.setEnabled(true); //.setBackgroundColor(Color.WHITE);
        v.vibrate(100);
    }

    @Override
    public void onError(int error) {
    }

    @Override
    public void onResults(Bundle results) {
        List<String> matches=new ArrayList<String>();
        if ((results != null) && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
            matches=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        }
        Log.d("tag", "results " + results);
        Log.d("tag", "matches " + matches);

        String result = "";
        if(matches.size() > 0){
            result = matches.get(0).toString();
        }else{
            Log.d("log","NO MATCHES IN LISTENER CLASS");
        }
        Toast toast = Toast.makeText(context, result, Toast.LENGTH_LONG);
        toast.show();

        //context.getApplicationContext().
        //String packageForIntent = "empty";

        for(Button b : buttonsList){
            String button_text = b.getText().toString();
            Log.d("log", "Button text is " + button_text);
            if(button_text.equalsIgnoreCase(result)){
                Log.d("log", " SUCCESS, FOUND MATCHING BUTTON");
                SharedPreferences settings = context.getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                String btnId = context.getResources().getResourceEntryName(b.getId());
                String btnPackage = settings.getString(btnId + "_package", "None");

                Log.d("log", "package for intent is " + btnPackage);
                if(!btnPackage.equals("empty")){
                    PackageManager pm = context.getPackageManager();
                    Intent intent = pm.getLaunchIntentForPackage(btnPackage);
                    Log.d("log", "end of speech, starting intent for " + btnPackage);
                    context.startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }
}
