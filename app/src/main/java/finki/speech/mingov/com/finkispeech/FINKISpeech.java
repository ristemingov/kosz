package finki.speech.mingov.com.finkispeech;

import java.io.File;

import edu.cmu.pocketsphinx.SpeechRecognizer;

/**
 * Created by Riste Mingov
 * email: riste.mingov@gmail.com
 * on 10/23/2015.
 */

public class FINKISpeech {

    private static FINKISpeech sinInstance= new FINKISpeech();

    public static FINKISpeech getInstance() {
        return sinInstance;
    }

    private FINKISpeech() {

    }

    private File menuGrammar;
    private File digitsGrammar;
    private File languageModel;
    private File phoneticModel;
    private SpeechRecognizer recognizer;
    private String Keyphrase;


    public File getMenuGrammar() {
        return menuGrammar;
    }

    public void setMenuGrammar(File menuGrammar) {
        this.menuGrammar = menuGrammar;
    }

    public File getDigitsGrammar() {
        return digitsGrammar;
    }

    public void setDigitsGrammar(File digitsGrammar) {
        this.digitsGrammar = digitsGrammar;
    }

    public File getLanguageModel() {
        return languageModel;
    }

    public void setLanguageModel(File languageModel) {
        this.languageModel = languageModel;
    }

    public File getPhoneticModel() {
        return phoneticModel;
    }

    public void setPhoneticModel(File phoneticModel) {
        this.phoneticModel = phoneticModel;
    }

    public SpeechRecognizer getRecognizer() {
        return recognizer;
    }

    public void setRecognizer(SpeechRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    public static FINKISpeech getSinInstance() {
        return sinInstance;
    }

    public static void setSinInstance(FINKISpeech sinInstance) {
        FINKISpeech.sinInstance = sinInstance;
    }

    public String getKeyphrase() {
        return Keyphrase;
    }

    public void setKeyphrase(String keyphrase) {
        Keyphrase = keyphrase;
    }
}
