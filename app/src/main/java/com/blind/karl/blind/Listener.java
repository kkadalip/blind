package com.blind.karl.blind;

import android.app.Activity;
import android.content.Context;
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

    public Listener(Context c, Button mic) {
        context = c;
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE); //this.context.getSystemService(Context.VIBRATOR_SERVICE);

        btnMic = mic;
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

        Toast toast = Toast.makeText(context, matches.get(0).toString(), Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
    }
}
