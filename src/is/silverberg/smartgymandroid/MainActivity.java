package is.silverberg.smartgymandroid;

import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lf.api.License;

/**
 * Class for displaying and interacting with login screen
 * @author Daníel Sveinsson
 *
 */
public class MainActivity extends Activity {

	final static String EXTRA_EMAIL = "com.example.smartgym.EMAIL";
	final static String EXTRA_PASSWORD = "com.example.smartgym.PASSWORD";
	
	
	@Override
	/**
	 * Called when a new intent is received
	 */
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		checkIntentIfFromEquipement(intent);

	}

	@SuppressLint("NewApi")
	/**
	 * A method to check if a received intent is from exercise equipment
	 * @param intent
	 */
	private void checkIntentIfFromEquipement(Intent intent) {
		if (intent.getAction().equals(UsbManager.ACTION_USB_ACCESSORY_ATTACHED)) {
			Toast.makeText(MainActivity.this, "USB accessory attached", Toast.LENGTH_LONG).show();
			
			startService(new Intent(MainActivity.this, LFOpen.class));

			UsbManager manager = (UsbManager) getSystemService(USB_SERVICE);
			UsbAccessory[] accessoryList = manager.getAccessoryList();
			// THIS IS TO GET THE MODEL OF THE CONSOLE.
			if (accessoryList[0] != null) {
				String returnStr = "";
				returnStr += "Manufacturer:" + accessoryList[0].getManufacturer() + "\n";
				returnStr += "Model:" + accessoryList[0].getModel() + "\n";
				returnStr += "Description:" + accessoryList[0].getDescription() + "\n";
				Toast.makeText(MainActivity.this, returnStr, Toast.LENGTH_LONG).show();

			}

		}

	}
	
	/**
	 * Called when activity is opened
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        License.getInstance().setEnvironmentToLive(MainActivity.this, true);
        License.getInstance().setLicense(this,"380-5619134716-54906");
        
		checkIntentIfFromEquipement(getIntent());
    }


    /**
     * Called when options menu is created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Login button 
     *  @param view
     */
    public void login(View view) {
    	Intent intent = new Intent(this, HomeActivity.class);
    	DBHandler dbHandler = new DBHandler(this, null, null, 1);
    	EditText emailInput = (EditText) findViewById(R.id.email_address);
    	EditText passwordInput = (EditText) findViewById(R.id.password);
    	User user = dbHandler.findUser(emailInput.getText().toString());
    	boolean result = false;
    	if( user != null ) { 
	    	try {
	    		result = Password.checkPassword(passwordInput.getText().toString(), user.getPassword(), user.getSalt());    		
	    	} catch( NoSuchAlgorithmException e ) {}
    	}
    	if(result) {
    		startActivity(intent);    		
    	} else {
    		Context context = getApplicationContext();
    		CharSequence text = "Wrong email or password!";
    		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    		toast.show();
    	}
    }
    
    /**
     * Called when the user presses the Register button
     * @param view
     */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    
}

