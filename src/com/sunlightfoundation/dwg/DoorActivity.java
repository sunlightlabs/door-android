package com.sunlightfoundation.dwg;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DoorActivity extends Activity {
	private EditText pinField;
	private String deviceId;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        deviceId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        
        setupControls();
    }
    
    public void setupControls() {
    	TextView deviceView = (TextView) findViewById(R.id.device_id);
    	if (deviceId != null && !deviceId.equals(""))
    		deviceView.setText("Device ID: " + deviceId);
    	else
    		deviceView.setText("No device ID found! You will not be able to open the door.");
    	
    	pinField = (EditText) findViewById(R.id.pin);
    	
    	String savedPin = Utils.getPin(this);
    	if (savedPin != null && !savedPin.equals(""))
    		pinField.setText(savedPin);
    	
    	findViewById(R.id.open_door).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String pin = pinField.getText().toString();
				if (deviceId != null && !deviceId.equals("") && !pin.equals("")) {
					openDoor(pin);
				}
			}
		});
    	
    	findViewById(R.id.clear_pin).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				clearPin();
			}
		});
    }
    
    public void clearPin() {
    	pinField.setText("");
		Utils.savePin(this, null);
		Utils.alert(this, "PIN cleared from phone.");
    }
    
    public void openDoor(String pin) {
    	try {
    		String url = getResources().getString(R.string.door_url);
    		String response = new Door(url).open(deviceId, pin);
    		Utils.savePin(this, pin);
			Utils.alert(this, response.trim());
    	} catch(DoorException exception) {
			Utils.alert(this, exception.getMessage());
		}
    }
    
    
}