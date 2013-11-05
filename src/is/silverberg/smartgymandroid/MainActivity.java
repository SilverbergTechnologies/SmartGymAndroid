package is.silverberg.smartgymandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_EMAIL = "com.example.smartgym.EMAIL";
	public final static String EXTRA_PASSWORD = "com.example.smartgym.PASSWORD";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Login button */
    public void login(View view) {
        // Do something in response to button
    	Intent intent = new Intent(this, HomeActivity.class);
    	EditText emailInput = (EditText) findViewById(R.id.email_address);
    	EditText passwordInput = (EditText) findViewById(R.id.password);
    	String email = emailInput.getText().toString();
    	String password = passwordInput.getText().toString();
    	intent.putExtra(EXTRA_EMAIL, email);
    	intent.putExtra(EXTRA_PASSWORD, password);
    	startActivity(intent);
    }
    
}
