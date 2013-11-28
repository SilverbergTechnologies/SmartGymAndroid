package is.silverberg.smartgymandroid;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.lf.api.EquipmentManager;
import com.lf.api.EquipmentObserver;
import com.lf.api.models.WorkoutPreset;
import com.lf.api.models.WorkoutResult;
import com.lf.api.models.WorkoutStream;

/**
 * Class for implementing LifeFitness EquipmentObserver 
 * @author Kjartan B. Kristjánsson
 *
 */
public class LFOpen extends Service implements EquipmentObserver {
	
	static final String CONNECTED = "is.silverberg.smartgymandroid.CONNECTED";
	
	public EquipmentObserver observer;
	private static LFOpen _instance;
	public static boolean isRunning = false;

	/**
	 * A method to instantiate EquipmentObserver
	 * @param pobserver
	 */
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
	/**
	 * Get instance of LFOpen class
	 * @return
	 */
    public static LFOpen getInstance() {

        return _instance;
    }
    
	@Override
	public void onAutoLoginRequest() {
				
	}

	@Override
	/**
	 * Called when connected to equipment
	 */
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
