package si.project.speech;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class Navegar {
  private ActionBarActivity activity;
  private String destiny;
  private double latitude;
  private double longitude;
  
  public Navegar(ActionBarActivity activity) {
    this.activity =  activity;
  }
  
  public void loadGPS() {
    LocationManager locManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    latitude = loc.getLatitude();
    longitude = loc.getLongitude();
  }
  
  public void setDestiny(String destiny) {
    this.destiny = destiny.replace(" ", "+");
  }
  
 public void showMap() {
   Uri geo = Uri.parse("geo:" + latitude + "," + longitude);
   Intent intent = new Intent(Intent.ACTION_VIEW);
   intent.setData(geo);
   if (intent.resolveActivity(activity.getPackageManager()) != null) {
     activity.startActivity(intent);
   }
 }
 
 public void showMapDestiny() {
   Uri geo = Uri.parse("geo:0,0?q=" + destiny);
   Intent intent = new Intent(Intent.ACTION_VIEW);
   intent.setData(geo);
   if (intent.resolveActivity(activity.getPackageManager()) != null) {
     activity.startActivity(intent);
   }
 }
}
