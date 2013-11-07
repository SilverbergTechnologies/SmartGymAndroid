package is.silverberg.smartgymandroid;


import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

/**
 * Class for displaying and interacting with Register Activity
 * @author Silverberg
 *
 */
public class RegisterActivity extends Activity {

	public final static String EXTRA_EMAIL = "com.example.smartgym.EMAIL";
	public final static String EXTRA_PASSWORD = "com.example.smartgym.PASSWORD";
	
	/**
	 * Called when activity is opened
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * Called when options menu is created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** 
     * Called when the user clicks the Register button
     */
    public void register(View view) {
       	DBHandler dbHandler = new DBHandler(this, null, null, 1);
    	EditText nameInput = (EditText)findViewById(R.id.full_name);
    	EditText emailInput = (EditText) findViewById(R.id.email_address);
    	EditText passwordInput = (EditText) findViewById(R.id.password);
    	User user = new User( nameInput.getText().toString(), emailInput.getText().toString() , passwordInput.getText().toString() );
    	try {
    		dbHandler.addUser(user);    		
    	} catch( NoSuchAlgorithmException e ) { }
    	Intent intent = new Intent(this, HomeActivity.class);
    	startActivity(intent);
    }
    
}
