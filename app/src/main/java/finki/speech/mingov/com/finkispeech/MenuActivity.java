package finki.speech.mingov.com.finkispeech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;

/**
 * Created by Riste Mingov
 * email: riste.mingov@gmail.com
 * on 10/23/2015.
 */

public class MenuActivity extends Activity implements RecognitionListener {

    private static final String KWS_MENU = "menu";
    private static final String DIGITS_SEARCH = "digits";
    private static final String FORECAST_SEARCH = "forecast";
    private static final String EXIT = "exit";
    private static final String CHANGE = "change";
    private static final String BACK = "back";
    private String currentSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        setUpGramars();

        FINKISpeech.getInstance().getRecognizer().addListener(this);
        switchSearch(KWS_MENU);
        currentSearch = KWS_MENU;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FINKISpeech.getInstance().getRecognizer().cancel();
        FINKISpeech.getInstance().getRecognizer().shutdown();
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
        if (text.equalsIgnoreCase(DIGITS_SEARCH)) {
            switchSearch(DIGITS_SEARCH);
            currentSearch = DIGITS_SEARCH;
            ((TextView) findViewById(R.id.tvDescription)).setText(R.string.digits_description);
            ((TextView) findViewById(R.id.tvFirst)).setText("");
            ((TextView) findViewById(R.id.tvSeccond)).setText("");
            ((TextView) findViewById(R.id.tvThird)).setText("");
            ((TextView) findViewById(R.id.tvFourth)).setText("");
        }
        else if (text.equalsIgnoreCase(FORECAST_SEARCH)) {
            switchSearch(FORECAST_SEARCH);
            currentSearch = FORECAST_SEARCH;
            ((TextView) findViewById(R.id.tvDescription)).setText(R.string.forecast_description);
            ((TextView) findViewById(R.id.tvFirst)).setText("");
            ((TextView) findViewById(R.id.tvSeccond)).setText("");
            ((TextView) findViewById(R.id.tvThird)).setText("");
            ((TextView) findViewById(R.id.tvFourth)).setText("");
        }
        else if (text.equalsIgnoreCase(BACK)) {
            switchSearch(KWS_MENU);
            currentSearch = KWS_MENU;
            //setUpMenu();
        }
        else if (text.equalsIgnoreCase(EXIT)) {
            FINKISpeech.getInstance().getRecognizer().stop();
            finish();
            System.exit(0);
        }
        else if (text.equalsIgnoreCase(CHANGE)) {
            FINKISpeech.getInstance().getRecognizer().stop();
            Intent intentForm = new Intent(MenuActivity.this, FormActivity.class);
            startActivity(intentForm);
            finish();
        }
        else {
            Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

            switchSearch(currentSearch);

            //setUpMenu();
        }

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
        switchSearch(KWS_MENU);
        currentSearch = KWS_MENU;

    }

    private void switchSearch(String searchName) {
        FINKISpeech.getInstance().getRecognizer().stop();
        if (!searchName.equalsIgnoreCase(FORECAST_SEARCH))
            FINKISpeech.getInstance().getRecognizer().startListening(searchName);
        else
            FINKISpeech.getInstance().getRecognizer().startListening(searchName,5000);
        if(searchName.equalsIgnoreCase(KWS_MENU))
            setUpMenu();

    }

    private void setUpGramars(){
        try {
            Assets assets = new Assets(MenuActivity.this);
            File assetDir = assets.syncAssets();
            File menuGrammar = new File(assetDir, "menu.gram");
            FINKISpeech.getInstance().getRecognizer().addGrammarSearch(KWS_MENU, menuGrammar);

            // Create grammar-based search for digit recognition
            File digitsGrammar = new File(assetDir, "digits.gram");
            FINKISpeech.getInstance().getRecognizer().addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

            // Create language model search
            File languageModel = new File(assetDir, "weather.dmp");
            FINKISpeech.getInstance().getRecognizer().addNgramSearch(FORECAST_SEARCH, languageModel);
        }catch (Exception e){
            Toast.makeText(getBaseContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void setUpMenu(){

        ((TextView) findViewById(R.id.tvDescription)).setText(R.string.menu_description);
        ((TextView) findViewById(R.id.tvFirst)).setText(R.string.digits);
        ((TextView) findViewById(R.id.tvSeccond)).setText(R.string.forecast);
        ((TextView) findViewById(R.id.tvThird)).setText(R.string.change);
        ((TextView) findViewById(R.id.tvFourth)).setText(R.string.exit);

    }
}
