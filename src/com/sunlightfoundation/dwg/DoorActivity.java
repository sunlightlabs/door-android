package com.sunlightfoundation.dwg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DoorActivity extends Activity {
	private EditText pinField;
	private DoorHelper door;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        door = new DoorHelper(this);
        
        setupControls();
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
					door.open(pin);
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
		door.savePin(null);
		alert("PIN cleared from phone.");
    }
    
    public void alert(String text) {
    	Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}