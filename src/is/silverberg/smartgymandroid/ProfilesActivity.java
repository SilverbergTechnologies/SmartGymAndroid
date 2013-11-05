package is.silverberg.smartgymandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class ProfilesActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        GraphViewData[] data = new GraphViewData[10];
        double v = 0;
        for (int i = 0; i < 10; i++) {
            v += 0.1;
            data[i] = new GraphViewData(i, Math.sin(v));            
        }
        GraphViewSeries seriesSin = new GraphViewSeries("Sinus", null, data);  
        GraphView graphView = new LineGraphView(this, "DemoGraphView");
        graphView.addSeries(seriesSin);
        graphView.setViewPort(2, 10);

        LinearLayout layout = (LinearLayout) findViewById(R.id.subLayout);  
        layout.addView(graphView); 
        
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    
    
}