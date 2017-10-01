package si.project.speech;

import java.util.Vector;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class Llamar extends ActionBarActivity{
  private Vector<String> nombres;
  private Vector<String> telefonos;
  private ActionBarActivity activity;
  
  public Llamar(ActionBarActivity activity) {
    this.activity = activity;
    getNameNumber();
  }
  
  private void getNameNumber(){ 
    nombres = new Vector<String>();
    telefonos = new Vector<String>(); 
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
  }
  
  public String getNumber(String name) {
    String number = "null";
    for (int i = 0; i < nombres.size(); i++) 
      if (nombres.get(i).equalsIgnoreCase(name))
        number = telefonos.get(i);
    return number;
  }
  
  public void realizarLlamada(final String numero) {
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+numero)); 
        //Realizamos la llamada
        activity.startActivity(callIntent);    
      }
    }, 2000);
  }
}
