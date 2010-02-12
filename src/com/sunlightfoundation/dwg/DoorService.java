package com.sunlightfoundation.dwg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class DoorService extends Service {
	
	@Override
	public void onCreate() {
		String deviceId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
	    String pin = getSharedPreferences("sunlight-door", 0).getString("pin", null);
	    
	    if (pin == null || pin.equals(""))
	    	alert("No PIN stored, please open the door once from the main application before using the widget.");
	    else
	    	openDoor(deviceId, pin);
	    
		stopSelf();
	}
	
	public void openDoor(String deviceId, String pin) {
		try {
			String url = getResources().getString(R.string.door_url);
    		String response = new Door(url).open(deviceId, pin);
    		getSharedPreferences("sunlight-door", 0).edit().putString("pin", pin).commit();
			alert(response.trim());
    	} catch(DoorException e) {
			alert(e.getMessage());
		}
	}

	private void alert(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
