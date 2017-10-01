package si.project.speech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class Speech {
  private ActionBarActivity activity;
  private int code;
  private int subCode;
  private boolean resultOK = false;
  private String[] result;
  private String message = "¿Qué desea hacer?";
  private boolean otherResult = false;
  private HashMap<String, int[] > vocabulary;
  
  public Speech(ActionBarActivity activity) {
    this.activity = activity;
    code = 0;
    subCode = 0;
    createVocabulary();
  }
  
  public void startVoiceRecognitionActivity() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);  
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, message);
    activity.startActivityForResult(intent, 1);
   }
  
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if(requestCode == 1 && resultCode == -1) {
      resultOK = true;
      otherResult = false;
      ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
      result = matches.get(0).toString().split(" ");
      String parse = parseString(matches.get(0));
      if (vocabulary.containsKey(parse)) {
        int[] codes = vocabulary.get(parse);
        code = codes[0];
        subCode = codes[1];
      }
      else {
        otherResult = true;
        Log.e("TRUE", getOtherResult());
      }
      
      Log.e("code", code + "");
      Log.e("subCode", subCode + "");
        
    }
    else
      resultOK = false;
  }
  
  public String getOtherResult() {
    String aux = "";
    for (int i = 0; i < result.length; i++) {
      if (i < (result.length - 1))
        aux += result[i] + " ";
      else
        aux += result[i];
    }
    otherResult = false;
    return aux;
  }
  
  public void createVocabulary() {
    vocabulary = new HashMap<String, int[]>();
    vocabulary.put("llamar",            new int[] {1, 2});
    vocabulary.put("realizar llamada",  new int[] {1, 2});
    vocabulary.put("llamar a alguien",  new int[] {1, 2});
    vocabulary.put("llamar a",          new int[] {1, 1});
    vocabulary.put("foto",              new int[] {2, 0});
    vocabulary.put("sacar foto",        new int[] {2, 0});
    vocabulary.put("sacar una foto",    new int[] {2, 0});
    vocabulary.put("cámara",            new int[] {2, 0});
    vocabulary.put("sms",               new int[] {3, 1});
    vocabulary.put("mensaje",           new int[] {3, 1});
    vocabulary.put("enviar sms",        new int[] {3, 2});
    vocabulary.put("enviar mensaje",    new int[] {3, 2});
    vocabulary.put("escribir sms",      new int[] {3, 2});
    vocabulary.put("escribir mensaje",  new int[] {3, 2});
    vocabulary.put("leer sms",          new int[] {3, 1});
    vocabulary.put("leer mensajes",     new int[] {3, 1});
    vocabulary.put("si",                new int[] {3, 1});
    vocabulary.put("No",                new int[] {3, 0});
    vocabulary.put("Aceptar",           new int[] {3, 5});
    vocabulary.put("cancelar",          new int[] {3, 6});
    vocabulary.put("alarma",            new int[] {4, 1});
    vocabulary.put("poner alarma",      new int[] {4, 1});
    vocabulary.put("poner una alarma",  new int[] {4, 1});
    vocabulary.put("poner despertador", new int[] {4, 1});
    vocabulary.put("Maps",              new int[] {5, 1});
    vocabulary.put("mapa",              new int[] {5, 1});
    vocabulary.put("abrir Maps",        new int[] {5, 1});
    vocabulary.put("abrir mapa",        new int[] {5, 1});
    vocabulary.put("navegar",           new int[] {5, 2});
    vocabulary.put("buscar",            new int[] {6, 1});
  }
  
  public String parseString(String parse) {
    String[] tokens = parse.split(" ");
    String result = "";
     if (tokens[0].equalsIgnoreCase("llamar") && tokens.length > 1) {
       if (tokens[1].equalsIgnoreCase("a")) {
         result = tokens[0] + " " + tokens[1];
       }
     }
     else if (tokens[0].equalsIgnoreCase("reproducir") && tokens.length > 1) {
       result = tokens[0];
       otherResult = true;
     }
     else if (tokens[0].equalsIgnoreCase("buscar") && tokens.length > 1) {
       result = tokens[0];
       otherResult = true;
     }
     else 
       result = parse;
       
     return result;
  }
  
  public String parseName() {
    String result = "";
    for (int i = 2; i < this.result.length; i++) {
      if (i == this.result.length - 1)
        result += this.result[i];
      else
        result += this.result[i] + " ";
    }
    return result;
  }
  
  public String parseSearch() {
    String result = "";
    int index = 0;
    if (this.result[0].equalsIgnoreCase("buscar"))
      index = 1;
    for (int i = index; i < this.result.length; i++) {
      if (i == this.result.length - 1)
        result += this.result[i];
      else
        result += this.result[i] + " ";
    }
    return result;
  }
  
  public int getCode() {
    return code;
  }
  
  public void setCode(int code) {
    this.code = code;
  }
  
  public int getSubCode() {
    return subCode;
  }
  
  public void setSubCode(int subCode) {
    this.subCode = subCode;
  }
    
  public boolean isResultOK() {
    return resultOK;
  }
  
  public String[] getResult() {
    return result;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public boolean isOtherResult() {
    return otherResult;
  }
  
  public void resetCodes() {
    code = 0;
    subCode = 0;
  }
  
  public void resetSubcode() {
    subCode = 0;
  }
}
