package com.blind.karl.blind;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener { //AppCompatActivity

    private Button btnSpeak;

    private EditText edit_text_1;
    private String edit_text_1_text;

    private SpeechRecognizer sr;
    private TextToSpeech tts;

    private MediaPlayer mediaPlayer; // http://stackoverflow.com/questions/8486147/how-can-i-play-a-mp3-without-download-from-the-url
    //private int mediaFileLengthInMilliseconds; // audio duration in ms. Used with getDuration()

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

        String grammar_supporting_server = "ee.ioc.phon.android.speak.service.HttpRecognitionService";
        String continuous_full_duplex_server = "ee.ioc.phon.android.speak.service.WebSocketRecognitionService";
        sr = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName("ee.ioc.phon.android.speak",grammar_supporting_server));
        sr.setRecognitionListener(new listener()); // this

        edit_text_1 = (EditText) findViewById(R.id.editText1);
        edit_text_1.setText("proovitekst tuleb siia jajaja");
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
        String textToRead = edit_text_1.getText().toString();
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

    public void doSomething5(View view) {
        startNewActivity(this, "com.android.calculator2");
    }

    public void swapScreen(View veiw){
        Intent intent = new Intent(this, ListenText.class);
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

        killMediaPlayer();
    }

    private void killMediaPlayer() {
        if(mediaPlayer!=null) {
            try {
                mediaPlayer.release();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
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
            edit_text_1_text = edit_text_1.getText().toString();
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

                String str = "v=6&speech="+ edit_text_1_text; //json; //"some string goes here";
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

            //Gson gson2 = new GsonBuilder().create();
            //JSONObject json2 = gson.fromJson(myParams);
            //String json2 = gson.toJson(myParams);
            //JSONObject JSONresult = gson.toJson(sb);

            JsonParser parser = new JsonParser();
            JsonObject o = (JsonObject)parser.parse(sb.toString()); //"{\"a\": \"A\"}"

            JsonElement jsonElement = o.get("mp3");
            String mp3value = jsonElement.getAsString();
            //String mp3value = o.get("mp3").toString();
            //String mp3value = o.get("mp3").toString();
            Log.d("tag", "mp3value is " + mp3value);

            //return null;
            return mp3value; //"this string is passed to onPostExecute";
        } // executega kaasas, progressupdate return, onpostexecute parameeter

        // This is called from background thread but runs in UI
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Do things like update the progress bar
        }

        // This runs in UI when background thread finishes
        @Override
        protected void onPostExecute(String mp3value) {
            super.onPostExecute(mp3value);
            // Do things like hide the progress bar or change a TextView
            StringBuilder sb_sound_url = new StringBuilder();
            sb_sound_url.append("http://heli.eki.ee/koduleht/kiisu/synteesitudtekstid/");
            sb_sound_url.append(mp3value);
            sb_sound_url.append(".mp3");
            String sound_url = sb_sound_url.toString();

            //String sound_url = String.format("http://heli.eki.ee/koduleht/kiisu/synteesitudtekstid/", Uri.encode(mp3value), Uri.encode(".mp3"));

            Log.d("tag", "mp3 link is " + sound_url);

            try {
                playAudio(sound_url);
                playLocalAudio();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //new DownloadMP3Task()
            //        .execute("http://heli.eki.ee/koduleht/kiisu/synteesitudtekstid/" + mp3value + ".mp3"); // "1511040453140_3995.mp3");
        }
    } // end StuffJSON

    private void playAudio(String url) throws Exception
    {
        killMediaPlayer();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    private void playLocalAudio() throws Exception
    {
        //mediaPlayer = MediaPlayer.create(this, R.raw.music_file);
        mediaPlayer.start();
    }


/*    private class DownloadMP3Task extends AsyncTask<String, Integer, File> {
        protected File doInBackground(String... urls) {
            File out = null;
            for (int i = 0; i < 4; i++) {
                System.out.println("[Jama] prooviL ," + urls[0]);
                out = HTTPtoFile(urls[0]);
                if (out != null)
                    break;
            }
            return out;
        }

        protected void onProgressUpdate(Integer... progress) {
            // setProgressPercent(progress[0]);
        }

        protected void onPostExecute(File result) {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            playMp3(result);
            SetBtns();
            downloading = false;
        }
    }

    private synchronized File HTTPtoFile(String urla) {
        try {
            URL url = new URL(urla);

            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestMethod("GET");
            // urlConnection.setDoOutput(true);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            // int totalSize = urlConnection.getContentLength();
            pref_jarg = urlConnection.getHeaderFieldInt("Nupp", 0);

            SharedPreferences.Editor prefsave = prefs.edit();
            prefsave.putInt("jarg", pref_jarg);
            prefsave.commit();

            byte[] buffer = new byte[1024];
            inputStream.read(buffer);
            System.out.println("[----] HTTP TO FILE = ," + getCacheDir());
            File tempMp3 = File.createTempFile("news", "mp3", getCacheDir());

            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            int len1;
            while ((len1 = inputStream.read(buffer)) > 0) {
                fos.write(buffer, 0, len1);
            }

            fos.close();
            inputStream.close();
            return tempMp3;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Jama] HTTP TO FILE = NULL ," + urla);
        return null;
    }

    private void playMp3(File tempMp3) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // teata kasutajat ka...
        try {
            mediaPlayer = new MediaPlayer();
            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            //textView.setText(getString(R.string.main_loaded));
            if (playing) {
                // mediaPlayer.seekTo(0);
                mediaPlayer.start();
                handler.removeCallbacks(UpdatePlayerStats);
                handler.postDelayed(UpdatePlayerStats, 100);
                wl.acquire();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }*/


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