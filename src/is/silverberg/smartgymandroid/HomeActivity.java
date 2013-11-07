package is.silverberg.smartgymandroid;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    
    public void openProfiles(View view) {
        Intent intent = new Intent(this, ProfilesActivity.class);
        startActivity(intent);
    }

    public void openExercise(View view) {
        Intent intent = new Intent(this, ExerciseActivity.class);
        startActivity(intent);
    }

    
}