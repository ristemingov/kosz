package finki.speech.mingov.com.finkispeech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

/**
 * Created by Riste Mingov
 * email: riste.mingov@gmail.com
 * on 10/23/2015.
 */

public class QuestionActivity extends Activity implements RecognitionListener{
    TextView tvPhrase;

    private static final String KWS_SEARCH = "keyphrase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        tvPhrase = (TextView) findViewById(R.id.tvPhrase);


        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        String KEYPHRASE = appSharedPrefs.getString("KEYPHRASE", "");
        tvPhrase.setText("Say the key phrase to enter the menu!");
        FINKISpeech.getInstance().getRecognizer().addKeyphraseSearch(KWS_SEARCH, FINKISpeech.getInstance().getKeyphrase());
        FINKISpeech.getInstance().getRecognizer().addListener(this);

        startSearch(KWS_SEARCH);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        if (text.equalsIgnoreCase(FINKISpeech.getInstance().getKeyphrase())) {
            FINKISpeech.getInstance().getRecognizer().stop();
            Intent intentForm = new Intent(QuestionActivity.this, MenuActivity.class);
            startActivity(intentForm);
            finish();

        }

//        else {
//            Toast.makeText(getBaseContext(), "You said the wrong phrase", Toast.LENGTH_LONG).show();
//            startSearch(KWS_SEARCH);
//        }
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onTimeout() {
        startSearch(KWS_SEARCH);
    }

    private void startSearch(String searchName) {
        FINKISpeech.getInstance().getRecognizer().stop();
        FINKISpeech.getInstance().getRecognizer().startListening(searchName);
    }
}
