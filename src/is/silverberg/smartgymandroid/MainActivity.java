package is.silverberg.smartgymandroid;

import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
    	Intent intent = new Intent(this, HomeActivity.class);
    	DBHandler dbHandler = new DBHandler(this, null, null, 1);
    	EditText emailInput = (EditText) findViewById(R.id.email_address);
    	EditText passwordInput = (EditText) findViewById(R.id.password);
    	User user = dbHandler.findUser(emailInput.getText().toString());
    	boolean result = false;
    	if( user == null ) { 
    		
    	} else {
	    	try {
	    		result = dbHandler.checkPassword(passwordInput.getText().toString(), user.getPassword(), user.getSalt());    		
	    	} catch( NoSuchAlgorithmException e ) {}
    	}
  //  	String email = emailInput.getText().toString();
  //  	String password = passwordInput.getText().toString();
  //  	intent.putExtra(EXTRA_EMAIL, email);
  //  	intent.putExtra(EXTRA_PASSWORD, password);
    	if(result) {
    		startActivity(intent);    		
    	} else {
    		Context context = getApplicationContext();
    		CharSequence text = "Wrong email or password!";
    		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    		toast.show();
    	}
    }
    
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    
}

