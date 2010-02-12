package com.sunlightfoundation.dwg;

import android.content.Context;
import android.widget.Toast;

public class Utils {

	public static void savePin(Context context, String pin) {
		context.getSharedPreferences("sunlight-door", 0).edit().putString("pin", pin).commit();
    }
    
    public static String getPin(Context context) {
    	return context.getSharedPreferences("sunlight-door", 0).getString("pin", null);
    }
    
    public static void alert(Context context, String text) {
    	Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
	
}
