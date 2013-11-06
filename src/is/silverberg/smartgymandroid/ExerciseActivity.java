package is.silverberg.smartgymandroid;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class ExerciseActivity extends Activity {
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
			data[i] = new GraphViewData(i, rand.nextInt(10)+5);
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(data);

		GraphView graphView;

		graphView = new LineGraphView(
				this // context
				, "Calories / min" // heading
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


	}
}
