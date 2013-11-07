package is.silverberg.smartgymandroid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static String EXTRA_USERNAME = "is.silverberg.smargymandroid.USERNAME";
	public final static String EXTRA_PASSWORD = "is.silverberg.smargymandroid.PASSWORD";
	
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
    	
    	// Send login information to server
    	EditText usernameInput = (EditText) findViewById(R.id.username);
    	EditText passwordInput = (EditText) findViewById(R.id.password);
    	String username = usernameInput.getText().toString();
    	String password = passwordInput.getText().toString();
    	
    	 // Create data variable for sent values to server  
        /*String data = URLEncoder.encode("user", "UTF-8") 
                     + "=" + URLEncoder.encode(usernameInput, "UTF-8"); 

        data += "&" + URLEncoder.encode("pwd", "UTF-8") 
                    + "=" + URLEncoder.encode(password, "UTF-8");
        */
    	String data = "";
		try {
			data = URLEncoder.encode("command=hello", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String text = "";
        BufferedReader reader=null;
        
        // Send data 
        try
        { 
          
            // Defined URL  where to send data
          URL url = new URL("http://146.185.141.99/");
             
         // Send POST data request

          URLConnection conn = url.openConnection(); 
          conn.setDoOutput(true); 
          OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
          wr.write( data ); 
          wr.flush(); 
      
          // Get the server response 
           
        reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        
        // Read Server Response
        while((line = reader.readLine()) != null)
            {
                   // Append server response in string
                   sb.append(line + "\n");
            }
            
            
            text = sb.toString();
        }
        catch(Exception ex)
        {
             
        }
        finally
        {
            try
            {
 
                reader.close();
            }

            catch(Exception ex) {}
        }
              
        // Show response on activity
        //content.setText( text  );
        
    	
    	// Sends the user to the home screen (HomeActivity)
        //Intent intent = new Intent(this, HomeActivity.class);
    	//intent.putExtra(EXTRA_USERNAME, username);
    	//intent.putExtra(EXTRA_PASSWORD, password);
    	//startActivity(intent);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(text);
        setContentView(textView);
    }
}
