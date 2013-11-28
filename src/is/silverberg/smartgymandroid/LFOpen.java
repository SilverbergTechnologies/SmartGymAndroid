package is.silverberg.smartgymandroid;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.lf.api.EquipmentManager;
import com.lf.api.EquipmentObserver;
import com.lf.api.models.WorkoutPreset;
import com.lf.api.models.WorkoutResult;
import com.lf.api.models.WorkoutStream;

public class LFOpen extends Service implements EquipmentObserver {
	
	static final String CONNECTED = "is.silverberg.smartgymandroid.CONNECTED";
	
	
	private NotificationManager nm;
    private Notification notification;
    private static final int NOTIFICATON_ID_STREAM = 8989;
	public EquipmentObserver observer;
	private static LFOpen _instance;
	public static boolean isRunning = false;
	private boolean reconnect = false;
    private volatile boolean hasResult = false;
	
//	@SuppressWarnings("unused")
//    private ServiceConnection eqManagerServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        	
//            EquipmentManager.getInstance().stop();
//
//            if (reconnect) {
//                reconnect = false;
//                bindService(new Intent(LFOpen.this, EquipmentManager.class), eqManagerServiceConnection, BIND_AUTO_CREATE);
//            }
//        }
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            EquipmentManager.getInstance().registerObserver(LFOpen.this);
//            if (observer != null) {
//                EquipmentManager.getInstance().registerObserver(observer);
//            }
//            EquipmentManager.getInstance().start();
//            isRunning = true;
//            EquipmentManager.getInstance().sendShowConsoleMessage("Testing...");
//        }
//    };
	
    
//    @SuppressWarnings("deprecation")
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        _instance = this;
////        prefs = PreferenceManager.getDefaultSharedPreferences(this);
//
//        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notification = new Notification(R.drawable.ic_launcher, "Equipment service RUnning", System.currentTimeMillis());
//        notification.setLatestEventInfo(LFOpen.this, "Equipment service RUnning", "Duration:00:00", PendingIntent.getActivity(LFOpen.this, 0, new Intent(LFOpen.this, WorkoutActivity.class), 0));
//        notification.flags = Notification.FLAG_ONGOING_EVENT;
//        nm.notify(NOTIFICATON_ID_STREAM, notification);
//
//        
//        bindService(new Intent(LFOpen.this, EquipmentManager.class), eqManagerServiceConnection, BIND_AUTO_CREATE);
//
//        return super.onStartCommand(intent, flags, startId);
//    }
    
    
    public void setEquipmentObserver(EquipmentObserver pobserver) {
        if (pobserver == null && observer != null) {
            EquipmentManager.getInstance().unregisterObserver(observer);
            observer = null;

        } else {
            if (EquipmentManager.getInstance() != null) {
                EquipmentManager.getInstance().registerObserver(pobserver);
            }
        }
        observer = pobserver;
    }

    public static LFOpen getInstance() {

        return _instance;
    }
    
	@Override
	public void onAutoLoginRequest() {
				
	}

	@Override
	public void onConnected() {
		Toast.makeText(this, "Phone connected", Toast.LENGTH_SHORT).show();	
		try {
            sendBroadcast(new Intent(CONNECTED));
        } catch (Exception e) {
        }
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
	public void onError(Exception arg0) {
		
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
	public void onStreamReceived(WorkoutStream arg0) {

	}

	@Override
	public void onWorkoutPaused() {
		
	}

	@Override
	public void onWorkoutPresetSent() {
		
	}

	@Override
	public void onWorkoutResultReceived(WorkoutResult arg0) {

	}

	@Override
	public void onWorkoutResume() {
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
