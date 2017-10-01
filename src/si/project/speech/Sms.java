package si.project.speech;

import java.util.Vector;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Sms extends ActionBarActivity {
  private ActionBarActivity activity;
  
  //Lectura sms
  private Vector<String> address;
  private Vector<String> body;
  private int index;
  
  //Enviar sms
  private String name = null;
  private String number = null;
  private String message = null;
  private boolean enviando = false;
  
  private TextView textName;
  private TextView textMessage;
  
  public Sms(ActionBarActivity activity) {
    this.activity = activity;
    index = 0;
    cargarSms();
  }
  
  public void activitySms() {
    activity.setContentView(R.layout.activity_sms);
    textName = (TextView) activity.findViewById(R.id.textView1);
    textMessage = (TextView) activity.findViewById(R.id.tv);
    textName.setText(name + " - " + number);
  }
  
  @SuppressLint("NewApi")
  private void cargarSms() {
    body = new Vector<String>();
    address = new Vector<String>();
    Uri uri = Telephony.Sms.Inbox.CONTENT_URI;
    String[] projection = new String[] {Telephony.Sms.Inbox.BODY, Telephony.Sms.Inbox.ADDRESS};
    Cursor mensajes = activity.getContentResolver().query(uri, projection, null, null, Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);
    int indexBody = mensajes.getColumnIndex(Telephony.Sms.Inbox.BODY);
    int indexAddress = mensajes.getColumnIndex(Telephony.Sms.Inbox.ADDRESS);
    mensajes.moveToFirst();
    do {
      String address = mensajes.getString(indexAddress);
      this.address.add(address);
      String body = mensajes.getString(indexBody);
      this.body.add(body);
    } while(mensajes.moveToNext());
  }
  
  public String[] getSms(int index) {
    String[] sms = null;
    if (index < address.size())
      sms = new String[] {address.get(index), body.get(index)};
    else
      sms = new String[] {null, null};
    return sms;
  }
  
  public boolean isEmpty() {
    boolean empty = false;
    if (address.size() == 0)
      empty = true;
    return empty;
  }
  
  public void sendSms() {
    SmsManager sms = SmsManager.getDefault();
    sms.sendTextMessage(number, null, message, null, null);
  }
  
  public boolean validarContacto() {
    boolean correcto = false;
    
    Vector<String> nombres = new Vector<String>();
    Vector<String> telefonos = new Vector<String>(); 
    Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    ContentResolver cr = activity.getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
    String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER };
    Cursor names = activity.getContentResolver().query(uri, projection, null, null, null);
    int indexName = names.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
    int indexNumber = names.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
    names.moveToFirst();
    do {
      String name   = names.getString(indexName);
      nombres.add(name);
      String number = names.getString(indexNumber);
      telefonos.add(number);
    } while (names.moveToNext());
    
    for (int i = 0; i < nombres.size(); i++) {
      if (nombres.get(i).equalsIgnoreCase(name)) {
        correcto = true;
        number = telefonos.get(i);
      }
    }
    
    return correcto;
  }
  
  public void setTextMessage(String textMessage) {
    this.textMessage.setText(textMessage);
  }
  
  public int getIndex() {
    index++;
    return index - 1;
  }
  
  public void setNumber(String number) {
    this.number = number;
  }
  
  public void setMessage(String message) {
    textMessage.setText(message);
    this.message = message;
  }
  
  public String getNumber() {
    return number;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setEnviando(boolean enviando) {
    this.enviando = enviando;
  }
  
  public boolean isEnviando() {
    return enviando;
  }
}
