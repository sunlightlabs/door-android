package com.sunlightfoundation.dwg;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Door {
	private static final String USER_AGENT = "Sunlight Door Opener for Android";
	private static final boolean TEST_MODE = false; // enable for test mode
	
	private String url;
	
	public Door(String url) {
		this.url = url;
	}
	
	public String open(String deviceId, String pin) throws DoorException {
		HttpResponse response = Door.get(openUrl(deviceId, pin));
		
		int code = response.getStatusLine().getStatusCode();
		switch (code) {
		case 200:
			return responseBody(response);
		case 403:
			throw new DoorException("Invalid PIN, or your device has not been granted access.");
		case 500:
		default:
			throw new DoorException("Unknown error (500) while attempting to open door.");
		}
	}
	
	private String openUrl(String deviceId, String pin) {
		String openUrl = this.url + "?device_id=" + deviceId + "&pin=" + pin;
		if (TEST_MODE)
			openUrl += "&test=true";
		return openUrl;
	}
	
	public static HttpResponse get(String url) throws DoorException {
		HttpGet request = new HttpGet(url);
    	request.addHeader("User-Agent", USER_AGENT);
    	DefaultHttpClient client = new DefaultHttpClient();
		
        try {
        	return client.execute(request);
		} catch (ClientProtocolException e) {
			throw new DoorException(e, "ClientProtocolException while making request to: " + request.getURI().toString());
		} catch (IOException e) {
			throw new DoorException(e, "Couldn't connect to the Internet. Check your network connection.");
		}
	}
	
	public static String responseBody(HttpResponse response) {
	    try {
	    	return EntityUtils.toString(response.getEntity());
		} catch(IOException e) {
			// Even if we can't read the welcome message, we've still been able to open the door
			// so it doesn't make sense to throw an exception. We'll just return a different message.
			return "Door has been opened. (Error reading server's welcome message.)"; 
		}
	}
}
