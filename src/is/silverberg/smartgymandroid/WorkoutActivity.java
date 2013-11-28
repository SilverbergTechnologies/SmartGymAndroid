package is.silverberg.smartgymandroid;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lf.api.EquipmentManager;
import com.lf.api.EquipmentObserver;
import com.lf.api.License;
import com.lf.api.models.WorkoutPreset;
import com.lf.api.models.WorkoutResult;
import com.lf.api.models.WorkoutStream;

/**
 * Class for handling and interacting with workout activity
 * @author Kjartan B. Kristjánsson
 *
 */
public class WorkoutActivity extends Activity {

	TextView workoutMsg;
	Button workout_btn;
	TextView speed_display;
	TextView distance_display;
	
	// An instance of BroadcastReceiver is created for connecting to equipment
	private BroadcastReceiver broadCastReciever = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
		}
	};
	
	@Override
	/**
	 * Called when activity is opened
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
		// Check if LifeFitness license is valid
		if(License.getInstance().isLicenseValid()) {
			workoutMsg = (TextView)findViewById(R.id.workout_msg);
			workoutMsg.setText("Leyfi er virkt!");	
		}
		
		
		bindService(new Intent(this, EquipmentManager.class), new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName pName) {    
			}
			@Override
			public void onServiceConnected(ComponentName pName, IBinder pService) {
				EquipmentManager.getInstance().registerObserver(cObserver);
				EquipmentManager.getInstance().start();
				String ver = EquipmentManager.getInstance().getConsoleVersion();
			}
		},
		Context.BIND_AUTO_CREATE);
		
		findViewById(R.id.workout_btn).setOnClickListener(button_click);
		speed_display = (TextView)findViewById(R.id.speed_display);
		distance_display = (TextView)findViewById(R.id.distance_display);
	}
	
	private OnClickListener button_click = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			EquipmentManager.getInstance().sendSetWorkoutLevel(10);
			EquipmentManager.getInstance().sendShowConsoleMessage("Level to ten!");
		}
	};

	@Override
	/**
	 * Called when options menu is created
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}
	
	
	
	@Override
	/**
	 * Called when activity is closed
	 */
	protected void onPause() {
		super.onStop();
		unregisterReceiver(broadCastReciever);
	}

	@Override
	/**
	 * Called when activity is opened
	 */
	protected void onResume() {
		super.onResume();
		registerReceiver(broadCastReciever, new IntentFilter(LFOpen.CONNECTED));
		if (LFOpen.getInstance() != null) {
			LFOpen.getInstance().setEquipmentObserver(cObserver);
		}
	}
	
	
	@SuppressWarnings("unused")
	// An instance of EquipmentObserver that connects to LifeFitness equipment
	private EquipmentObserver cObserver = new EquipmentObserver() {

		@Override
		public void onAutoLoginRequest() {
			
		}

		@Override
		/**
		 * Called when equipment connection is successful
		 */
		public void onConnected() {
			
			WorkoutActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					CharSequence text = "Observer connected!";
					Toast.makeText(WorkoutActivity.this, text, Toast.LENGTH_SHORT).show();
				}
			});
			EquipmentManager.getInstance().sendSetWorkoutLevel(5);
		}

		@Override
		public void onConnection() {
			
		}

		@Override
		public void onConsoleMaxInclineReceived(double arg0) {
			
		}

		@Override
		public void onConsoleMaxTimeReceived(int arg0) {
			
		}

		@Override
		public void onConsoleUnitsReceived(byte arg0) {
			
		}

		@Override
		public void onDisconnected() {
			
		}

		@Override
		/**
		 * Called when error occurs
		 */
		public void onError(Exception arg0) {
			Log.e("WorkoutActivity", "Error!", arg0);
		}

		@Override
		public void onInit() {
			
		}

		@Override
		public List<WorkoutPreset> onSendingWorkoutPreset() {
			return null;
		}

		@Override
		public void onSetWorkoutInclineAckReceived(boolean arg0) {
			
		}

		@Override
		public void onSetWorkoutLevelAckReceived(boolean arg0) {
			
		}

		@Override
		public void onSetWorkoutThrAckReceived(boolean arg0) {
			
		}

		@Override
		public void onSetWorkoutWattsAckReceived(boolean arg0) {
			
		}

		@Override
		public void onShowConsoleMessageAckReceived(boolean arg0) {
			
		}

		@Override
		/**
		 * Called when data is received from equipment
		 */
		public void onStreamReceived(WorkoutStream workoutstream) {
			
		}

		@Override
		public void onWorkoutPaused() {
			
		}

		@Override
		public void onWorkoutPresetSent() {
			
		}

		@Override
		/**
		 * Called when workout result is received (exercise is finished by user)
		 */
		public void onWorkoutResultReceived(final WorkoutResult workoutresult) {
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					DBHandler dbHandler = new DBHandler(WorkoutActivity.this, null, null, 1);
					dbHandler.addWorkoutData(workoutresult.getEquipmentResultxml());
					Intent intent = new Intent(WorkoutActivity.this, CustomizedListView.class);
					startActivity(intent);
				}
			});
		}

		@Override
		public void onWorkoutResume() {
			
		}
	};
	
}


