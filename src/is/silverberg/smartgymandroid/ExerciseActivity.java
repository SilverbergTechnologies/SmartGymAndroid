package is.silverberg.smartgymandroid;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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
 * @author Silverberg
 *
 */
public class ExerciseActivity extends Activity {

	
	Bundle extras;
	String value;
	TextView maxSpeed;
	TextView minSpeed;
	TextView distance;
	int max, min;
	
	/** Called when the activity is first created. */
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);



                // init example series data
                Random rand = new Random();
                int size = 20;
                GraphViewData[] data = new GraphViewData[size];
                for (int i=0; i<size; i++) {
                        int tmp = rand.nextInt(15);
                        if(i==0){ max = tmp; min = tmp; }
                        if(tmp > max) { max = tmp; }
                        if(tmp < min) { min = tmp; }
                        data[i] = new GraphViewData(i, tmp+5);
                }
                GraphViewSeries exampleSeries = new GraphViewSeries(data);

                GraphView graphView;

                graphView = new LineGraphView(
                                this // context
                                , "km / hour" // heading
                );
                
                graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);  
                graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
                graphView.addSeries(exampleSeries); // data
                graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
                        @Override
                        public String formatLabel(double value, boolean isValueX) {

                                return null; // let graphview generate Y-axis label for us
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


		extras = getIntent().getExtras();
		if (extras != null) {
			value = extras.getString("workoutXML");
		}
		
		
		maxSpeed = (TextView)findViewById(R.id.maxSpeed);
		minSpeed = (TextView)findViewById(R.id.minSpeed);
		distance = (TextView)findViewById(R.id.distance);
		maxSpeed.setText("Maximum speed: " + String.valueOf(max+5) + " km/h");
		minSpeed.setText("Minimum speed: " + String.valueOf(min+5) + " km/h");
		distance.setText(value);
	}

}
