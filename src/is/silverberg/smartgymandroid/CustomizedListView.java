package is.silverberg.smartgymandroid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Activity for displaying and interacting with workout listview
 * @author Einar Jóhansson
 *
 */
public class CustomizedListView extends Activity {
	
	static final String KEY_ID = "id";	
	static final String KEY_THUMB_URL = "thumb_url";
	static final String KEY_DATE = "create-date";
	static final String KEY_CALORIES = "calories";
	static final String KEY_DURATION = "elapsed-time";
	int count;
	String values;
	XMLParser parser = new XMLParser();
	
	ListView list;
    LazyAdapter adapter;

  @Override
  /**
   *  Called when Activity is opened
   */
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

	DBHandler dbHandler = new DBHandler(this, null, null, 1);
    count = dbHandler.getWorkoutCount();
    ArrayList<HashMap<String, String>> exerciseList = new ArrayList<HashMap<String, String>>();
    
    for( int k = 1 ; k <= count ; k++ ) {
    	values = dbHandler.getWorkoutData(k);
    	Document doc = parser.getDomElement(values);
    	NodeList nl = doc.getElementsByTagName("workout");
	    
    	String caloriesString = "0";
	    String dateString = "0";
	    String durationString = "0";
	    DecimalFormat df = new DecimalFormat("#.##");
	    for( int i = 0 ; i < nl.getLength() ; i++ ) {
	    	Element e = (Element) nl.item(i);
	    	caloriesString = parser.getValue(e, KEY_CALORIES);
	    	dateString = parser.getValue(e, KEY_DATE);
	    	durationString = parser.getValue(e, KEY_DURATION);
	    	Double dur = Double.parseDouble(durationString);
	    	dur = dur/60;
	    	durationString = String.valueOf(df.format(dur));
	    
		      HashMap<String, String> map = new HashMap<String, String>();
		      map.put(KEY_ID, String.valueOf(k));
		      map.put(KEY_CALORIES, caloriesString + " calories");
		      map.put(KEY_DURATION, durationString + " minutes");
		      map.put(KEY_DATE, dateString.substring(8,10)+"/"+dateString.substring(5,7));
		      map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
		      exerciseList.add(map);
	    }	    
    }

    list=(ListView)findViewById(R.id.list);

    // Getting adapter by passing xml data ArrayList
        adapter=new LazyAdapter(this, exerciseList);
        list.setAdapter(adapter);


        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		        Intent intent = new Intent(CustomizedListView.this, ExerciseActivity.class);
		        intent.putExtra("id", String.valueOf(position+1)); //Optional parameters
		        startActivity(intent);	

			}
		});
    
  }
  
}
