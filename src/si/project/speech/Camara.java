package si.project.speech;

import java.io.File;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;

public class Camara extends ActionBarActivity{
  private ImageView foto;
  private ActionBarActivity activity;
  
  public Camara(ActionBarActivity activity) {
    this.activity = activity;
  }
  
  public void hacerFoto() {
    Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    File imagesFolder = new File(Environment.getExternalStorageDirectory(), "DCIM/CAMERA");
    imagesFolder.mkdirs();   

    Time time = new Time();
    time.setToNow();
    File image = new File(imagesFolder, Long.toString(time.toMillis(false)) + ".jpg"); 
    Uri uriSavedImage = Uri.fromFile(image);
    camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
    activity.startActivityForResult(camaraIntent, 2);
  }
}
