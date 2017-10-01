package si.project.speech;

import java.util.Locale;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class GeneradorTexto {
  private TextToSpeech tts;
  private ActionBarActivity activity;
  
  public GeneradorTexto(ActionBarActivity activity) {
    this.activity = activity;
  }
  
  protected void onCreate(Bundle savedInstanceState) {
    tts = new TextToSpeech(activity, null);
  }
  
  public void onInit(int status) {
    if ( status == TextToSpeech.SUCCESS ) {
      int result = tts.setLanguage( Locale.getDefault() );
      if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
        Log.e("TTS", "This Language is not supported");
      } 
    } 
    else {
      Log.e("TTS", "Initilization Failed!");
    }
  }
  
  public void playText(String texto) {
    tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null );
  }
  
  public boolean isSpeaking() {
    return tts.isSpeaking();
  }
}
