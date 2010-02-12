package com.sunlightfoundation.dwg;

public class DoorException extends Exception {
	private static final long serialVersionUID = -2623309261327198081L;
    private String msg;
    
    public DoorException(String msg) {
    	super(msg);
    	this.msg = msg;
    }
    
    public DoorException(Exception e, String msg) {
    	super(e);
    	this.msg = msg;
    }
    
    public String getMessage() {
    	return this.msg;
    }
}
