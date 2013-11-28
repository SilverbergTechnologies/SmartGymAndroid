package is.silverberg.smartgymandroid;

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


public class CustomizedListView extends Activity {
  // All static variables
  static final String URL = "http://ej.is/entries.xml";
  // XML node keys
  static final String KEY_ENTRY = "entry"; // parent node
  static final String KEY_ID = "id";
  static final String KEY_CALORIES = "calories";
  static final String KEY_DURATION = "duration";
  static final String KEY_DATE = "date";
  static final String KEY_THUMB_URL = "thumb_url";

  ListView list;
    LazyAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);


    ArrayList<HashMap<String, String>> exerciseList = new ArrayList<HashMap<String, String>>();

    XMLParser parser = new XMLParser();
    String xml = parser.getXmlFromUrl(URL); // getting XML from URL
    Document doc = parser.getDomElement(xml); // getting DOM element

    NodeList nl = doc.getElementsByTagName(KEY_ENTRY);
    // looping through all song nodes <song>
    for (int i = 0; i < nl.getLength(); i++) {
      // creating new HashMap
      HashMap<String, String> map = new HashMap<String, String>();
      Element e = (Element) nl.item(i);
      // adding each child node to HashMap key => value
      map.put(KEY_ID, parser.getValue(e, KEY_ID));
      map.put(KEY_CALORIES, parser.getValue(e, KEY_CALORIES) + " calories");
      map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION) + "min");
      map.put(KEY_DATE, parser.getValue(e, KEY_DATE));
      map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

      // adding HashList to ArrayList
      exerciseList.add(map);
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
		        intent.putExtra("id", KEY_ID); //Optional parameters
		        startActivity(intent);	

			}
		});
    
  }
}
