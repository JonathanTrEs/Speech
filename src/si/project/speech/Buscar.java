package si.project.speech;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;

public class Buscar {
  private ActionBarActivity activity;
  private String query;
  
  public Buscar(ActionBarActivity activity) {
    this.activity = activity;
  }
  
  public void searchWeb() {
    Intent intent = new Intent(Intent.ACTION_SEARCH);
    intent.putExtra(SearchManager.QUERY, query);
    if (intent.resolveActivity(activity.getPackageManager()) != null) {
      activity.startActivity(intent);
    }
  }
  
  public void setQuery(String query) {
    this.query = query;
  }
}
