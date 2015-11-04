package com.blind.karl.blind;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener { //AppCompatActivity
    private SpeechRecognizer sr;
    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText et1;
    private String text_from_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sr = SpeechRecognizer.createSpeechRecognizer(this,
                new ComponentName(
                        "ee.ioc.phon.android.speak",
                        "ee.ioc.phon.android.speak.service.HttpRecognitionService"));
        // ee.ioc.phon.android.speak.service.HttpRecognitionService (uses the grammar-supporting server)
        // ee.ioc.phon.android.speak.service.WebSocketRecognitionService (uses the continuous full-duplex server)
        sr.setRecognitionListener(new listener()); // this

        et1 = (EditText) findViewById(R.id.editText1);
        et1.setText("proovitekst tuleb siia jajaja");
        btnSpeak = (Button) findViewById(R.id.btn3);
    }

    public void onResume (){
        super.onResume();
        tts = new TextToSpeech(this, this);
    }

    @Override
    public void onInit(int status) {
        //peale keeleseadete muutmist tuleks uuesti kontrollida.

        if (status == TextToSpeech.SUCCESS) {
//            tts.setLanguage(Locale.US);
            int result = tts.setLanguage(new Locale("est","EST"));
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
                btnSpeak.setEnabled(false);
            } else {
                btnSpeak.setEnabled(true);
                //speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    public void doSomething2(View view) {
        Log.d("tag", "do something 2 start");

        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        /*SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(this,
                new ComponentName(
                        "ee.ioc.phon.android.speak",
                        "ee.ioc.phon.android.speak.service.HttpRecognitionService"));*/
        sr.startListening(recognizerIntent);
        //startActivity(intent);
        Log.d("tag", "do something 2 end");
    }

    public void doSomething3(View view) {
        String textToRead = et1.getText().toString();
        Log.d("tag", "text to read is " + textToRead);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ttsGreater21(textToRead);
        } else {
            ttsUnder20(textToRead);
        }
    }

    // EKI veebiserverit kasutades tekst heliks:
    // POST päring: (nt Chrome'i Postman app)
    // http://heli.eki.ee/koduleht/kiisu/koduleht.php
    // v 6
    // JSON POST application/x-www-form-urlencoded
    // speech misiganes
    // tagasi saab tulemusena wav/mp3 id
    // tulemus asub aadressil http://heli.eki.ee/koduleht/kiisu/synteesitudtekstid/123456.mp3
    //http://stackoverflow.com/questions/6218143/how-to-send-post-request-in-json-using-httpclient
    public void doSomething4(View view) {
        new StuffJSON().execute();
    }


    @SuppressWarnings("deprecation")
    private void ttsUnder20(String text) {
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "MessageId");
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, map);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ttsGreater21(String text) {
        String utteranceId=this.hashCode() + "";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
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
            List<String> matches=new ArrayList<String>();
            if ((results != null) && results.containsKey(SpeechRecognizer.RESULTS_RECOGNITION)) {
                matches=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            }
            Log.d("tag", "results " + results);
            Log.d("tag", "matches " + matches);
            EditText editText = (EditText) findViewById(R.id.editText1);
            editText.setText(matches.get(0).toString());
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

    // http://stackoverflow.com/questions/25647881/android-asynctask-example-and-explanation/25647882#25647882

    // Esimene parameeter tuleb executest, läheb doInBackgroundi nt params[0]
    // Teine parameeter läheb doInBackGroundi publishProgress(i)-st onProgressUpdate'i
    // Kolmas parameeter läheb doInBackground returnist onPostExecute'i
    private class StuffJSON extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Do something like display a progress bar
            text_from_et = et1.getText().toString();
        }

        // This is run in a background thread
        @Override
        protected String doInBackground(String... params) {
            // get the string from params, which is an array
            //String myString = params[0];

            // Do something that takes a long time, for example:
/*
            for (int i = 0; i <= 100; i++) {
                // Do things
                // Call this to update your progress
                publishProgress(i);
            }
*/
            HashMap myParams = new HashMap();
            myParams.put("v", "6");
            myParams.put("speech", "hobune lammas orkester test 1234 2");
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(myParams);
            Log.d("tag", "json is: " + json);


            StringBuilder sb = new StringBuilder();

            HttpURLConnection c = null;
            //DataOutputStream printout = null;
            OutputStreamWriter printout = null;
            DataInputStream input;
            try {
                //URL url = new URL(path); //(getCodeBase().toString() + "env.tcgi");
                String request = "http://heli.eki.ee/koduleht/kiisu/koduleht.php";
                URL url = new URL (request); // URL url = new URL(strings[0]);
                c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                //c.setRequestProperty("Content-Type", "application/json");
                c.setRequestProperty("Host", "heli.eki.ee");
                //c.setRequestProperty("Content-Length", "" + Integer.toString(postData.getBytes().length));

                c.setDoOutput(true);
                c.setDoInput(true);
                c.setUseCaches(false);

                c.connect();

                //http://stackoverflow.com/questions/20020902/android-httpurlconnection-how-to-set-post-data-in-http-body

                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                try {
                    jsonParam.put("v", "6");
                    jsonParam.put("speech", "proov");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Send POST output.
                //printout = new DataOutputStream(c.getOutputStream());

//                printout = new OutputStreamWriter(c.getOutputStream());
//                Log.d("tag","json on " + printout);
//                printout.write(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
//                printout.flush();
//                printout.close();

                String str = "v=6&speech="+text_from_et; //json; //"some string goes here";
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = c.getOutputStream();
                os.write(outputInBytes);
                os.close();

                int HttpResult = c.getResponseCode();
                Log.d("tag","httpresult is " + Integer.toString(HttpResult));
                if(HttpResult == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            c.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.d("tag", "sb is " + sb.toString());
                }else{
                    Log.d("tag","sb is "+c.getResponseMessage());
                    System.out.println("swag");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(c!=null)
                    c.disconnect();
            }

            //return null;
            return "this string is passed to onPostExecute";
        } // executega kaasas, progressupdate return, onpostexecute parameeter

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Do things like hide the progress bar or change a TextView
        }
    } // end StuffJSON

}

/*
        HttpURLConnection httpcon;
        String data = null;
        String result = null;
        try{
//Connect
            httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

//Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

//Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/