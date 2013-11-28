package is.silverberg.smartgymandroid;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * Class for displaying activity and displaying exercise data.
 * UNFINISHED, Contains test data at the moment. 
 * @author Einar Jóhannssson
 *
 */
public class ExerciseActivity extends Activity {

	static final String KEY_CALORIES = "calories";
	static final String KEY_TIME = "elapsed-time";
	static final String KEY_AVSPEED = "average-speed";
	static final String KEY_DISTANCE = "distance";
	static final String KEY_TIMEELAPSED = "elapsed-seconds";
	static final String KEY_DISTCHANGE = "distance-change";
	XMLParser parser = new XMLParser();
	
	String[] timeValues;
	String[] distValues;
	String value;
	TextView averageSpeed;
	TextView duration;
	TextView calories;
	int max, min;
	
	/** Called when the activity is first created. */
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		
		DecimalFormat df = new DecimalFormat("#.#");
		
		DBHandler dbHandler = new DBHandler(this, null, null, 1);
		Bundle extras = getIntent().getExtras();
		int val = 1;
		if(extras != null)
		{
			val = Integer.parseInt(extras.getString("id"));
		}
        value = dbHandler.getWorkoutData(val);
        
        Document doc = parser.getDomElement(value);
        NodeList nl = doc.getElementsByTagName("workout");
        
        
        String timeString = "0";
        String speedString = "0";
        String caloriesString = "0";
        for( int i = 0 ; i < nl.getLength() ; i++ ) {
        	Element e = (Element) nl.item(i);
        	caloriesString = parser.getValue(e, KEY_CALORIES);
        	timeString = parser.getValue(e, KEY_TIME);
        	speedString = parser.getValue(e, KEY_AVSPEED);
        }
        
        NodeList nl2 = doc.getElementsByTagName(KEY_DISTANCE);
        
        String elapsedString = "0";
        String distChangeString = "0";
        for( int j = 0 ; j < nl2.getLength() ; j++ ) {
        	Element e = (Element) nl2.item(j);
        	elapsedString = parser.getValue(e, KEY_TIMEELAPSED);
        	distChangeString = parser.getValue(e, KEY_DISTCHANGE);
        }
        
        timeValues = elapsedString.split(",");
        distValues = distChangeString.split(",");

        // init example series data
        Random rand = new Random();
        int size = timeValues.length;
        GraphViewData[] data = new GraphViewData[size];
        for (int i=0; i < timeValues.length; i++) {
            data[i] = new GraphViewData(Double.parseDouble(timeValues[i]), Double.parseDouble(distValues[i]));
        }
        GraphViewSeries exampleSeries = new GraphViewSeries(data);

        GraphView graphView;

        graphView = new LineGraphView(
                        this // context
                        , "Distance [km] / Time [s] " // heading
        );
        
        graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);  
        graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
        graphView.addSeries(exampleSeries); // data
        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                	DecimalFormat df = new DecimalFormat("#.#");
                	DecimalFormat dg = new DecimalFormat("#.##");
                		if (!isValueX) {
                			if(value>0.1) {
                				return df.format(value);
                			} else {
                				return dg.format(value);
                			}
                		} else {
                			return null; // let graphview generate Y-axis label for us
                		}
                }
        });
        LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
        layout.addView(graphView);

        /*
         * use Date as x axis label
         */
        long now = new Date().getTime();
        data = new GraphViewData[size];
        for (int i=0; i<size; i++) {
                data[i] = new GraphViewData(now+(i*60*60*24*1000), rand.nextInt(20)); // next day
        }
        exampleSeries = new GraphViewSeries(data);

        graphView = new LineGraphView(
                        this
                        , "GraphViewDemo"
        );
        ((LineGraphView) graphView).setDrawBackground(true);

        graphView.addSeries(exampleSeries); // data

        /*
         * date as label formatter
         */
        final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");

        graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                                Date d = new Date((long) value);
                                return dateFormat.format(d);
                        }
                        return null; // let graphview generate Y-axis label for us
                }
        });


        
		
		String speedStringFormat = df.format(Double.parseDouble(speedString)); 
		
		averageSpeed = (TextView)findViewById(R.id.averageSpeed);
		duration = (TextView)findViewById(R.id.duration);
		calories = (TextView)findViewById(R.id.calories);
		averageSpeed.setText("Average speed: " + speedStringFormat + " km/h");
		duration.setText("Duration of exercise: " + String.valueOf(timeString) + " seconds");
		calories.setText("Calories burned: " +String.valueOf(caloriesString));
	}
	
}
