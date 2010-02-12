package com.sunlightfoundation.dwg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DoorService extends Service {
	
	@Override
	public void onCreate() {
		DoorHelper door = new DoorHelper(this);
		String pin = door.getPin();
	    
	    if (pin == null || pin.equals(""))
	    	alert("No PIN stored, please open the door once from the main application before using the widget.");
	    else
	    	door.open(pin);
	    
		stopSelf();
	}
	
	public void alert(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}