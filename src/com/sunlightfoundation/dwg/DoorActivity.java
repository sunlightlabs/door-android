package com.sunlightfoundation.dwg;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DoorActivity extends Activity {
	private EditText pinField;
	private DoorHelper door;
	private OpenTask openTask;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        door = new DoorHelper(this);
        
        setupControls();
        
        openTask = (OpenTask) getLastNonConfigurationInstance();
        if (openTask != null)
        	openTask.onScreenLoad(this);
    }
    
    public void onOpenDoor(String response) {
    	alert(response);
    }
    
    public void onOpenDoor(DoorException exception) {
    	alert(exception.getMessage());
    }
    
    public void openDoor(String pin) {
    	if (openTask == null)
			openTask = (OpenTask) new OpenTask(DoorActivity.this).execute(pin);
    }
    
    public Object onRetainNonConfigurationInstance() {
    	return openTask;
    }
    
    public void setupControls() {
    	TextView deviceView = (TextView) findViewById(R.id.device_id);
    	String deviceId = door.deviceId();
    	if (deviceId != null && !deviceId.equals(""))
    		deviceView.setText("Device ID: " + deviceId);
    	else
    		deviceView.setText("No device ID found! You will not be able to open the door.");
    	
    	pinField = (EditText) findViewById(R.id.pin);
    	
    	String savedPin = door.getPin();
    	if (savedPin != null && !savedPin.equals(""))
    		pinField.setText(savedPin);
    	
    	findViewById(R.id.open_door).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String pin = pinField.getText().toString();
				if (!pin.equals(""))
					openDoor(pin);
			}
		});
    }
    
    public void clearPin() {
    	pinField.setText("");
		door.savePin(null);
		alert("PIN cleared from phone.");
    }
    
    public void alert(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    public void showSpinner() {
    	findViewById(R.id.opening).setVisibility(View.VISIBLE);
    	findViewById(R.id.open_door).setEnabled(false);
    }
	
	public void hideSpinner() {
		findViewById(R.id.opening).setVisibility(View.INVISIBLE);
		findViewById(R.id.open_door).setEnabled(true);
	}
    
    private class OpenTask extends AsyncTask<String,Void,String> {
    	public DoorActivity context;
    	private DoorException exception;
    	private DoorHelper door;
    	
    	public OpenTask(DoorActivity context) {
    		super();
    		this.context = context;
    		this.door = new DoorHelper(context);
    	}
    	
    	@Override
    	protected void onPreExecute() {
    		context.showSpinner();
    	}
    	
    	public void onScreenLoad(DoorActivity context) {
    		this.context = context;
    		this.door = new DoorHelper(this.context);
    		this.context.showSpinner();
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
    		context.hideSpinner();
    		context.openTask = null;
    		
    		if (exception == null)
    			context.onOpenDoor(response);
    		else
    			context.onOpenDoor(exception);
    	}
    }
}