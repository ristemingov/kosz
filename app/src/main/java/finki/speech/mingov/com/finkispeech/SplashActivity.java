package finki.speech.mingov.com.finkispeech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

/**
 * Created by Riste Mingov
 * email: riste.mingov@gmail.com
 * on 10/23/2015.
 */

public class SplashActivity extends Activity {

    private SpeechRecognizer recognizer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task

        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(SplashActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    Toast.makeText(getBaseContext(), "Setup failed "+ result, Toast.LENGTH_LONG).show();

                } else {
                    SharedPreferences appSharedPrefs = PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

                    String KEYPHRASE = appSharedPrefs.getString("KEYPHRASE", "");
                    // if pass phrase does not exist
                    if(KEYPHRASE.equalsIgnoreCase("")) {
                        Intent intentForm = new Intent(SplashActivity.this, FormActivity.class);
                        startActivity(intentForm);
                    }
                    // else
                    else {
                        FINKISpeech.getInstance().setKeyphrase(KEYPHRASE);
                        Intent intentQuestion = new Intent(SplashActivity.this, QuestionActivity.class);
                        startActivity(intentQuestion);
                    }
                }
            }
        }.execute();

    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

         recognizer = defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                        // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setRawLogDir(assetsDir)

                        // Threshold to tune for keyphrase to balance between false alarms and misses
                .setKeywordThreshold(1e-45f)

                        // Use context-independent phonetic search, context-dependent is too slow for mobile
                .setBoolean("-allphone_ci", true)

                .getRecognizer();



        FINKISpeech.getInstance().setRecognizer(recognizer);
    }

}
