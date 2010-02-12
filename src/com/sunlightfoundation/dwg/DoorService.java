package com.sunlightfoundation.dwg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;

public class DoorService extends Service {
	
	@Override
	public void onCreate() {
		String deviceId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
	    String pin = Utils.getPin(this);
	    
	    if (pin == null || pin.equals(""))
	    	Utils.alert(this, "No PIN stored, please open the door once from the main application before using the widget.");
	    else
	    	openDoor(deviceId, pin);
	    
		stopSelf();
	}
	
	public void openDoor(String deviceId, String pin) {
		try {
			String url = getResources().getString(R.string.door_url);
    		String response = new Door(url).open(deviceId, pin);
    		Utils.savePin(this, pin);
			Utils.alert(this, response.trim());
    	} catch(DoorException exception) {
			Utils.alert(this, exception.getMessage());
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
