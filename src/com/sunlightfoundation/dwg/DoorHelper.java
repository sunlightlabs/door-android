package com.sunlightfoundation.dwg;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class DoorHelper {
	private Context context;
	
	public DoorHelper(Context context) {
		this.context = context;
	}
	
	public void open(String pin) {
		String deviceId = deviceId();
		if (deviceId == null || deviceId.equals("")) {
			alert("No device ID - cannot open door.");
			return;
		}
		
		try {
    		String response = new Door(doorUrl()).open(deviceId, pin);
    		savePin(pin);
			alert(response.trim());
    	} catch(DoorException exception) {
			alert(exception.getMessage());
		}
	}

	public void savePin(String pin) {
		context.getSharedPreferences("sunlight-door", 0).edit().putString("pin", pin).commit();
    }
    
    public String getPin() {
    	return context.getSharedPreferences("sunlight-door", 0).getString("pin", null);
    }
    
    public String doorUrl() {
    	return context.getResources().getString(R.string.door_url);
    }
    
    public String deviceId() {
    	return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }
    
    public void alert(String text) {
    	Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
	
}