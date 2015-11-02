package finki.speech.mingov.com.finkispeech;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.cmu.pocketsphinx.SpeechRecognizer;

/**
 * Created by Riste Mingov
 * email: riste.mingov@gmail.com
 * on 10/23/2015.
 */

public class FormActivity extends Activity implements View.OnClickListener {

    EditText etKeyphrase;
    Button btnSave;
    private SpeechRecognizer recognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        etKeyphrase = (EditText) findViewById(R.id.etKeyphrase);
        btnSave = (Button) findViewById(R.id.btnSave);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        String KEYPHRASE = appSharedPrefs.getString("KEYPHRASE", "");

        etKeyphrase.setText(KEYPHRASE);

        btnSave.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                if (!etKeyphrase.getText().toString().equalsIgnoreCase("")) {



                    SharedPreferences appSharedPrefs = PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                    prefsEditor.putString("KEYPHRASE", etKeyphrase.getText().toString());
                    prefsEditor.commit();
                    FINKISpeech.getInstance().setKeyphrase(etKeyphrase.getText().toString());
                    Intent intentQuestion = new Intent(FormActivity.this, SplashActivity.class);
                    startActivity(intentQuestion);
                    finish();


                    //Toast.makeText(getBaseContext(), "Save clicked", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getBaseContext(), "Text should not be empty", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
