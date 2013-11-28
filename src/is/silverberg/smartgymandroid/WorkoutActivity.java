package is.silverberg.smartgymandroid;

import java.util.ArrayList;
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

public class WorkoutActivity extends Activity {

	TextView workoutMsg;
	Button workout_btn;
	TextView speed_display;
	TextView distance_display;
	private double speedValue = 0;
	private int distanceValue = 0;
	private ArrayList<Double> speedList;
	private BroadcastReceiver broadCastReciever = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
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
		 speedList = new ArrayList<Double>();
	}
	
	private OnClickListener button_click = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			EquipmentManager.getInstance().sendSetWorkoutLevel(10);
			EquipmentManager.getInstance().sendShowConsoleMessage("Level to ten!");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout, menu);
		return true;
	}
	
	
	
	@Override
	protected void onPause() {
		super.onStop();
		unregisterReceiver(broadCastReciever);
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadCastReciever, new IntentFilter(LFOpen.CONNECTED));
		if (LFOpen.getInstance() != null) {
			LFOpen.getInstance().setEquipmentObserver(cObserver);
		}
	}
	
	
	@SuppressWarnings("unused")
	private EquipmentObserver cObserver = new EquipmentObserver() {

		@Override
		public void onAutoLoginRequest() {
			// TODO Auto-generated method stub
			
		}

		@Override
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConsoleMaxInclineReceived(double arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConsoleMaxTimeReceived(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConsoleUnitsReceived(byte arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(Exception arg0) {
			Log.e("WorkoutActivity", "Error herna!", arg0);
		}

		@Override
		public void onInit() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<WorkoutPreset> onSendingWorkoutPreset() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onSetWorkoutInclineAckReceived(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSetWorkoutLevelAckReceived(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSetWorkoutThrAckReceived(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSetWorkoutWattsAckReceived(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onShowConsoleMessageAckReceived(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStreamReceived(WorkoutStream workoutstream) {
//			Log.w("WorkoutActivity", "Stream received!");
//			EquipmentManager.getInstance().sendShowConsoleMessage("Workout Stream");
			speedValue = workoutstream.getCurrentSpeed();
			distanceValue = (int) workoutstream.getAccumulatedDistance();
			
			speedList.add(speedValue);
			
//			if (speed_display.isFocused() == false) {
//				speed_display.setText(speedValue + "");
//			}
//			if (distance_display.isFocused() == false) {
//				distance_display.setText(distanceValue + "");
//			}
			
			
			
//			new DisplayData().execute();
//			level_display.setText(levelValue);
			
		}

		@Override
		public void onWorkoutPaused() {
			
			Toast.makeText(WorkoutActivity.this, distanceValue, Toast.LENGTH_LONG).show();
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWorkoutPresetSent() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onWorkoutResultReceived(final WorkoutResult workoutresult) {
//			
//			finalResult = Double.toString(finalDistance)+"    "+Double.toString(finalCalories)+"    "+currentDate+Double.toString(finalElapsedTime);
//			Toast.makeText(WorkoutActivity.this, finalResult, Toast.LENGTH_LONG).show();
//			Toast.makeText(WorkoutActivity.this, "Drasl", Toast.LENGTH_LONG).show();
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					DBHandler dbHandler = new DBHandler(WorkoutActivity.this, null, null, 1);
					dbHandler.addWorkoutData(workoutresult.getEquipmentResultxml());
					Intent intent = new Intent(WorkoutActivity.this, ExerciseActivity.class);
					startActivity(intent);
				}
			});
			
			
		}

		@Override
		public void onWorkoutResume() {
			
		}
	};
	
}


