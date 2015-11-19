package ee.karlkadalipp.blind;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blind.karl.blind.R;
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

import android.os.Vibrator;

public class ListenTextActivity extends Activity {
    String LOG_TAG = "[ListenTextActivity]";

    private EditText et_main;
    private String et_main_text;

    private Button btn_record;
    private SpeechRecognizer sr;

    private Button btn_speak;
    private MediaPlayer mediaPlayer; // http://stackoverflow.com/questions/8486147/how-can-i-play-a-mp3-without-download-from-the-url
    //private int mediaFileLengthInMilliseconds; // audio duration in ms. Used with getDuration()

    private Vibrator v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listen_text_main);

        et_main = (EditText) findViewById(R.id.editText);
        btn_record = (Button) findViewById(R.id.btn_record);
        btn_speak = (Button) findViewById(R.id.btn_speak);

        String grammar_supporting_server = "ee.ioc.phon.android.speak.service.HttpRecognitionService";
        String continuous_full_duplex_server = "ee.ioc.phon.android.speak.service.WebSocketRecognitionService";
        sr = SpeechRecognizer.createSpeechRecognizer(this, new ComponentName("ee.ioc.phon.android.speak",grammar_supporting_server));
        sr.setRecognitionListener(new listener());

        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE); //this.context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void RecordMic(View view) {
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        sr.startListening(recognizerIntent);
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
    public void TextToSpeechService(View view) {
        new StuffJSON().execute();
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
            et_main_text = et_main.getText().toString();

            btn_speak.setEnabled(false);
            //btn_speak.setBackgroundColor(Color.RED); // http://stackoverflow.com/questions/2173936/how-to-set-background-color-of-a-view
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
            Log.d(LOG_TAG, "[StuffJSON doInBackground] json is: " + json);
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

                String str = "v=6&speech="+ et_main_text; //json; //"some string goes here";
                byte[] outputInBytes = str.getBytes("UTF-8");
                OutputStream os = c.getOutputStream();
                os.write(outputInBytes);
                os.close();

                int HttpResult = c.getResponseCode();
                Log.d(LOG_TAG,"[StuffJSON doInBackground] httpresult is " + Integer.toString(HttpResult));
                if(HttpResult == HttpURLConnection.HTTP_OK){
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            c.getInputStream(),"utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    Log.d(LOG_TAG, "[StuffJSON doInBackground] sb is " + sb.toString());
                }else{
                    Log.d(LOG_TAG,"[StuffJSON doInBackground] sb is "+c.getResponseMessage());
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
            Log.d(LOG_TAG, "[StuffJSON doInBackground] mp3value is " + mp3value);

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

            Log.d(LOG_TAG, "[StuffJSON onPostExecute] mp3 link is " + sound_url);

            try {
                playAudio(sound_url);
                playLocalAudio();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //new DownloadMP3Task()
            //        .execute("http://heli.eki.ee/koduleht/kiisu/synteesitudtekstid/" + mp3value + ".mp3"); // "1511040453140_3995.mp3");
            //btn_speak.setBackgroundColor(Color.WHITE);
            btn_speak.setEnabled(true);
        }
    } // end StuffJSON

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


    class listener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
            btn_record.setEnabled(false); //.setBackgroundColor(Color.RED);
            v.vibrate(100);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(LOG_TAG, "[StuffJSON onBeginningOfSpeech] THIS IS THE BEGINNING");
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
            btn_record.setEnabled(true); //.setBackgroundColor(Color.WHITE);
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
            Log.d(LOG_TAG, "[StuffJSON onResults] results " + results);
            Log.d(LOG_TAG, "[StuffJSON onResults] matches " + matches);
            et_main.setText(matches.get(0).toString());
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {

        }
    }

    public void copyToClipboard(View view){
        String text_from_textarea = et_main.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Blind", text_from_textarea);
        clipboard.setPrimaryClip(clip);
    }

    public void emptyTextArea(View view){
        et_main.setText("");
    }

}