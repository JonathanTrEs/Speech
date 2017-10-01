package si.project.speech;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;


public class Alarma {
  private ActionBarActivity activity;
  private String message;
  private int hour = -1;
  private int minutes = -1;
  private HashMap<String, Integer> vocabulary;
  
  public Alarma(ActionBarActivity activity) {
    this.activity = activity;
    createVocabulary();
  }
  
  public void createAlarm() {
    Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
    .putExtra(AlarmClock.EXTRA_HOUR, hour)
    .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
     if (intent.resolveActivity(activity.getPackageManager()) != null) {
       activity.startActivity(intent);
     }
     resetHour();
  }
  
  public void parserHour(String result) {
    String[] tokens = result.split(" ");
    for (int i = 0; i < tokens.length; i++) {
      if (vocabulary.containsKey(tokens[i])) 
        if (hour == -1)
          hour = vocabulary.get(tokens[i]);
        else if (minutes == -1)
          minutes = vocabulary.get(tokens[i]);
    }
  }
  
  private void resetHour() {
    hour = -1;
    minutes = -1;
  }
  
  public void createVocabulary() {
    vocabulary = new HashMap<String, Integer>();
    vocabulary.put("una", 1);
    vocabulary.put("dos", 2);
    vocabulary.put("tres", 3);
    vocabulary.put("cuatro", 4);
    vocabulary.put("cinco", 5);
    vocabulary.put("seis", 6);
    vocabulary.put("siete", 7);
    vocabulary.put("ocho", 8);
    vocabulary.put("nueve", 9);
    vocabulary.put("diez", 10);
    vocabulary.put("once", 11);
    vocabulary.put("doce", 12);
    vocabulary.put("1", 1);
    vocabulary.put("2", 2);
    vocabulary.put("3", 3);
    vocabulary.put("4", 4);
    vocabulary.put("5", 5);
    vocabulary.put("6", 6);
    vocabulary.put("7", 7);
    vocabulary.put("8", 8);
    vocabulary.put("9", 9);
    vocabulary.put("10", 10);
    vocabulary.put("X", 10);
    vocabulary.put("11", 11);
    vocabulary.put("12", 12);
    vocabulary.put("13", 13);
    vocabulary.put("14", 14);
    vocabulary.put("15", 15);
    vocabulary.put("16", 16);
    vocabulary.put("17", 17);
    vocabulary.put("18", 18);
    vocabulary.put("19", 19);
    vocabulary.put("20", 20);
    vocabulary.put("21", 21);
    vocabulary.put("22", 22);
    vocabulary.put("23", 23);
    vocabulary.put("24", 24);
    vocabulary.put("25", 25);
    vocabulary.put("26", 26);
    vocabulary.put("27", 27);
    vocabulary.put("28", 28);
    vocabulary.put("29", 29);
    vocabulary.put("30", 30);
    vocabulary.put("31", 31);
    vocabulary.put("32", 32);
    vocabulary.put("33", 33);
    vocabulary.put("34", 34);
    vocabulary.put("35", 35);
    vocabulary.put("36", 36);
    vocabulary.put("37", 37);
    vocabulary.put("38", 38);
    vocabulary.put("39", 39);
    vocabulary.put("40", 40);
    vocabulary.put("41", 41);
    vocabulary.put("42", 42);
    vocabulary.put("43", 43);
    vocabulary.put("44", 44);
    vocabulary.put("45", 45);
    vocabulary.put("46", 46);
    vocabulary.put("47", 47);
    vocabulary.put("48", 48);
    vocabulary.put("49", 49);
    vocabulary.put("50", 50);
    vocabulary.put("51", 51);
    vocabulary.put("52", 52);
    vocabulary.put("53", 53);
    vocabulary.put("54", 54);
    vocabulary.put("55", 55);
    vocabulary.put("56", 56);
    vocabulary.put("57", 57);
    vocabulary.put("58", 58);
    vocabulary.put("59", 59);
    vocabulary.put("60", 0);
  }
}
