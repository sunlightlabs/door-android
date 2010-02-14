package com.sunlightfoundation.dwg;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DoorService extends Service {
	private DoorHelper door;
	
	@Override
	public void onCreate() {
		door = new DoorHelper(this);
		String pin = door.getPin();
	    
	    if (pin == null || pin.equals(""))
	    	alert("No PIN stored, please open the door once from the main application before using the widget.");
	    else
	    	new OpenTask().execute(pin);
	}
	
	public void showSpinner() {
		replaceWidget(R.layout.widget_rotating);
	}
	
	public void hideSpinner() {
		replaceWidget(R.layout.widget);
	}
	
	public void replaceWidget(int layout) {
		RemoteViews views = DoorWidgetProvider.buildView(this, layout);
		
		ComponentName widget = new ComponentName(this, DoorWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(widget, views);
	}
	
	public void alert(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	private class OpenTask extends AsyncTask<String,Void,String> {
    	private DoorException exception;
    	
    	@Override
    	protected void onPreExecute() {
    		showSpinner();
    	}
    	
    	@Override
    	protected String doInBackground(String... pin) {
    		try {
    			String deviceId = door.deviceId();
    			if (deviceId == null || deviceId.equals(""))
    				throw new DoorException("No device ID - cannot open door.");
    			
	    		String response = new Door(door.doorUrl()).open(deviceId, pin[0]);
	    		door.savePin(pin[0]);
	    		return response;
    		} catch (DoorException exception) {
    			this.exception = exception;
    		}
    		return null;
    	}
    	
    	@Override
    	protected void onPostExecute(String response) {
    		hideSpinner();
    		
    		if (exception == null)
    			alert(response);
    		else
    			alert(exception.getMessage());
    		
    		stopSelf();
    	}
    }
}